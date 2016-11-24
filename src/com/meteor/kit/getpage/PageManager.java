package com.meteor.kit.getpage;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.meteor.kit.JsonKit;

/**
 * Created by Meteor on 2015/8/30.
 *
 * @category (这里用一句话描述这个类的作用)
 */
public class PageManager {
    private final Logger logger = LoggerFactory.getLogger(PageManager.class);
    private StringBuffer sb=new StringBuffer();
    private long bgtime;
    private List<String> errnums=new ArrayList<String>();

    public String getErrnums() {
        if(errnums!=null&&errnums.size()>0) {
            return JsonKit.bean2JSON(errnums);
        }else {
            return "";
        }
    }

    public void setErrnums(String errnum) {
        this.errnums.add(errnum);
    }

    public long getBgtime() {
        return bgtime;
    }

    public void setBgtime(long bgtime) {
        this.bgtime = bgtime;
    }

    public String getSb() {
        return sb.toString();
    }

    public void setSb(String sbtr) {
        this.sb.append(sbtr);
    }
}
