package cn.wolfcode.crm.web.controller;

import cn.wolfcode.crm.util.JSONResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@ControllerAdvice//##########
public class BaseController {
    @ExceptionHandler(AuthorizationException.class)
    public void handleException(HandlerMethod method, HttpServletResponse response) throws IOException {
        ResponseBody annotation = method.getMethodAnnotation(ResponseBody.class);
        if(annotation!=null){//异步请求
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(new ObjectMapper().writeValueAsString(JSONResult.mark("没有权限!请联系管理员")));
        }else{
            response.sendRedirect("/nopermission.jsp");
        }
    }
}
