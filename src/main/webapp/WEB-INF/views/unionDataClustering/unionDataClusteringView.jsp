<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>数据聚类(联表)</title>
</head>
<body>
<div id ="unionDataClusteringPanel"></div>
	<script type="text/javascript">
		Ext.onReady(function() {
			 Ext.Loader.setPath('Bjfu.unionDataClustering', Global_Path + 'module/unionDataClustering');
	  	 	 Ext.Loader.setConfig({
		    	enabled: true,
		    	paths: {
		    		'Bjfu.unionDataClustering.view.DataDisplayView': Global_Path + 'module/unionDataClustering/view/DataDisplayView.js',
		    		'Bjfu.unionDataClustering.view.TableDisplayView': Global_Path + 'module/unionDataClustering/view/TableDisplayView.js',
		    		'Bjfu.unionDataClustering.view.QueryData': Global_Path + 'module/unionDataClustering/view/QueryData.js',
		    		'Bjfu.unionDataClustering.view.KMeans': Global_Path + 'module/unionDataClustering/view/KMeans.js'
		    	}
			 });
		  	 	
			var unionDataClusteringView = Ext.create('Bjfu.unionDataClustering.view.DataDisplayView',{
									id :'unionDataClusteringId',
									region:"center",
									width:'80%',
									autoShow : true,
									height:400
								});	
			var tableDisplayView = Ext.create('Bjfu.unionDataClustering.view.TableDisplayView',{
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
				title:'数据聚类',
				width:'100%',
				height:'100%',
				layout:'border',
				items:[tableDisplayView,unionDataClusteringView],
				renderTo:'unionDataClusteringPanel',
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