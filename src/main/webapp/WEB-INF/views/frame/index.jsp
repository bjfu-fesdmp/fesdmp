<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title><fmt:message key="sys.name"/></title>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/extjs/ext-theme-neptune/ext-theme-neptune-all.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/resources/extjs/css/style.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/resources/extjs/css/buttons.css">
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/extjs/js/ext-all.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/extjs/js/ext-lang-zh_CN.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/resources/extjs/jquery/jquery.min.js"></script>
  	<script type="text/javascript" src="<%=request.getContextPath() %>/resources/extjs/jquery/jquery.knob.js"></script>
  	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/extjs/jquery/jquery.hotkeys.min.js"></script>
  	<script type="text/javascript" src="<%=request.getContextPath()%>/module/common/customVTypes.js"></script>
  	<script type="text/javascript" src="<%=request.getContextPath()%>/module/common/DictionaryStatus.js"></script>
	<script type="text/javascript">
	Global_Path = "<%=request.getContextPath()%>/";
	//Ext.Loader.setPath('Bjfu.tramp', rootUrl + "/demo/js");
	$(document).bind("keydown", "esc", userLogout);
		
	function userLogout() {
			Ext.MessageBox.confirm('退出确认', '确认退出并注销当前用户？', function(button) {
				if ('yes' == button) {
  				Ext.Ajax.request({
					url : Global_Path + 'logout',
					method : 'post',
					success : function(response, options) {
						window.close();
						location.href = Global_Path;
					},
					failure : function(response, options) {
						Ext.MessageBox.show({
					           title: 'Error',
					           msg: '系统退出异常！',
					           buttons: Ext.MessageBox.OK,
					           icon: Ext.MessageBox.ERROR
					       });
					}
				})				
				}
			});
	}
	
	Ext.onReady(function () {
		<%-- Ext.Loader.setConfig({
		      enabled: true,
		      paths: {
		      	   //配置的格式是  组件的访问全路径(例如Ext.form.Panel) : 该组件在工程中的绝对路径,这里文档中写的是文件所在文件夹的相对路径,通过实验,这样是不行的,只能是绝对路径的文件全路径
		          'MyTreePanel': '<%=request.getContextPath()%>/demo/js/MyTreePanel.js'
		      }
		    });
		
		function showResult(msg){
		   Ext.MessageBox.alert('提示', msg);
		}; --%>
		
		function loadUrls(){
       		centers.getLoader().load(Ext.create('Ext.ComponentLoader',{
           	  	url : Global_Path + "statistic/portal",
           	    scripts : true,                             
           	    nocache : true                            
            }));
	  	}
	
		
		Ext.create('Ext.util.KeyMap',{
			target: Ext.getBody(),
		    binding: [{
		        key: Ext.EventObject.M,
		        ctrl:true,
		        fn: function(keyCode, e) {
		        	if(top.getCollapsed() == false){
		        		top.collapse();
		        	}else{
		        		top.expand()
		        	}
		        }
		    }]
	  	});
		
		// 记录当前页面的状态
		Ext.state.Manager.setProvider(Ext.create("Ext.state.CookieProvider"));
		Ext.BLANK_IMAGE_URL = '<%=request.getContextPath()%>/resources/extjs/images/s.gif';
		Ext.onReady(function(){
			Ext.Ajax.request({
				url : Global_Path+'sysuser/checkIfAdmin',
				success : function(response) {
					var result = Ext.decode(response.responseText);
					if(!result.success){
						Ext.getCmp('systemManager').hide();
						Ext.getCmp('resourcrGroupManager').hide();
						Ext.Ajax.request({
							url : Global_Path+'sysuser/checkIfNotIsTemporaryManager',
							success : function(response) {
								var result = Ext.decode(response.responseText);
								if(result.success){
									Ext.getCmp('indexManager').hide();
									
								}
							}
						});
					}
				}
			});
			
			});
		var tb = Ext.create('Ext.toolbar.Toolbar' , {
			style : {
				backgroundColor : '#157FCC'
			},
			border : true,
			items : [' ',{
				text : '首页',
				    handler: function () {
					Ext.getCmp('centerPanel').getLoader().load({
					autoLoad : true,
					url: Global_Path + "statistic/portal",
 					scripts : true,
 					loadMask : true,
 					noCache : true	
				});
                    }
			},' ',{
				text : '聚类分析',
				menu: {
			            items: [
			{
				                 
				                    text: '划分聚类',
									handler : function() {
										Ext.getCmp('centerPanel').getLoader().load({
											autoLoad : true,
	    									url: Global_Path + "dataClustering/listView",
	            	 						scripts : true,
	            	 						loadMask : true,
	            	 						noCache : true	
	    								});
									}
	                
				                },
//				{
//					                 
//					                    text: '数据聚类(联表)',
//										handler : function() {
//											Ext.getCmp('centerPanel').getLoader().load({
//												autoLoad : true,
//		    									url: Global_Path + "dataClustering/unionListView",
//		            	 						scripts : true,
//		            	 						loadMask : true,
//		            	 						noCache : true	
//		    								});
//										}
//		                
//					                },
					{
						                 
						                    text: '层次聚类',
											handler : function() {
												Ext.getCmp('centerPanel').getLoader().load({
													autoLoad : true,
			    									url: Global_Path + "dataClustering/hierarchicalClusteringListView",
			            	 						scripts : true,
			            	 						loadMask : true,
			            	 						noCache : true	
			    								});
											}
			                
						                }
			            ]
			        }
			},' ',{
				text : '数据管理',
				menu: {
			            items: [
			                {
			                 
			                    text: '数据展示',
								handler : function() {
									Ext.getCmp('centerPanel').getLoader().load({
										autoLoad : true,
    									url: Global_Path + "dataDisplay/listView",
            	 						scripts : true,
            	 						loadMask : true,
            	 						noCache : true	
    								});
								}
                
			                }
			            ]
			        }
			},' ',{
				text : '指标与权限管理',
				id : 'indexManager',
				menu: {
			            items: [{
				       				text: '区域设置',
				       				handler: function () {
									Ext.getCmp('centerPanel').getLoader().load({
									autoLoad : true,
	    							url: Global_Path + "location/listView",
                	 				scripts : true,
                	 				loadMask : true,
                	 				noCache : true	
	    							});
				       					}
				      				 },{
									text: '资源组与指标资源管理',
									handler:  function () {
									Ext.getCmp('centerPanel').getLoader().load({
									autoLoad : true,	
									url: Global_Path + "indexresource/listView",
									scripts : true,
									loadMask : true,
									noCache : true
									});
									} 
	       						 },{
							    	text: '资源组管理员设置',
									id : 'resourcrGroupManager',
							  		handler: function () {
									Ext.getCmp('centerPanel').getLoader().load({
									autoLoad : true,
				    				url: Global_Path + "sysuser/userResourceGroupListView",
			                	 	scripts : true,
			                	 	loadMask : true,
			                	 	noCache : true	
				    				});
							  			 }
			              		 },{
				       				text: '用户指标管理设置',
				       				handler: function () {
									Ext.getCmp('centerPanel').getLoader().load({
									autoLoad : true,
	    							url: Global_Path + "sysuser/userIndexResourceListView",
                	 				scripts : true,
                	 				loadMask : true,
                	 				noCache : true	
	    							});
				       					}
				      				 }
			           				 ]
			        }
			},' ',{
				text : '系统管理',
				id : 'systemManager',
				menu: {
			            items: [
			                {
			                    text: '日志管理',
								handler : function() {
									Ext.getCmp('centerPanel').getLoader().load({
										autoLoad : true,
					    				url: Global_Path + "syslog/listView",
				                	 	scripts : true,
				                	 	loadMask : true,
				                	 	noCache : true	
					    			});
								}
			                },{
				                    text: '用户管理',
				                    handler: function () {
										Ext.getCmp('centerPanel').getLoader().load({
										autoLoad : true,
	    								url: Global_Path + "sysuser/listView",
                	 					scripts : true,
                	 					loadMask : true,
                	 					noCache : true	
	    							});
				                    }
				                }
			            ]
			        }
			},'->',{
            	text : '退出系统',
            	handler : userLogout
            }]
			
		});
		
		//上部面板
		var top = Ext.create('Ext.panel.Panel',{
			region: 'north',
          	border : false,
			frame : false,
           	items : [Ext.create('Ext.panel.Panel',{
           	  	border : false,
           	  	loader : Ext.create('Ext.ComponentLoader',{
           	 	url: Global_Path + "top",
           	 	scripts : true,
           	 	noCache : true,
           	 	autoLoad : true
           	  }),
           	 bbar : tb
           })],
           listeners : {
           	'expand' : function(p,opt){
           		loadUrls();
           	},
           	'collapse' : function(p,eOpts){
           		loadUrls();
           	}
           }
		});
		
		var centers = Ext.create('Ext.panel.Panel', {
           	region: 'center',  
           	id : 'centerPanel',
          	border : false,
           	autoScroll : true,
           	frame : false,
			loader : Ext.create('Ext.ComponentLoader',{
				autoLoad : true,     
	     	  	noCache: true,
           	  	url : Global_Path + "statistic/portal",                 
     	      	scripts: true            
            })
           /*  loader : {
            	url : Global_Path + "statistic/portal",  
           	    scripts: true,               
        	    autoLoad : true,     
        	    noCache: true,
        	    renderer : "html"
            }, 
           'beforeload': function() {
               Ext.getCmp('centerPanel').removeAll();
           }*/
		});
		var viewport = Ext.create('Ext.Viewport', {
       	 	autoRender : 'frameDiv',
       	 	layout : 'border',
       	 	items : [top, centers]
		});
		
	});
	</script>
</head>
<body>
<div id="frameDiv"></div> 
</body>
</html>
