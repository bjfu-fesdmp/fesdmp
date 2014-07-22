<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>日志管理</title>
    
	<script type="text/javascript">
	
  	    Ext.Loader.setPath('Boco.log','/smp/module/manage/log');
		
		Ext.onReady(function(){
		
			var syslogListView = Ext.create('Boco.log.view.logListView',{
			                                  
												width:'100%',
												height:400
											});	
											
			Ext.create('Ext.panel.Panel',{
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
			    }
			});
		});
	</script>
</head>
<body>
<div id ="systemLogPanel"></div>
</body>
</html>