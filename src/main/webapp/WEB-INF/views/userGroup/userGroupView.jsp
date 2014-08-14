<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>用户组管理</title>
</head>
<body>
<div id ="userGroupPanel"></div>
	<script type="text/javascript">
		Ext.onReady(function() {
			 Ext.Loader.setPath('Bjfu.userGroup', Global_Path + 'module/userGroup');
	  	 	 Ext.Loader.setConfig({
		    	enabled: true,
		    	paths: {
		    		'Bjfu.userGroup.view.UserGroupView': Global_Path + 'module/userGroup/view/UserGroupView.js',
		    		'Bjfu.userGroup.view.QueryUserGroup' : Global_Path + 'module/userGroup/view/QueryUserGroup.js',
		    		'Bjfu.userGroup.view.AddUserGroup' : Global_Path + 'module/userGroup/view/AddUserGroup.js'
		    	}
			 });
		  	 	
			var sysuserGroupView = Ext.create('Bjfu.userGroup.view.UserGroupView',{
									id :'userGroupViewId',
									width:'100%',
									autoShow : true,
									//autoRender : true,
									height:400
								});	
											
			Ext.create('Ext.panel.Panel',{
				autoRender : true,
				title:'用户组管理',
				width:'100%',
				height:'100%',
				layout:'fit',
				items:[sysuserGroupView],
				renderTo:'userGroupPanel',
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
