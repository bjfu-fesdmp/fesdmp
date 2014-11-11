package cn.bjfu.fesdmp.web;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.bjfu.fesdmp.domain.sys.User;
import cn.bjfu.fesdmp.sys.service.IUserService;

/**
 * 
 * ClassName: HomeController <br />
 * Function: 处理系统的登陆，登出. <br />
 * date: 2014年7月8日 下午9:18:36 <br />
 * 
 * @author zhangzhaoyu
 * @version  1.0
 * @since JDK 1.7
 */
@Controller
public class HomeController extends BaseController {
	private static final Logger logger = Logger.getLogger(HomeController.class);
	@Autowired
	private IUserService userService;
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		return "frame/login";
	}
	@RequestMapping(value = "loginpage")
	public String loginPage(Locale locale, Model model) {
		return "frame/login";
	}
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String index(HttpServletRequest request,User user) {
		logger.info("index method");
		logger.info(user);
		try{
		User nowUser=this.userService.findByUserLoginName(user.getUserLoginName());
		if(nowUser.getUserLoginName()!=null){
			if(user.getPassword().equals(nowUser.getPassword())){
				request.getSession(true).setAttribute("user", nowUser); 
				return "frame/index";
			}
			else 
			{
				request.setAttribute("errorMsg", "用户 "+ user.getUserLoginName() +" 密码不正确!");
				System.out.println("forward:loginpage");
				return "forward:loginpage";
			}
		}
		request.setAttribute("errorMsg", "用户 "+ user.getUserLoginName() +" 不存在!");
		System.out.println("forward:loginpage");
		return "forward:loginpage";
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		return "frame/index";
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public String logout(HttpSession session) {
		super.detroySession(session);
		return "frame/login";
	}
	
	@RequestMapping(value = "/top", method = RequestMethod.GET)
	public String top() {
		return "frame/top";
	}
}