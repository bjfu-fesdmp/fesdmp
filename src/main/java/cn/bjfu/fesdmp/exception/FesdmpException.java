/** 
 * Project Name:fesdmp 
 * File Name:FesdmpException.java 
 * Package Name:cn.bjfu.fesdmp.exception 
 * Date:2014年7月8日 上午9:56:55 
 * Copyright (c) 2014, zhangzhaoyu0524@163.com All Rights Reserved. 
 * 
*/  
  
package cn.bjfu.fesdmp.exception;  
/** 
 * ClassName:FesdmpException <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason:   TODO ADD REASON. <br/> 
 * Date:     2014年7月8日 上午9:56:55 <br/> 
 * @author   zhangzhaoyu 
 * @version   
 * @since    JDK 1.7 
 * @see       
 */
public class FesdmpException extends RuntimeException {
	/**
	 * serialVersionUID:TODO
	 * @since JDK 1.7
	 */
	private static final long serialVersionUID = 5836475828798562426L;

	public FesdmpException() {
		super();
	}
	
	public FesdmpException(String message) {
		 super(message);
	}
	
	public FesdmpException(String message, Throwable cause) {
        super(message, cause);
    }
	
	public FesdmpException(String message, Throwable cause,
            boolean enableSuppression,
            boolean writableStackTrace) {
		//super(message, cause, enableSuppression, writableStackTrace);
	}
	
	public FesdmpException(Throwable cause) {
        super(cause);
    }
}
 