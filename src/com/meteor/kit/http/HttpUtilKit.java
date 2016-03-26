package com.meteor.kit.http;

import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.meteor.common.MainConfig;
import com.meteor.kit.DateKit;
import com.meteor.kit.JsonKit;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by L on 2016/3/19.
 */
public class HttpUtilKit {
    private static final Logger logger = LoggerFactory.getLogger(HttpUtilKit.class);

    public static void main(String[] args) throws Exception {
//        String url = "http://pornleech.com/doctor-adventures-anna-bell-peaks-nicole-aniston-rachel-starr-romi-rain-the-last-dick-on-earth-new-march-15-2016-545004.html";
//        System.out.println(get503Page(url));

//        String url="http://pornleech.com/torrentimg/7e4133ac20ec880fe2e1b8b6ed866d30cd88a74d.jpg";
//        System.out.println(get503Resource(url));
    }


    public static String get503Page(String url) throws Exception{
        HtmlPage htmlPage = (HtmlPage) get503(url);
        return htmlPage.asXml();
    }

    public static String get503Resource(String url){
        Map resp = new HashMap();
        try {
            Page htmlPage = get503(url);
            WebResponse wr = htmlPage.getWebResponse();
            String filename = getFileName(wr);
            String tmpsavedir= MainConfig.tmpsavedir;
            String filpath=tmpsavedir+"/"+filename;
            File dir = new File(tmpsavedir);
            if(!dir.exists()){
                dir.mkdir();
            }
            File f = new File(filpath);
            if (!f.exists()) {
                InputStream is = wr.getContentAsStream();
                FileUtils.copyInputStreamToFile(is, f);
            }

            resp.put("status", 0);
            resp.put("filepath", f.toString());

        }   catch (Exception e) {
            // TODO: handle exception
            if(!e.toString().contains("404")) {
                logger.error("下载文件" + e.toString());
            }
            resp.put("status", -1);
            resp.put("errmsg", e.toString());
        }  catch(Throwable t) {
            if(!t.toString().contains("404")) {
                logger.error("下载文件" + t.toString());
            }
            resp.put("status", -1);
            resp.put("errmsg", t.toString());
        }
        return JsonKit.map2JSON(resp);
    }

    private static Page get503(String url) throws Exception{
        WebClient webClient = new WebClient();
        // 1 启动JS
        webClient.getOptions().setJavaScriptEnabled(true);
        // 2 禁用Css，可避免自动二次请求CSS进行渲染
        webClient.getOptions().setCssEnabled(false);
        // 3 启动客户端重定向
        webClient.getOptions().setRedirectEnabled(true);
        // 4 js运行错误时，是否抛出异常
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        // 5 设置超时
        webClient.getOptions().setTimeout(30000);
        // 6 设置忽略http异常
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);

        Page htmlPage = webClient.getPage(url);
        if (htmlPage.getWebResponse().getStatusCode() == 503) {
            // 等待JS驱动dom完成获得还原后的网页
            webClient.waitForBackgroundJavaScript(8000);
            // 禁用js驱动
            webClient.getOptions().setJavaScriptEnabled(false);
            htmlPage = webClient.getPage(url);
            if (htmlPage.getWebResponse().getStatusCode() == 503){
                logger.error("503递归：" + url);
                webClient.close();
                return get503(url);
            }
        }
        webClient.close();
        return htmlPage;
    }

    private static String getFileName(WebResponse response){
        String filename=null;
        String ctype=response.getContentType();
        if(!ctype.contains("text/html")){
            List l=response.getResponseHeaders();
            String hd= response.getResponseHeaderValue("Content-Disposition");
            if(hd!=null){
                filename=hd.split(";")[1];
                filename=filename.substring(filename.indexOf("\"")+1, filename.lastIndexOf("\""));
                filename= DateKit.getStringTodayB()+"_"+filename;
            }else{
                filename=DateKit.getStringTodayB()+"_"+DateKit.buildRandom(5);
                String filetype=".*";
                try {
                    Prop p=PropKit.getProp("contenttype.properties");
                    filetype= p.get(ctype);
                } catch (Exception e) {
                    // TODO: handle exception
                }
                filename=filename+filetype;
            }
        }
        return filename;
    }
}
