<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>数据展示</title>
</head>
<body>
<div id ="dataDisplayPanel"></div>
	<script type="text/javascript">
		Ext.onReady(function() {
			 Ext.Loader.setPath('Bjfu.dataDisplay', Global_Path + 'module/dataDisplay');
	  	 	 Ext.Loader.setConfig({
		    	enabled: true,
		    	paths: {
		    		'Bjfu.dataDisplay.view.DataDisplayView': Global_Path + 'module/dataDisplay/view/DataDisplayView.js',
		    		'Bjfu.dataDisplay.view.FileUpload': Global_Path + 'module/dataDisplay/view/FileUpload.js'
		    	}
			 });
		  	 	
			var dataDisplayView = Ext.create('Bjfu.dataDisplay.view.DataDisplayView',{
									id :'dataDisplayId',
									width:'100%',
									autoShow : true,
									//autoRender : true,
									height:400
								});	
											
			Ext.create('Ext.panel.Panel',{
				autoRender : true,
				title:'数据展示',
				width:'100%',
				height:'100%',
				layout:'fit',
				items:[dataDisplayView],
				renderTo:'dataDisplayPanel',
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