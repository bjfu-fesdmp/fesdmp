<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>日志管理</title>
    

</head>
<body>
<div id ="systemLogPanel"></div>
	<script type="text/javascript">
		Ext.onReady(function() {
			 Ext.Loader.setPath('Bjfu.log', Global_Path + 'module/log');
	  	 	 Ext.Loader.setConfig({
		    	enabled: true,
		    	paths: {
		    		'Bjfu.log.view.LogListView': Global_Path + 'module/log/view/LogListView.js',
		    		'Bjfu.log.view.QueryLog' : Global_Path + 'module/log/view/QueryLog.js'
		    	}
			 });
		  	 	
			var syslogListView = Ext.create('Bjfu.log.view.LogListView',{
									id :'logListViewId',
									width:'100%',
									autoShow : true,
									autoRender : true,
									height:400
								});	
											
			Ext.create('Ext.panel.Panel',{
				autoRender : true,
				title:'日志管理',
				width:'100%',
				height:'100%',
				layout:'fit',
				items:[syslogListView],
				renderTo:'systemLogPanel',
				listeners : {
					'boxready' : function(){
				 		this.updateBox(Ext.getCmp('centerPanel').getSize());
				 	}
					/* 'render' : function() {
						this.updateBox(Ext.getCmp('centerPanel').getSize());
					} */
			    }
			});
		});
	</script>
</body>
</html>