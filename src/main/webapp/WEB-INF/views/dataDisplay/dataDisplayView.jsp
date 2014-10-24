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
		    		'Bjfu.dataDisplay.view.TableDisplayView': Global_Path + 'module/dataDisplay/view/TableDisplayView.js',
		    		'Bjfu.dataDisplay.view.FileUpload': Global_Path + 'module/dataDisplay/view/FileUpload.js',
		    		'Bjfu.dataDisplay.view.QueryData': Global_Path + 'module/dataDisplay/view/QueryData.js',
		    		'Bjfu.dataDisplay.view.ModifyData': Global_Path + 'module/dataDisplay/view/ModifyData.js'
		    	}
			 });
		  	 	
			var dataDisplayView = Ext.create('Bjfu.dataDisplay.view.DataDisplayView',{
									id :'dataDisplayId',
									region:"center",
									width:'80%',
									autoShow : true,
									height:400
								});	
			var tableDisplayView = Ext.create('Bjfu.dataDisplay.view.TableDisplayView',{
				id :'tableDisplayId',
				title:'数据表',
				region:"west",
			    collapsible:true,
			    split:true,//显示分隔条  
				width:'20%',
				autoShow : true,
				height:400
			});						
			
			
			Ext.create('Ext.panel.Panel',{
				autoRender : true,
				title:'数据展示',
				width:'100%',
				height:'100%',
				layout:'border',
				items:[tableDisplayView,dataDisplayView],
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