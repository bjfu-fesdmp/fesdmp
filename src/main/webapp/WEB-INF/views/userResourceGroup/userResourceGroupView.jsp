<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>资源组管理员设置</title>
</head>
<body>
<div id ="userResourceGroupPanel"></div>
	<script type="text/javascript">
		Ext.onReady(function() {
			 Ext.Loader.setPath('Bjfu.userResourceGroup', Global_Path + 'module/userResourceGroup');
	  	 	 Ext.Loader.setConfig({
		    	enabled: true,
		    	paths: {
		    		'Bjfu.userResourceGroup.view.UserView': Global_Path + 'module/userResourceGroup/view/UserView.js',
		    		'Bjfu.userResourceGroup.view.QueryUser' : Global_Path + 'module/userResourceGroup/view/QueryUser.js',
		    		'Bjfu.userResourceGroup.view.ResourceGroupView':Global_Path + 'module/userResourceGroup/view/ResourceGroupView.js',
		    		'Bjfu.resourceGroup.model.ResourceGroup':Global_Path + 'module/resourceGroup/model/ResourceGroup.js',
		    		'Bjfu.userResourceGroup.view.AddResourceGroupForUser':Global_Path + 'module/userResourceGroup/view/AddResourceGroupForUser.js'
		    	}
			 });
		  	 	
				var sysuserView = Ext.create('Bjfu.userResourceGroup.view.UserView',{
					id :'userViewId',
					title:'用户列表',
					width:'40%',
					autoShow : true,
					region:"west",
					height:400
				});	
			var resourceGroupView = Ext.create('Bjfu.userResourceGroup.view.ResourceGroupView',{
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
				items:[sysuserView,resourceGroupView],
				renderTo:'userResourceGroupPanel',
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
