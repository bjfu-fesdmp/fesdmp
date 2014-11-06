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
		    		'Bjfu.user.view.QueryUser' : Global_Path + 'module/user/view/QueryUser.js',
		    		'Bjfu.user.view.IndexResourceListView' : Global_Path + 'module/user/view/IndexResourceListView.js',
		    		'Bjfu.indexResource.model.IndexResource' : Global_Path + 'module/indexResource/model/IndexResource.js',
		    		'Bjfu.user.view.AddIndexResourceForUser' : Global_Path + 'module/user/view/AddIndexResourceForUser.js'
		    	}
			 });
		  	 	
			var sysuserView = Ext.create('Bjfu.user.view.UserView',{
									id :'userViewId',
									title:'用户管理',
									width:'70%',
									autoShow : true,
									region:"west",
									height:400
								});	
			var indexResourceListView = Ext.create('Bjfu.user.view.IndexResourceListView',{
				id :'indexResourceListViewId',
				title:'指标管理',
				region:"center",
				width:'30%',
				autoShow : true,
				height:400
			});		
			Ext.create('Ext.panel.Panel',{
				autoRender : true,
				width:'100%',
				height:'100%',
				layout:'border',
				items:[sysuserView,indexResourceListView],
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