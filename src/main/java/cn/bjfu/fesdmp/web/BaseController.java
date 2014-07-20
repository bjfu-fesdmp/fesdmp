/** 
 * Project Name:fesdmp 
 * File Name:BaseController.java 
 * Package Name:cn.bjfu.fesdmp.web 
 * Date:2014年7月8日 上午9:52:57 
 * Copyright (c) 2014, zhangzhaoyu0524@163.com All Rights Reserved. 
 * 
*/  
  
package cn.bjfu.fesdmp.web;  

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import cn.bjfu.fesdmp.constant.AppConstants;
import cn.bjfu.fesdmp.domain.sys.User;


/** 
 * ClassName:BaseController <br/> 
 * Function: BaseController 提供返回值的基础控制器，包括返回值的国际化. <br/> 
 * Reason:   BaseController. <br/> 
 * Date:     2014年7月8日 上午9:52:57 <br/> 
 * @author   zhangzhaoyu 
 * @version   1.0
 * @since    JDK 1.7 
 * @see       
 */
public class BaseController {
	/**
	 * 定义JSON 返回的常量值
	 */
	public static String PAGE_COUNT = "pageCount";
	public static String RESULT = "result";
	public static String SUCCESS = "success";
	
	public static String errorMsg = "";
	
	@Autowired
	protected ResourceBundleMessageSource messageSource;
	
	protected ModelAndView ajaxDone(int statusCode, String message) {
		ModelAndView mav = new ModelAndView("ajaxDone");
		mav.addObject("statusCode", statusCode);
		mav.addObject("message", message);
		return mav;
	}

	protected ModelAndView ajaxDoneSuccess(String message) {
		return ajaxDone(200, message);
	}

	protected ModelAndView ajaxDoneError(String message) {
		return ajaxDone(300, message);
	}
	protected String getMessage(String code) {
		return this.getMessage(code, new Object[] {});
	}

	protected String getMessage(String code, Object arg0) {
		return getMessage(code, new Object[] { arg0 });
	}

	protected String getMessage(String code, Object arg0, Object arg1) {
		return getMessage(code, new Object[] { arg0, arg1 });
	}

	protected String getMessage(String code, Object arg0, Object arg1,
			Object arg2) {
		return getMessage(code, new Object[] { arg0, arg1, arg2 });
	}

	protected String getMessage(String code, Object arg0, Object arg1,
			Object arg2, Object arg3) {
		return getMessage(code, new Object[] { arg0, arg1, arg2, arg3 });
	}

	protected String getMessage(String code, Object[] args) {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
		Locale locale = localeResolver.resolveLocale(request);

		return messageSource.getMessage(code, args, locale);
	}
	
	/**
	 *  Spring MVC 允许采用自定的编辑器使用注解 @InitBinder 
	 *  也可以通过WebBindingInitializer接口 装配全局范围使用的编辑器
	 */
	@InitBinder
	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) throws Exception {
		
		 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		 dateFormat.setLenient(false);
		 binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}
	
	/**
	 * 
	 * addSessionUser:<br />
	 * 将登陆的用户放入 session 中
	 *
	 * @author zhangzhaoyu
	 * @param request
	 * @param user
	 */
	public void addSessionUser(HttpServletRequest request, User user) {
		request.getSession().setAttribute(AppConstants.SESSION_USER, user);
	}
	
	/**
	 * 
	 * removeSessionUser:<br />
	 * 将登陆的用户从 session 取出来
	 *
	 * @author zhangzhaoyu
	 * @param request
	 */
	public void removeSessionUser(HttpServletRequest request) {
		User user = (User) request.getAttribute(AppConstants.SESSION_USER);
		if (user != null) {
			request.getSession().removeAttribute(AppConstants.SESSION_USER);
		}
	}
	
	/**
	 * 
	 * detroySession:<br />
	 * 清除 Session
	 *
	 * @author zhangzhaoyu
	 * @param session
	 */
	public void detroySession(HttpSession session) {
		session.invalidate();
	}
	
}
 