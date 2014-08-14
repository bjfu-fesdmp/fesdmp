<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>资源组管理</title>
</head>
<body>
<div id ="resourceGroupPanel"></div>
	<script type="text/javascript">
		Ext.onReady(function() {
			 Ext.Loader.setPath('Bjfu.resourceGroup', Global_Path + 'module/resourceGroup');
	  	 	 Ext.Loader.setConfig({
		    	enabled: true,
		    	paths: {
		    		'Bjfu.resourceGroup.view.ResourceGroupView': Global_Path + 'module/resourceGroup/view/ResourceGroupView.js',
		    		'Bjfu.resourceGroup.view.QueryResourceGroup' : Global_Path + 'module/resourceGroup/view/QueryResourceGroup.js',
		    		'Bjfu.resourceGroup.view.AddResourceGroup' : Global_Path + 'module/resourceGroup/view/AddResourceGroup.js'
		    	}
			 });
		  	 	
			var resourceGroupView = Ext.create('Bjfu.resourceGroup.view.ResourceGroupView',{
									id :'resourceGroupViewId',
									width:'100%',
									autoShow : true,
									//autoRender : true,
									height:400
								});	
											
			Ext.create('Ext.panel.Panel',{
				autoRender : true,
				title:'资源组管理',
				width:'100%',
				height:'100%',
				layout:'fit',
				items:[resourceGroupView],
				renderTo:'resourceGroupPanel',
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
