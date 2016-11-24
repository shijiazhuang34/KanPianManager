package com.meteor.interceptor;

import java.util.Map;

import javax.servlet.http.HttpSession;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;

/**
 * Created by Meteor on 2015/9/13.
 *
 * @category (这里用一句话描述这个类的作用)
 */
public class LoginCheck implements Interceptor {
    @Override
    public void intercept(Invocation inv) {
        Controller cr = inv.getController();
        HttpSession session = cr.getSession();
        Map<String, String> p = (Map) session.getAttribute("lguser");
        if (p != null) {
            String root = p.get("root");
            String pass = p.get("pass");
            String cfroot = PropKit.get("root");
            String cfpass = PropKit.get("pass");
            if (root.equals(cfroot) && pass.equals(cfpass)) {
                inv.invoke();
            } else {
                cr.redirect("/tologin");
            }
        } else {
            cr.redirect("/tologin");
        }
    }
}
