<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>用户管理</title>
</head>
<body>
<div id ="userPanel"></div>
	<script type="text/javascript">
		Ext.onReady(function() {
			 Ext.Loader.setPath('Bjfu.user', Global_Path + 'module/user');
	  	 	 Ext.Loader.setConfig({
		    	enabled: true,
		    	paths: {
		    		'Bjfu.user.view.AddUser': Global_Path + 'module/user/view/AddUser.js',
		    		'Bjfu.user.view.UserView': Global_Path + 'module/user/view/UserView.js',
		    		'Bjfu.user.view.ModifyUser': Global_Path + 'module/user/view/ModifyUser.js',
		    		'Bjfu.user.view.QueryUser' : Global_Path + 'module/user/view/QueryUser.js'
		    	}
			 });
		  	 	
			var sysuserView = Ext.create('Bjfu.user.view.UserView',{
									id :'userViewId',
									title:'用户管理',
									width:'100%',
									autoShow : true,
									region:"west",
									height:400
								});	
	
			Ext.create('Ext.panel.Panel',{
				autoRender : true,
				width:'100%',
				height:'100%',
				layout:'border',
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