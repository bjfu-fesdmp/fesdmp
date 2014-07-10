/** 
 * Project Name:fesdmp 
 * File Name:SystemLogServiceTests.java 
 * Package Name:cn.bjfu.fesdmp.sys.service 
 * Date:2014年7月9日 上午10:35:57 
 * Copyright (c) 2014, zhangzhaoyu0524@163.com All Rights Reserved. 
 * 
*/  
  
package cn.bjfu.fesdmp.sys.service;  

import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.bjfu.fesdmp.domain.enums.BusinessType;
import cn.bjfu.fesdmp.domain.enums.OperationType;
import cn.bjfu.fesdmp.domain.sys.SystemLog;
import cn.bjfu.fesdmp.frame.IOrder;
import cn.bjfu.fesdmp.frame.JoinMode;
import cn.bjfu.fesdmp.frame.Order;
import cn.bjfu.fesdmp.utils.Pagination;

/** 
 * ClassName:SystemLogServiceTests <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason:   TODO ADD REASON. <br/> 
 * Date:     2014年7月9日 上午10:35:57 <br/> 
 * @author   zhangzhaoyu 
 * @version   
 * @since    JDK 1.7 
 * @see       
 */
@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration("classpath:spring/root-context.xml") 
public class SystemLogServiceTests {

	@Autowired
	private ISystemLogService systemLogService;
	
	@Ignore
	public void test() {
		SystemLog log = new SystemLog();
		log.setBusinessType(BusinessType.SYS_LOGIN);
		log.setOperateContent("user login");
		log.setOperateTime(new Date());
		log.setOperationType(OperationType.QUERY);
		log.setUserName("Lilei");
		log.setUserSourceIp("202.204.110.22");
		
		this.systemLogService.addSysLog(log);
		System.out.println("add success");
		List<SystemLog> list = this.systemLogService.queryAll(null);
		System.out.println(list);
	}
	
	@Test
	public void testFind() {
		IOrder order = new Order();
		order.addOrderBy("operateTime", "DESC");
		SystemLog log = new SystemLog();
		log.setUserName("zhang");
		log.setId(1L);
		
		Pagination<SystemLog> pagination = new Pagination<SystemLog>();
		
		List list = this.systemLogService.findByCondition(log, order, pagination, JoinMode.AND);
		System.out.println(list);
	}
}
 