<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>层次聚类</title>
</head>
<body>
<div id ="hierarchicalClusteringPanel"></div>
	<script type="text/javascript">
		Ext.onReady(function() {
			 Ext.Loader.setPath('Bjfu.hierarchicalClustering', Global_Path + 'module/hierarchicalClustering');
	  	 	 Ext.Loader.setConfig({
		    	enabled: true,
		    	paths: {
		    		'Bjfu.hierarchicalClustering.view.DataDisplayView': Global_Path + 'module/hierarchicalClustering/view/DataDisplayView.js',
		    		'Bjfu.hierarchicalClustering.view.TableDisplayView': Global_Path + 'module/hierarchicalClustering/view/TableDisplayView.js',
		    		'Bjfu.hierarchicalClustering.view.QueryData': Global_Path + 'module/hierarchicalClustering/view/QueryData.js',
		    		'Bjfu.hierarchicalClustering.view.AllTableDisplayView': Global_Path + 'module/hierarchicalClustering/view/AllTableDisplayView.js'
		    	}
			 });
		  	 	
			var dataClusteringView = Ext.create('Bjfu.hierarchicalClustering.view.DataDisplayView',{
									id :'dataClusteringId',
									region:"east",
									width:'60%',
									autoShow : true,
									height:400
								});	
			var tableDisplayView = Ext.create('Bjfu.hierarchicalClustering.view.TableDisplayView',{
				id :'tableDisplayId',
				title:'资源组',
				region:"west",
			    collapsible:true,
			    split:true,//显示分隔条  
				width:'20%',
				autoShow : true,
				height:400
			});						
			var allTableDisplayView = Ext.create('Bjfu.hierarchicalClustering.view.AllTableDisplayView',{
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
				title:'层次聚类',
				width:'100%',
				height:'100%',
				layout:'border',
				items:[tableDisplayView,allTableDisplayView,dataClusteringView],
				renderTo:'hierarchicalClusteringPanel',
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