
package cn.bjfu.fesdmp.web.aspect;  

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import cn.bjfu.fesdmp.constant.AppConstants;
import cn.bjfu.fesdmp.domain.enums.BusinessType;
import cn.bjfu.fesdmp.domain.enums.OperationType;
import cn.bjfu.fesdmp.domain.sys.SystemLog;
import cn.bjfu.fesdmp.domain.sys.User;
import cn.bjfu.fesdmp.sys.service.ISystemLogService;
import cn.bjfu.fesdmp.web.annotation.MethodRecordLog;
import cn.bjfu.fesdmp.web.sys.UserManagerController;


/** 
 * ClassName:LogAspect <br/> 
 * Function: LogAspect. <br/> 
 * Reason:   LogAspect <br/> 
 * Date:     2014年11月1日 下午1:30:49 <br/> 
 * @author   zhangzhaoyu 
 * @version   
 * @since    JDK 1.7 
 * @see       
 */
@Aspect
@Component
public class LogAspect {

	private static final Logger logger = Logger.getLogger(UserManagerController.class);
	
	@Resource
	private ISystemLogService systemLogService;
	
	public LogAspect() {
	}
	
	@Pointcut("@annotation(cn.bjfu.fesdmp.web.annotation.MethodRecordLog)")  
    public void methodCachePointcut() {  
		return;
    }  
	
	//@AfterReturning("within(cn.bjfu.fesdmp.web..*) && @annotation(mrl)")
	@Around("within(cn.bjfu.fesdmp.web..*) && @annotation(mrl)") 
	public Object saveLog(final ProceedingJoinPoint joinPoint, final MethodRecordLog mrl) throws Throwable {
		
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder  
                .getRequestAttributes()).getRequest();  
		User sessionUser = (User) request.getSession().getAttribute(AppConstants.SESSION_USER);
		
		SystemLog systemLog = new SystemLog();
		systemLog.setOperateContent(mrl.desc());
		systemLog.setOperateTime(new Date());
		systemLog.setBusinessType(getBusinessType(mrl.bussinessType()));
		systemLog.setOperationType(getOperationType(mrl.operateType()));
		systemLog.setUserSourceIp(getIpAddress(request));
		
		System.out.println(systemLog);
		
		if (sessionUser != null) {
			systemLog.setUserName(sessionUser.getUserName());
		}
		
		this.systemLogService.addSysLog(systemLog);
		
		Object[] method_param = null;  
		Object object;
        try {  
            method_param = joinPoint.getArgs(); //获取方法参数   
            object = joinPoint.proceed();  
        } catch (Exception e) {
        	logger.info("savelog exception");
            throw e;  
        }  
        
		logger.info("............savelog method.......................");
		return object;
	}
	
	
	private BusinessType getBusinessType(String bussinessType) {
		if ("SYS_LOGIN".equals(bussinessType)) {
			return BusinessType.SYS_LOGIN;
		}
		else if ("SYS_LOGOUT".equals(bussinessType)) {
			return BusinessType.SYS_LOGOUT;
		}
		else if ("SYS_OPERATE".equals(bussinessType)) {
			return BusinessType.SYS_OPERATE;
		}
		else {
			return BusinessType.SYS_OTHERS;
		}
	}
	
	private OperationType getOperationType(String operationType) {
		if ("ADD".equals(operationType)) {
			return OperationType.ADD;
		}
		else if ("DELETE".equals(operationType)) {
			return OperationType.DELETE;
		}
		else if ("UPDATE".equals(operationType)) {
			return OperationType.UPDATE;
		}
		else if ("QUERY".equals(operationType)) {
			return OperationType.QUERY;
		}
		else {
			return OperationType.OTHERS;
		}
	}
	
	public String getIpAddress(HttpServletRequest request) {
    	String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
    	  ip = request.getHeader("http_client_ip");  
    	}  
    	if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
    	  ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
    	}  
    	if (ip != null && ip.indexOf(",") != -1) {  
		  ip = ip.substring(ip.lastIndexOf(",") + 1, ip.length()).trim();  
		} 
        if("0:0:0:0:0:0:0:1".equals(ip))
        {
        	ip="127.0.0.1";
        }
        return ip;
    }
}
 






















