package com.meteor.kit.getpage;

import com.meteor.kit.JsonKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by Meteor on 2015/8/30.
 *
 * @category (这里用一句话描述这个类的作用)
 */
public class PageManager {
    private final Logger logger = LoggerFactory.getLogger(PageManager.class);
    private StringBuffer sb=new StringBuffer();
    private long bgtime;
    private List<Integer> errnums=new ArrayList<Integer>();

    public String getErrnums() {
        if(errnums!=null&&errnums.size()>0) {
            return JsonKit.bean2JSON(errnums);
        }else {
            return "";
        }
    }

    public void setErrnums(int errnum) {
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
