<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>数据聚类(单表)</title>
</head>
<body>
<div id ="dataClusteringPanel"></div>
	<script type="text/javascript">
		Ext.onReady(function() {
			 Ext.Loader.setPath('Bjfu.dataClustering', Global_Path + 'module/dataClustering');
	  	 	 Ext.Loader.setConfig({
		    	enabled: true,
		    	paths: {
		    		'Bjfu.dataClustering.view.DataDisplayView': Global_Path + 'module/dataClustering/view/DataDisplayView.js',
		    		'Bjfu.dataClustering.view.TableDisplayView': Global_Path + 'module/dataClustering/view/TableDisplayView.js',
		    		'Bjfu.dataClustering.view.AllTableDisplayView': Global_Path + 'module/dataClustering/view/AllTableDisplayView.js',
		    		'Bjfu.dataClustering.view.QueryData': Global_Path + 'module/dataClustering/view/QueryData.js',
		    		'Bjfu.dataClustering.view.KMeans&KMedoids': Global_Path + 'module/dataClustering/view/KMeans&KMedoids.js'
		    	}
			 });
		  	 	
			var dataClusteringView = Ext.create('Bjfu.dataClustering.view.DataDisplayView',{
									id :'dataDisplayId',
									region:"east",
									width:'60%',
									autoShow : true,
									height:400
								});	
			var tableDisplayView = Ext.create('Bjfu.dataClustering.view.TableDisplayView',{
				id :'tableDisplayId',
				title:'资源组',
				region:"west",
			    collapsible:true,
			    split:true,//显示分隔条  
				width:'20%',
				autoShow : true,
				height:400
			});		
			var allTableDisplayView = Ext.create('Bjfu.dataClustering.view.AllTableDisplayView',{
				id :'allTableDisplayId',
				title:'数据表',
				region:"center",
			    collapsible:true,
			    split:true,//显示分隔条  
				width:'20%',
				autoShow : true,
				height:400
			});						
			
			
			Ext.create('Ext.panel.Panel',{
				autoRender : true,
				title:'划分聚类',
				width:'100%',
				height:'100%',
				layout:'border',
				items:[tableDisplayView,allTableDisplayView,dataClusteringView],
				renderTo:'dataClusteringPanel',
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