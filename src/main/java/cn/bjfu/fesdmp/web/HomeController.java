package cn.bjfu.fesdmp.web;

import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.bjfu.fesdmp.domain.sys.User;

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
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		return "frame/login";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String index(User user) {
		logger.info("index method");
		logger.info(user);
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