<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>角色管理</title>
</head>
<body>
<div id ="rolePanel"></div>
	<script type="text/javascript">
		Ext.onReady(function() {
			 Ext.Loader.setPath('Bjfu.role', Global_Path + 'module/role');
	  	 	 Ext.Loader.setConfig({
		    	enabled: true,
		    	paths: {
		    		'Bjfu.role.view.RoleView': Global_Path + 'module/role/view/RoleView.js',
		    		'Bjfu.role.view.QueryRole': Global_Path + 'module/role/view/QueryRole.js',
		    		'Bjfu.role.view.AddRole': Global_Path + 'module/role/view/AddRole.js'
		    	}
			 });
		  	 	
			var roleView = Ext.create('Bjfu.role.view.RoleView',{
									id :'roleViewId',
									width:'100%',
									autoShow : true,
									//autoRender : true,
									height:400
								});	
											
			Ext.create('Ext.panel.Panel',{
				autoRender : true,
				title:'角色管理',
				width:'100%',
				height:'100%',
				layout:'fit',
				items:[roleView],
				renderTo:'rolePanel',
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