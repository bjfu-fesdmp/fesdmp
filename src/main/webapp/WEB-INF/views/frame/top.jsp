<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8" import="cn.bjfu.fesdmp.domain.sys.User" import="java.util.Date" import="java.text.SimpleDateFormat"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<script language="javascript">
  	
  	    dwr.engine.setAsync(true);      
        dwr.engine.setActiveReverseAjax(true);
        dwr.engine.setNotifyServerOnPageUnload(true);
        
		var user = '<s:property value="#session.user.userName"/>';
        DWRPushHelper.onPageLoad(user);
        
		function callback(content){ 
	   		Ext.create('Ext.ux.window.Notification', {
	  			title: '短消息通知',
		    	position: 'br',
		 		iconCls: 'ux-notification-icon-information',
		 		autoCloseDelay: 5000,
		 		spacing: 20,
		 		html: '<p style=" text-indent:2em; padding:5px; line-height:26px; font-family:\'微软雅黑\',Arial; font-size:14px;">' + content + '</p>'
			}).show();   
		}     
         
  	</script>
  </head>
<body class="smp_indexbody">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="smp_toptable">
  <tr>
    <td width="20">&nbsp;</td>
    <td width="617"><img src="<%=request.getContextPath() %>/resources/extjs/images/smplogo.png" width="617" height="70" /></td>
    <td>&nbsp;</td>
    <td width="410" align="center" class="smp_toptime"><img style="vertical-align: middle;" width="12px;" height="12px;" src="<%=request.getContextPath() %>/resources/extjs/images/uers.png"/>
    <%User nowUser=(User)request.getSession().getAttribute("user");
    Date date=new Date();
    SimpleDateFormat matter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String dates=matter.format(date);%>
    <span style="font-size:14px;">&nbsp;当前用户：<%=nowUser.getUserName() %> 登录时间：&nbsp; <%=dates%></span></td>
  </tr>
</table>
</body>
</html>
