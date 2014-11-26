<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>用户数据管理设置</title>
</head>
<body>
<div id ="userIndexResourcePanel"></div>
	<script type="text/javascript">
		Ext.onReady(function() {
			 Ext.Loader.setPath('Bjfu.userIndexResource', Global_Path + 'module/userIndexResource');
	  	 	 Ext.Loader.setConfig({
		    	enabled: true,
		    	paths: {
		    		'Bjfu.userIndexResource.view.UserView': Global_Path + 'module/userIndexResource/view/UserView.js',
		    		'Bjfu.userIndexResource.view.QueryUser' : Global_Path + 'module/userIndexResource/view/QueryUser.js',
		    		'Bjfu.userIndexResource.view.IndexResourceListView' : Global_Path + 'module/userIndexResource/view/IndexResourceListView.js',
		    		'Bjfu.indexResource.model.IndexResource' : Global_Path + 'module/indexResource/model/IndexResource.js',
		    		'Bjfu.userIndexResource.view.AddIndexResourceForUser' : Global_Path + 'module/userIndexResource/view/AddIndexResourceForUser.js',
		    		'Bjfu.Ext.ux.TreePicker' : Global_Path + 'resources/extjs/ux/TreePicker.js'
		    	}
			 });
		  	 	
			var sysuserView = Ext.create('Bjfu.userIndexResource.view.UserView',{
									id :'userViewId',
									title:'用户列表',
									width:'40%',
									autoShow : true,
									region:"west",
									height:400
								});	
			var indexResourceListView = Ext.create('Bjfu.userIndexResource.view.IndexResourceListView',{
				id :'indexResourceListViewId',
				title:'指标管理',
				region:"center",
				width:'60%',
				autoShow : true,
				height:400
			});		
			Ext.create('Ext.panel.Panel',{
				autoRender : true,
				width:'100%',
				height:'100%',
				layout:'border',
				items:[sysuserView,indexResourceListView],
				renderTo:'userIndexResourcePanel',
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