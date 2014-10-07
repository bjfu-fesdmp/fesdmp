package cn.bjfu.fesdmp.web.jsonbean;

public class ExtJSFormResult {  
	   
    private boolean success;  
   
    public boolean isSuccess() {  
        return success;  
    }  
    public void setSuccess(boolean success) {  
        this.success = success;  
    }  
   
    public String toString(){  
        return "{success:"+this.success+"}";  
    }  
}  