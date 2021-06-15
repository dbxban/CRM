package cn.wolfcode.crm.util;

import cn.wolfcode.crm.domain.Employee;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;

//用来获取当前登录的用户,解耦
public abstract class UserContext {
    public static Session getSession(){
        return SecurityUtils.getSubject().getSession();
    }

    public static Employee getCurrentUser(){
        return (Employee) SecurityUtils.getSubject().getPrincipal();
    }

}
