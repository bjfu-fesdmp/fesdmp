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
		    		'Bjfu.userGroup.view.ModifyUserGroup' : Global_Path + 'module/userGroup/view/ModifyUserGroup.js',
		    		'Bjfu.userGroup.view.AddUserGroup' : Global_Path + 'module/userGroup/view/AddUserGroup.js',
		    		'Bjfu.userGroup.view.ResourceGroupView':Global_Path + 'module/userGroup/view/ResourceGroupView.js',
		    		'Bjfu.resourceGroup.model.ResourceGroup':Global_Path + 'module/resourceGroup/model/ResourceGroup.js',
		    		'Bjfu.userGroup.view.AddResourceGroupForUserGroup':Global_Path + 'module/userGroup/view/AddResourceGroupForUserGroup.js'
		    	}
			 });
		  	 	
			var sysuserGroupView = Ext.create('Bjfu.userGroup.view.UserGroupView',{
									id :'userGroupViewId',
									width:'40%',
									title:'用户组管理',
									autoShow : true,
									region:"west",
									height:400
								});	
			var resourceGroupView = Ext.create('Bjfu.userGroup.view.ResourceGroupView',{
				id :'resourceGroupViewId',
				title:'资源组管理',
				width:'60%',
				autoShow : true,
				region:"center",
				height:400
			});									
			Ext.create('Ext.panel.Panel',{
				autoRender : true,
				width:'100%',
				height:'100%',
				layout:'border',
				items:[sysuserGroupView,resourceGroupView],
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
