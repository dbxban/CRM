package cn.wolfcode.crm.util;

import cn.wolfcode.crm.domain.SystemLog;
import cn.wolfcode.crm.service.ISystemLogService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jws.soap.SOAPBinding;
import java.util.Date;

@Component
public class SystemLogUtil {

    @Autowired
    private ISystemLogService systemLogService;

    public void write(JoinPoint joinPoint) {
        //如果是日志的service就直接放行
        if (joinPoint.getTarget() instanceof ISystemLogService) {
            return ;
        }
        SystemLog log = new SystemLog();
        //开始日志记录的相关操作
        Long empId = UserContext.getCurrentUser().getId();//当前用户id
        String ip = UserContext.getSession().getHost();//id
        String className = joinPoint.getTarget().getClass().getName();//类名
        String methodName = joinPoint.getSignature().getName();//获取方法名
        log.setOpUser_id(empId);
        log.setOpTime(new Date());
        log.setOpIp(ip);
        log.setFunction(className + ":" + methodName);
        try {
            log.setParams(new ObjectMapper().writeValueAsString(joinPoint.getArgs()));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        systemLogService.write(log);

    }

}
