package cn.bjfu.fesdmp.web;

import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String index(User user) {
		logger.info("index method");
		logger.info(user);
		User nowUser=this.userService.findByUserLoginName(user.getUserLoginName());
		if(nowUser.getUserLoginName()!=null){
			if(user.getPassword().equals(nowUser.getPassword())){
				return "frame/index";
			}
			else 
				return "密码错误";
		}
		else return "noSuchUser";
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