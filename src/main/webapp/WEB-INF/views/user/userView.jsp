<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>日志管理</title>
</head>
<body>
<div id ="userPanel"></div>
	<script type="text/javascript">
		Ext.onReady(function() {
			 Ext.Loader.setPath('Bjfu.user', Global_Path + 'module/user');
	  	 	 Ext.Loader.setConfig({
		    	enabled: true,
		    	paths: {
		    		'Bjfu.user.view.UserView': Global_Path + 'module/user/view/UserView.js',
		    		'Bjfu.user.view.QueryUser' : Global_Path + 'module/user/view/QueryUser.js'
		    	}
			 });
		  	 	
			var sysuserView = Ext.create('Bjfu.user.view.UserView',{
									id :'userViewId',
									width:'100%',
									autoShow : true,
									//autoRender : true,
									height:400
								});	
											
			Ext.create('Ext.panel.Panel',{
				autoRender : true,
				title:'用户管理',
				width:'100%',
				height:'100%',
				layout:'fit',
				items:[sysuserView],
				renderTo:'userPanel',
				listeners : {
					'boxready' : function(){
				 		this.updateBox(Ext.getCmp('centerPanel').getSize());
				 	}
			    }
			});
		});
	</script>
</body>
</html>