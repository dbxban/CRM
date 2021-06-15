package cn.wolfcode.crm.web.filter;

import cn.wolfcode.crm.util.JSONResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

@Component
public class MyFormAuthenticationFilter extends FormAuthenticationFilter { //交给Spring容器管理
    //重写登录成功的方法,因为是异步提交,把json数据写出去
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject,
                                     ServletRequest request,ServletResponse response) throws Exception {
        //设置响应的数据是json格式
        response.setContentType("application/json;charset=utf-8");
        JSONResult jsonResult = JSONResult.success();
        //通过jackSon把数据变成json格式
        response.getWriter().print(new ObjectMapper().writeValueAsString(jsonResult));
        return false;//阻止放行,因为该方法的原来操作是重定向
    }

    //重写登录失败的数据响应格式是json格式  ctrl+o
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e,
                                     ServletRequest request, ServletResponse response) {

        response.setContentType("application/json;charset=utf-8");
        String errorMsg = "登录失败,请联系管理员";
        if (e instanceof UnknownAccountException) {
            errorMsg = "用户名错误";
        } else if (e instanceof IncorrectCredentialsException) {
            errorMsg = "密码错误";
        }
        JSONResult jsonResult = JSONResult.mark(errorMsg);
        try {
            response.getWriter().print(new ObjectMapper().writeValueAsString(jsonResult));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        e.printStackTrace();
        return false; //阻止放行
    }
}
