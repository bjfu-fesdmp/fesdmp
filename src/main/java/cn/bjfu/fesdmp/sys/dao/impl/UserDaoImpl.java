
package cn.bjfu.fesdmp.sys.dao.impl;  

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import cn.bjfu.fesdmp.domain.sys.SystemLog;
import cn.bjfu.fesdmp.domain.sys.User;
import cn.bjfu.fesdmp.frame.dao.IOrder;
import cn.bjfu.fesdmp.frame.dao.JoinMode;
import cn.bjfu.fesdmp.sys.dao.IUserDao;
import cn.bjfu.fesdmp.utils.DateFormat;
import cn.bjfu.fesdmp.utils.Pagination;
import cn.bjfu.fesdmp.web.jsonbean.LogSearch;
import cn.bjfu.fesdmp.web.jsonbean.UserSearch;


@Repository
public class UserDaoImpl extends AbstractGenericDao<User> implements IUserDao {
	
	private static final Logger logger = Logger.getLogger(UserDaoImpl.class);
	
	public UserDaoImpl() {
		super(User.class);
	}
	@Override
	public List<User> findByCondtinGetCreater(UserSearch condition, IOrder order, Pagination<User> page, JoinMode joinMode) {
		String jpal = " SELECT p FROM User p ";
		if (condition != null) {
			jpal += convertBeanToJPAL(condition, joinMode);
			if (condition != null) {
				jpal += convertBeanToJPAL(condition, joinMode);
			} 
			if (order != null) {
				jpal += convertToSQL(order);
			}
		} 
		if (order != null) {
			jpal += convertToSQL(order);
		}
		
		logger.info(jpal);
		Query query = super.getEntityManager().createQuery(jpal);
		if (page != null) {
			page.setTotalRecord(query.getResultList().size());
			List<User> result =  query.setFirstResult(page.getOffset()).setMaxResults(page.getPageSize()).getResultList();
 
			page.setDatas(result);
			return result;
		}else{
			return query.getResultList();
		}
		
	}
}
 