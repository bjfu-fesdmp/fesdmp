<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
  <head>
  	<link href="<%=request.getContextPath() %>/resources/extjs/css/style.css" rel="stylesheet" type="text/css" />
  	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/extjs/jquery/jquery.min.js"></script>
  	<script type="text/javascript">
  	var contextPath = "<%=request.getContextPath()%>";
	$(document).ready(function(){
	  	$("#userName").focus();
	    $("#userName").keydown(function(event) {
	        if ($.trim($("#userName").val()) == '') {
	        	$("#userName").focus();
	        } else {
		        if (event.which == "13") {
		            $("#password").focus();
		        }		        
	        }
	    });
	    $("#password").keydown(function(event) {
	        if ($.trim($("#password").val()) == '') {
	        	$("#password").focus();
	        } else {
		        if (event.which == "13") {
	            	$("#loginButton").trigger("click");
		        }		        
	        }
	    });
	    $("#loginButton").click(function () {
	    	if ($.trim($("#userName").val()) != '' && $.trim($("#password").val()) != '') {
			    $('#loginForm').submit();
	        } else {
	    		$("#content").html("<div>用户名或密码不允许为空!</div>");
	    	}
	    });
    });
  	</script>
  </head>
<body class="smp_loginbody">
<table border="0" align="center" cellpadding="0" cellspacing="0" class="smp_logintable">
  <tr>
    <td width="463" height="119">&nbsp;</td>
    <td width="337">&nbsp;</td>
  </tr>
  <tr>
    <td height="236" valign="top">
    
    <table width="86%" border="0" align="center" cellpadding="8" cellspacing="0">
      <tr>
        <td height="40" class="spm_logintitle">系统公告</td>
      </tr>
      <tr>
        <td>&nbsp;&nbsp;森林生态站数据管理平台（Forest Ecology Station Data Management Platform 简称：FESDMF）是实现森林生态站观测指标管理为目的的，以数据的预处理，导入、导出和数据挖掘和分析为主要功能的综合数据管理平台。<br />
          <br />
          <!--  <a href="#" class="logindownload">补丁下载1</a>&nbsp;&nbsp;<a href="#" class="logindownload">补丁下载2</a>--></td>
      </tr>
    </table>
    </td>
    <td valign="top">
    <form action="<c:url value='/login'/>" method="post" id="loginForm">
   	<table width="90%" border="0" align="center" cellpadding="8" cellspacing="0">
      <tr>
        <td height="40" id="content" class="spm_logintitle">
        <c:if test="${! empty errorMsg }">
        	消息提示：<c:out value="${errorMsg }"/>
        </c:if>
        </td>
      </tr>
      <tr>
        <td height="40"><label for="textfield"></label><input type="text" name="userLoginName" id="userName" class="spm_loginuersinput"/></td>
      </tr>
      <tr>
        <td height="40"><input type="password" name="password" id="password" class="spm_loginpwinput"/></td>
      </tr>
      <tr>
        <td height="40"><input type="button" name="loginButton" id="loginButton" value="登 录" class="spm_loginbutton"/></td>
      </tr>
    </table>
    </form> 
    </td>
  </tr>
  <tr>
    <td colspan="2" align="center" class="spm_logincopyright">版权所有北京林业大学信息学院2014年</td>
  </tr>
</table>
</body>
</html>
