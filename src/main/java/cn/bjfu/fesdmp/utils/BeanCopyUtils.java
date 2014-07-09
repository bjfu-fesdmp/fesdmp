package cn.bjfu.fesdmp.utils;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.beanutils.BeanUtils;
/**
 * ��һ���������ֵ����������һ���������ֵ����
 * Description:
 * Copyright (C) 2014 BOCO All Right Reserved.
 * createDate��May 28, 2014
 * author��zhanghong
 * @version ISMP V1.0
 */
public class BeanCopyUtils {
	/**
	 * ��obj1�е�����ֵ������obj2����������
	 * Definition: 
	 * author: zhanghong
	 * Created date: May 28, 2014
	 * @param obj1
	 * @param obj2
	 */
	public static void Vo2Dto(Object obj1, Object obj2) {
		try {
			BeanUtils.copyProperties(obj2, obj1);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * copy:<br />
	 * ����һ����������һ��
	 *
	 * @author zhangzhaoyu
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public static void copy(Object org, Object des) {
		
		Class<?> orgClassType = org.getClass();
		Class<?> desClassType = des.getClass();
		
		//Object objectCopy = desClassType.getConstructor(new Class[]{}).newInstance(new Object[]{});
		Field []fields = orgClassType.getDeclaredFields();
		
		for (Field field : fields) {
			String filedName = field.getName();
			String firstLetter = filedName.substring(0, 1).toUpperCase();
			String getMethodName = "get" + firstLetter + filedName.substring(1);
			String setMethodName = "set" + firstLetter + filedName.substring(1);
			
			try {
				Method getMethod = orgClassType.getMethod(getMethodName, new Class[]{});
				Method setMethod = desClassType.getMethod(setMethodName, new Class[]{field.getType()});
				
				if (getMethod != null) {
					Object value = getMethod.invoke(org, new Object[]{});
					if (setMethod != null) {
						setMethod.invoke(des, new Object[]{value});
					}
				}
			} catch (Exception e) {
			}
			
		}
	}
}