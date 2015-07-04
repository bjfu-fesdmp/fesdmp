<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>区域设置</title>
</head>
<body>
<div id ="locationPanel"></div>
	<script type="text/javascript">
		Ext.onReady(function() {
			 Ext.Loader.setPath('Bjfu.location', Global_Path + 'module/location');
	  	 	 Ext.Loader.setConfig({
		    	enabled: true,
		    	paths: {
		    		'Bjfu.location.view.LocationListView': Global_Path + 'module/location/view/LocationListView.js',
		    		'Bjfu.location.view.QueryLocation' : Global_Path + 'module/location/view/QueryLocation.js'
		    	}
			 });
		  	 	
			var locationListView = Ext.create('Bjfu.location.view.LocationListView',{
									id :'locationListViewId',
									width:'100%',
									autoShow : true,
									//autoRender : true,
									height:400
								});	
											
			Ext.create('Ext.panel.Panel',{
				autoRender : true,
				title:'区域管理',
				width:'100%',
				height:'100%',
				layout:'fit',
				items:[locationListView],
				renderTo:'locationPanel',
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