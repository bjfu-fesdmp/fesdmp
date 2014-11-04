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
		    		'Bjfu.role.view.AddRole': Global_Path + 'module/role/view/AddRole.js',
		    		'Bjfu.role.model.Role': Global_Path + 'module/role/model/Role.js',
		    		'Bjfu.role.view.ResourceGroupView':Global_Path + 'module/role/view/ResourceGroupView.js',
		    		'Bjfu.resourceGroup.model.ResourceGroup':Global_Path + 'module/resourceGroup/model/ResourceGroup.js',
		    		'Bjfu.role.view.AddResourceGroupForRole':Global_Path + 'module/role/view/AddResourceGroupForRole.js'
		    	}
			 });
		  	 	
			var roleView = Ext.create('Bjfu.role.view.RoleView',{
									title:'角色管理',
									id :'roleViewId',
									width:'40%',
									region:"west",
								    collapsible:true,
									autoShow : true,
								    split:true,//显示分隔条  
									//autoRender : true,
									height:400
								});	
			var resourceGroupView = Ext.create('Bjfu.role.view.ResourceGroupView',{
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
				items:[roleView,resourceGroupView],
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