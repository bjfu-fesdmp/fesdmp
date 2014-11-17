package cn.bjfu.fesdmp.web.annotation;  

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MethodRecordLog {
	
	String moduleName();
	String operateType();
	String bussinessType();
	String desc() default "描述信息";
}
 