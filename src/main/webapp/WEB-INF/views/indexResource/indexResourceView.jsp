<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>指标管理</title>
</head>
<body>
<div id ="indexResourcePanel"></div>
	<script type="text/javascript">
		Ext.onReady(function() {
			 Ext.Loader.setPath('Bjfu.indexResource', Global_Path + 'module/indexResource');
			 Ext.Loader.setPath('Bjfu.resourceGroup', Global_Path + 'module/resourceGroup');
	  	 	 Ext.Loader.setConfig({
		    	enabled: true,
		    	paths: {
		    		'Bjfu.indexResource.view.IndexResourceListView': Global_Path + 'module/indexResource/view/IndexResourceListView.js',
		    		'Bjfu.indexResource.view.QueryIndexResource' : Global_Path + 'module/indexResource/view/QueryIndexResource.js',
		    		'Bjfu.indexResource.view.AddIndexResource' : Global_Path + 'module/indexResource/view/AddIndexResource.js',
		    		'Bjfu.indexResource.view.ModifyIndexResource' : Global_Path + 'module/indexResource/view/ModifyIndexResource.js',
		    		'Bjfu.resourceGroup.view.ResourceGroupView': Global_Path + 'module/resourceGroup/view/ResourceGroupView.js',
		    		'Bjfu.resourceGroup.view.QueryResourceGroup' : Global_Path + 'module/resourceGroup/view/QueryResourceGroup.js',
		    		'Bjfu.resourceGroup.view.AddResourceGroup' : Global_Path + 'module/resourceGroup/view/AddResourceGroup.js',
		    		'Bjfu.resourceGroup.view.ModifyResourceGroup' : Global_Path + 'module/resourceGroup/view/ModifyResourceGroup.js'
		    	}
			 });
		  	 	
			var indexResourceListView = Ext.create('Bjfu.indexResource.view.IndexResourceListView',{
									id :'indexResourceListViewId',
									region:"center",
									width:'70%',
									autoShow : true,
									height:400
								});	
			var resourceGroupView = Ext.create('Bjfu.resourceGroup.view.ResourceGroupView',{
				id :'resourceGroupViewId',
				title:'资源组管理',
				width:'30%',
				autoShow : true,
				region:"west",
			    collapsible:true,
			    split:true,//显示分隔条  
				height:400
			});				
			Ext.create('Ext.panel.Panel',{
				autoRender : true,
				title:'指标管理',
				width:'100%',
				height:'100%',
				layout:'border',
				items:[resourceGroupView,indexResourceListView],
				renderTo:'indexResourcePanel',
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