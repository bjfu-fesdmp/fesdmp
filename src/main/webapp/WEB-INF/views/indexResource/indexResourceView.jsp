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
	  	 	 Ext.Loader.setConfig({
		    	enabled: true,
		    	paths: {
		    		'Bjfu.indexResource.view.IndexResourceListView': Global_Path + 'module/indexResource/view/IndexResourceListView.js',
		    		'Bjfu.indexResource.view.QueryIndexResource' : Global_Path + 'module/indexResource/view/QueryIndexResource.js',
		    		'Bjfu.indexResource.view.AddIndexResource' : Global_Path + 'module/indexResource/view/AddIndexResource.js',
		    		'Bjfu.indexResource.view.ModifyIndexResource' : Global_Path + 'module/indexResource/view/ModifyIndexResource.js'
		    	}
			 });
		  	 	
			var indexResourceListView = Ext.create('Bjfu.indexResource.view.IndexResourceListView',{
									id :'indexResourceListViewId',
									width:'100%',
									autoShow : true,
									//autoRender : true,
									height:400
								});	
											
			Ext.create('Ext.panel.Panel',{
				autoRender : true,
				title:'指标管理',
				width:'100%',
				height:'100%',
				layout:'fit',
				items:[indexResourceListView],
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