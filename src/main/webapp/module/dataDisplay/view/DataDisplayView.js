Ext.define('Bjfu.dataDisplay.view.DataDisplayView',{
	extend : 'Ext.grid.Panel',
	id:'dataDisplayView',
	alias:'widget.DataDisplayView',
	forceFit : true,
	layout : 'fit',
    autoScroll: true,
	layoutConfig : {
		animate : true
	},
	search_cache: null,	  //用于分页时缓存高级查询条件
	split : true,
	overflowY : 'scroll', //只显示上下滚动的滚动条
	overflowX : 'hidden',
	selType : 'checkboxmodel',	// 单选，复选框
	requires : ['Bjfu.dataDisplay.model.DataDisplay'],
	
	initComponent : function() {
		var me = this;
		var gridStore = Ext.create('Ext.data.Store', {
			model : 'Bjfu.dataDisplay.model.DataDisplay',
			pageSize : 25,
			proxy : {
				type : 'ajax',
				actionMethods: {
	                create : 'POST',
	                read   : 'POST', // by default GET
	                update : 'POST',
	                destroy: 'POST'
				},
				url : Global_Path+'dataDisplay/dataDisplayList',
				reader : {
					type : 'json',
					root : 'result',
					idProperty : 'id',
					totalProperty : 'pageCount'
				}
			},
			listeners : {
				
				'beforeload': function(store,record, operation, eOpts) {
					
					if (me.search_cache != null) {
						Ext.apply(store.proxy.extraParams, { 
							searchJson : me.search_cache
						});
					} else {
						Ext.apply(store.proxy.extraParams, {
							searchJson : ""
						});
					}
					
					
				}
			},
			autoLoad : true
		}); 

		Ext.apply(me, {
			store : gridStore,
			forceFit:true,
			columns : [
				{
					text : '数据编号',
			        dataIndex : 'id',
			        hidden : true
				},
				Ext.create('Ext.grid.RowNumberer',{
			          		header : '序号',
			          		align: 'left',
			          		width : 60
			    }), 
			    {
			        text : '时间',
			        dataIndex : 'time',
			        width : '10%'
			    }, {
			        text : '站点',
			        dataIndex : 'station',
			        width : '10%'
			    },{
			        text : '数据值',
			        dataIndex : 'data',
			        width : '10%'
			    },{
			        text : '单位',
			        dataIndex : 'unit',
			        width : '10%'
			    }
			],
			tbar : [{
				text: '月平均柱状图',
				  id:'columnGraph',
				  scope:this,
		          icon:Global_Path+'/resources/extjs/images/up2.gif',
		          handler : function(btn){
			        	var tableName=gridStore.baseParams.tableName;
	                  	var grid = btn.up('gridpanel').store;
	                	var ids = [];
	                	grid.each(function (record) {  
	                		ids.push(record.get('id')); 
	                	}); 
			      			Ext.Ajax.request({
			      				url : Global_Path+'sysuser/checkFunctionIfForbid',
			      				params:{
			      					tableName:tableName
			      					},
			      				success : function(response,record) {
			      					var result = Ext.decode(response.responseText);
			      					if(result.success){
			      						Ext.Msg.alert('提示','您并没有获得该权限');
			      						return;
			      					}
			      					else{
			      			    		var monthStore = Ext.create('Ext.data.Store', {
			      			    			fields : ['time', 'data'],
			      			    			proxy : {
			      			    				type : 'ajax',
			      			    				actionMethods : {
			      			    					create : 'POST',
			      			    					read : 'POST', // by default GET
			      			    					update : 'POST',
			      			    					destroy : 'POST'
			      			    				},
			      			    				url : Global_Path + 'dataClustering/monthAve',
			      			    				reader : {
			      			    					type : 'json',
			      			    					root : 'result',
			      			    					idProperty : 'name',
			      			    					totalProperty : 'pageCount'
			      			    				}
			      			    			},
			      			    			autoLoad:false
			      			    		});

			      						var mchart = new Ext.chart.Chart({
			      				            style: 'background:#fff',
			      				            animate: true,
			      				            shadow: true,
			      				            insetPadding: 60,
			      						    width: 480,
			      						    height: 320,
			      						    store: monthStore,
			      						    renderTo: Ext.getBody(),
			      						  axes: [{
			      			                type: 'Numeric',
			      			                minimum: 0,
			      			                position: 'left',
			      			                fields: ['data'],
			      			                title: '数值',
			      			                grid: true,
			      			                label: {
			      			                    renderer: Ext.util.Format.numberRenderer('0,0'),
			      			                    font: '10px Arial'
			      			                }
			      			            }, {
			      			                type: 'Category',
			      			                position: 'bottom',
			      			                fields: ['time'],
			      			                title: 'Month of the Year',
			      			                label: {
			      			                    font: '11px Arial',
			      			                    renderer: function(time) {
			      			                        return time;
			      			                    },
			      			                  rotate: { degrees: 90}
			      			                }
			      			            }],
			      			          series: [{
			      		                type: 'column',
			      		                axis: 'left',
			      		              highlight: true,
			      		                xField: 'time',
			      		                yField: 'data',
			      		                listeners: {
			      		                  itemmouseup: function(item) {
			      		                      Ext.example.msg('Item Selected', item.value[1] + ' visits on ' + Ext.Date.monthNames[item.value[0]]);
			      		                  }  
			      		                },
			      		                tips: {
			      		                    trackMouse: true,
			      		                    width: 200,
			      		                    height: 50,
			      		                    renderer: function(storeItem, item) {
			      		                        this.setTitle(storeItem.get('time'));
			      		                        this.update(storeItem.get('data'));
			      		                    }
			      		                },
			      		              label: {
			      		                  display: 'insideEnd',
			      		                  'text-anchor': 'middle',
			      		                    field: 'data1',
			      		                    renderer: Ext.util.Format.numberRenderer('0'),
			      		                    orientation: 'vertical',
			      		                    color: '#333'
			      		                },
			      		            }]
			      						});
			      						monthStore.load({
        	    	               		params: {
               		    	 	   			tableName:tableName,
            		    	 	   			ids:ids.join(",")
        	    	           			}
        	    	            });
	            		            	 var win = Ext.create('Ext.Window', {
	            		                     width: 800,
	            		                     height: 600,
	            		                     minHeight: 400,
	            		                     minWidth: 550,
	            		                     hidden: false,
	            		                     maximizable: true,
	            		                     title: '柱状图',
	            		                     autoShow: true,
	            		                     layout: 'fit',
	            		                     tbar: [{
	            		                         text: '下载图表',
	            		                         handler: function() {
	            		                             Ext.MessageBox.confirm('下载提示', '是否下载当前图表?', function(choice){
	            		                                 if(choice == 'yes'){
	            		                                	 mchart.save({
	            		                                         type: 'image/png'
	            		                                     });
	            		                                 }
	            		                             });
	            		                         }
	            		                     } ],
	            		                     items: mchart
	            		                 });
			      						
			      						
			      						
			      					}
			      				}
			      			});

			        	} 				
			
				},{
					text: '日平均折线图',
					  id:'lineGraph',
					  scope:this,
			          icon:Global_Path+'/resources/extjs/images/up2.gif',
			          handler : function(btn){
				        	var tableName=gridStore.baseParams.tableName;
		                  	var grid = btn.up('gridpanel').store;
		                	var ids = [];
		                	grid.each(function (record) {  
		                		ids.push(record.get('id')); 
		                	}); 
				      			Ext.Ajax.request({
				      				url : Global_Path+'sysuser/checkFunctionIfForbid',
				      				params:{
				      					tableName:tableName
				      					},
				      				success : function(response,record) {
				      					var result = Ext.decode(response.responseText);
				      					if(result.success){
				      						Ext.Msg.alert('提示','您并没有获得该权限');
				      						return;
				      					}
				      					else{
				      			    		var dataStore = Ext.create('Ext.data.Store', {
				      			    			fields : ['time', 'data'],
				      			    			proxy : {
				      			    				type : 'ajax',
				      			    				actionMethods : {
				      			    					create : 'POST',
				      			    					read : 'POST', // by default GET
				      			    					update : 'POST',
				      			    					destroy : 'POST'
				      			    				},
				      			    				url : Global_Path + 'dataClustering/dayAve',
				      			    				reader : {
				      			    					type : 'json',
				      			    					root : 'result',
				      			    					idProperty : 'name',
				      			    					totalProperty : 'pageCount'
				      			    				}
				      			    			},
				      			    			autoLoad:false
				      			    		});

				      						var chart = new Ext.chart.Chart({
				      				            animate: false,
				      				            insetPadding: 60,
				      						    width: 480,
				      						    height: 320,
				      						    store: dataStore,
				      						    renderTo: Ext.getBody(),
				      						  axes: [{
				      			                type: 'Numeric',
				      			                minimum: 0,
				      			                position: 'left',
				      			                fields: ['data'],
				      			                title: false,
				      			                grid: true,
				      			                label: {
				      			                    renderer: Ext.util.Format.numberRenderer('0,0'),
				      			                    font: '10px Arial'
				      			                }
				      			            }, {
				      			                type: 'Category',
				      			                position: 'bottom',
				      			                fields: ['time'],
				      			                title: false,
				      			                label: {
				      			                    font: '11px Arial',
				      			                    renderer: function(time) {
				      			                        return time;
				      			                    },
				      			                  rotate: { degrees: 90}
				      			                }
				      			            }],
				      			          series: [{
				      		                type: 'line',
				      		                axis: 'left',
				      		                xField: 'time',
				      		                yField: 'data',
				      		                listeners: {
				      		                  itemmouseup: function(item) {
				      		                      Ext.example.msg('Item Selected', item.value[1] + ' visits on ' + Ext.Date.monthNames[item.value[0]]);
				      		                  }  
				      		                },
				      		                tips: {
				      		                    trackMouse: true,
				      		                    width: 200,
				      		                    height: 50,
				      		                    renderer: function(storeItem, item) {
				      		                        this.setTitle(storeItem.get('time'));
				      		                        this.update(storeItem.get('data'));
				      		                    }
				      		                },
				      		                style: {
				      		                    fill: '#38B8BF',
				      		                    stroke: '#38B8BF',
				      		                    'stroke-width': 3
				      		                },
				      		                markerConfig: {
				      		                    type: 'circle',
				      		                    size: 4,
				      		                    radius: 4,
				      		                    'stroke-width': 0,
				      		                    fill: '#38B8BF',
				      		                    stroke: '#38B8BF'
				      		                }
				      		            }]
				      						});
		            		            	dataStore.load({
	            	    	               		params: {
	                   		    	 	   			tableName:tableName,
	                		    	 	   			ids:ids.join(",")
	            	    	           			}
	            	    	            });
		            		            	 var win = Ext.create('Ext.Window', {
		            		                     width: 800,
		            		                     height: 600,
		            		                     minHeight: 400,
		            		                     minWidth: 550,
		            		                     hidden: false,
		            		                     maximizable: true,
		            		                     title: '折线图',
		            		                     autoShow: true,
		            		                     layout: 'fit',
		            		                     tbar: [{
		            		                         text: '下载图表',
		            		                         handler: function() {
		            		                             Ext.MessageBox.confirm('下载提示', '是否下载当前图表?', function(choice){
		            		                                 if(choice == 'yes'){
		            		                                     chart.save({
		            		                                         type: 'image/png'
		            		                                     });
		            		                                 }
		            		                             });
		            		                         }
		            		                     } ],
		            		                     items: chart
		            		                 });
				      						
				      						
				      						
				      					}
				      				}
				      			});

				        	} 				
				
					},{ 
		          text: '上传',
		          id:'updata',
		          scope:this,
		          icon:Global_Path+'/resources/extjs/images/up2.gif',
		          handler : function(){
		        	  var tableName=gridStore.baseParams.tableName;
		      			Ext.Ajax.request({
		      				url : Global_Path+'sysuser/checkFunctionIfForbid',
		      				params:{
		      					tableName:tableName
		      					},
		      				success : function(response) {
		      					var result = Ext.decode(response.responseText);
		      					if(result.success){
		      						Ext.Msg.alert('提示','您并没有获得该权限');
		      						return;
		      					}
		      					else{
		      			        	var upLoadForm = Ext.create('Bjfu.dataDisplay.view.FileUpload',{
		      			        		tableName:tableName
		      			        	});
		      			        	  
		      			        	Ext.create('Ext.window.Window',{
		      			        		title:'文件上传',
		      			        		closable:true,
		      			        		closeAction:'destroy',
		      			        		modal:true,
		      			        		resizable:false,
		      			        	    border:false,
		      			        		width:300,
		      			        		height:150,
		      			        		layout:'fit',
		      			        		items:[upLoadForm]
		      			        	}).show();
		      					}
		      				}
		      			});

		        	} 
		        },{
				       text: '修改',
				       scope:this, 
				        icon:Global_Path+'/resources/extjs/images/update.png',
				          handler : function(o){
					         var tableName=gridStore.baseParams.tableName;
				      			Ext.Ajax.request({
				      				url : Global_Path+'sysuser/checkFunctionIfForbid',
				      				params:{
				      					tableName:tableName
				      					},
				      				success : function(response) {
				      					var result = Ext.decode(response.responseText);
				      					if(result.success){
				      						Ext.Msg.alert('提示','您并没有获得该权限');
				      						return;
				      					}
				      					else{				        	
				      						var gird = o.ownerCt.ownerCt;
									     var record = gird.getSelectionModel().getSelection();
									     	if(record.length>1||record.length==0)
									     		{
									     			Ext.Msg.alert('提示','请选择一条记录！');
									     			return;
									     		}else{
							        	var modifyForm = Ext.create('Bjfu.dataDisplay.view.ModifyData',{
							        		tableName:tableName
														});
							        	modifyForm.loadRecord(record[0]);
							        	Ext.create('Ext.window.Window',{
							        		title:'修改数据界面',
							        		closable:true,
							        		closeAction:'destroy',
							        		modal:true,
							        		border:false,
							        		resizable:false,
							        		width:250,
							        		height:150,
							        		layout:'fit',
							        		items:[modifyForm]
							        	}).show();
							        	
							        	}
				      					}
				      				}
				      			});

				        	}
				        },{
						       text: '删除',
						       scope:this, 
						        icon:Global_Path+'/resources/extjs/images/delete.png',
						          handler : function(o){
							         var tableName=gridStore.baseParams.tableName;
						      			Ext.Ajax.request({
						      				url : Global_Path+'sysuser/checkFunctionIfForbid',
						      				params:{
						      					tableName:tableName
						      					},
						      				success : function(response) {
						      					var result = Ext.decode(response.responseText);
						      					if(result.success){
						      						Ext.Msg.alert('提示','您并没有获得该权限');
						      						return;
						      					}
						      					else{				        	
						      						var gird = o.ownerCt.ownerCt;
											     var record = gird.getSelectionModel().getSelection();
											     	if(record.length==0)
											     		{
											     			Ext.Msg.alert('提示','请至少选择一条记录！');
											     			return;
											     		}else{
											        		//1.先得到ID的数据(domtId)
											        		var st = gird.getStore();
											        		var ids = [];
											        		Ext.Array.each(record,function(data){
											        			ids.push(data.get('id'));
											        			Ext.Msg.confirm("提示","确定删除所选记录吗？",function(btn){
											        				if(btn=='yes'){
											        						Ext.Ajax.request({
											        							url:Global_Path+'dataDisplay/deleteData',
																				params:{ids:ids.join(","),
																					tableName:tableName},
																				method:'POST',
																				timeout:2000,
																				success:function(response,opts){
															                    	var	result =  Ext.decode(response.responseText);
															                    	if(result.success){
																						Ext.Array.each(record,function(data){
																							st.remove(data);
																						});
															                    		Ext.Msg.alert('提示','删除数据成功');
																    	 	   			Ext.getCmp('dataDisplayId').store.reload();
																    	 	   			Ext.getCmp('dataDisplayId').store.loadRawData();
															                    	}
											        							}
											        						})
											        				}
											        			})
											        		});}
						      					}
						      				}
						      			});

						        	}
						        },{ 
				        	text: '数据导出' ,
				        	icon:Global_Path+'/resources/extjs/images/bottom2.gif',
				        	scope:this,
		                    handler: function(btn) {
		                    	var tableName=gridStore.baseParams.tableName;
		                    	var grid = btn.up('gridpanel').store;
		                    	var ids = [];
		                    	grid.each(function (record) {  
		                    		ids.push(record.get('id')); 
		                    	}); 
		                    	
				      			Ext.Ajax.request({
				      				url : Global_Path+'sysuser/checkFunctionIfForbid',
				      				params:{
				      					tableName:tableName
				      					},
				      				success : function(response) {
				      					var result = Ext.decode(response.responseText);
				      					if(result.success){
				      						Ext.Msg.alert('提示','您并没有获得该权限');
				      						return;
				      					}
				      					else{	
		                    	
		                    	 Ext.Msg.show({
		                    		  title:'确认导出',
		                    		     msg: '您确定导出数据?',
		                    		     buttons: Ext.Msg.YESNO,
		                    		     width : 250,
		                    		     fn: function(btn){
		                    		   if(btn=='yes'){ 
		                    		             Ext.Ajax.request({
		                    		             url : Global_Path+'dataDisplay/downloadData',
		                    		             params:{ids:ids.join(","),tableName:tableName},
		                    		             success:function(res){
		                    		             var obj =res.responseText.replace(/\"/g, "");
		                    		             if(obj!="")
		                    		             window.location=obj;
		                    		             else
		     			      						Ext.Msg.alert('提示','请先选择一个表');
		 			      						return; 
		                    		            },
		                    		             failure : function() {
		                    		             Ext.MessageBox.updateProgress(1);   
		                    		             Ext.MessageBox.hide();   
		                    		             Ext.Msg.show({
		                    		             title : '错误提示',
		                    		             msg : '下载时发生错误!',
		                    		             width : 200,
		                    		             buttons : Ext.Msg.OK,
		                    		             icon : Ext.Msg.ERROR
		                    		         });
		                    		     }
		                    		    });
		                    		   }
		                    		     },
		                    		            icon: Ext.MessageBox.QUESTION
		                    		 });
				      					}
				      				}
		                    	 
				      			})
		                    	 
		                    		 }
				        },{ 
				        	text: '模板下载' ,
				        	icon:Global_Path+'/resources/extjs/images/bottom2.gif',
				        	scope:this,
		                    handler: function(btn) {
		                    	 Ext.Msg.show({
		                    		  title:'确认导出',
		                    		     msg: '您确定导出模板?',
		                    		     buttons: Ext.Msg.YESNO,
		                    		     width : 250,
		                    		     fn: function(btn){
		                    		   if(btn=='yes'){ 
		                    		             Ext.Ajax.request({
		                    		             url : Global_Path+'dataDisplay/downloadTemplate',
		                    		             success:function(res){
		                    		             var obj =res.responseText.replace(/\"/g, "");
		                    		             window.location=obj;
		                    		            },
		                    		             failure : function() {
		                    		             Ext.MessageBox.updateProgress(1);   
		                    		             Ext.MessageBox.hide();   
		                    		             Ext.Msg.show({
		                    		             title : '错误提示',
		                    		             msg : '下载时发生错误!',
		                    		             width : 200,
		                    		             buttons : Ext.Msg.OK,
		                    		             icon : Ext.Msg.ERROR
		                    		         });
		                    		     }
		                    		    });
		                    		   }
		                    		     },
		                    		            icon: Ext.MessageBox.QUESTION
		                    		 });
		                    		 }
				        },{
					    	text:'批量上传',
					    	scope:this,
					    	icon : Global_Path + '/resources/extjs/images/up2.gif',
				    		handler : function(btn) {
				    			var tableName=gridStore.baseParams.tableName;
				    			Ext.Ajax.request({
			      				url : Global_Path+'sysuser/checkFunctionIfForbid',
			      				params:{
			      					tableName:tableName
			      					},
			      				success : function(response) {
			      					var result = Ext.decode(response.responseText);
			      					if(result.success){
			      						Ext.Msg.alert('提示','您并没有获得该权限');
			      						return;
			      					}
			      					else{		      			        	
			      						var groupUpLoadForm = Ext.create('Bjfu.dataDisplay.view.FileGroupUpload',{
		      			        		tableName:tableName
		      			        	});
		      			        	  
		      			        	Ext.create('Ext.window.Window',{
		      			        		title:'文件批量上传',
		      			        		closable:true,
		      			        		closeAction:'destroy',
		      			        		modal:true,
		      			        		resizable:false,
		      			        	    border:false,
		      			        		width:300,
		      			        		height:150,
		      			        		layout:'fit',
		      			        		items:[groupUpLoadForm]
		      			        	}).show();}
			      				}
			      			});
				    		}
						},"->", {
		    	text:'高级查询',
		    	scope:this,
		    	icon : Global_Path + '/resources/extjs/images/search.png',
	    		handler : function(btn) {
		       		var gridStore = btn.up('gridpanel').store;
		       		var tableName=gridStore.baseParams.tableName;
		      		var queryForm = Ext.create('Bjfu.dataDisplay.view.QueryData',{
		        		tableName:tableName
		        	});
		  			Ext.create('Ext.window.Window', {
						title : '数据高级查询',
			       		height : 250,
			       		width:600,
			       		closable : true,
			       		closeAction : 'destroy',
			       		border : false,
			       		modal : true,
			       		resizable : false,
			       		layout : 'fit',
			       		items : [queryForm],
			       		listeners : {
							'close' : function(){
								me.search_cache = JSON.stringify(queryForm.getForm().getValues());
								this.destroy();
							}
						}
			       	}).show();
		       }
			}],
			loadMask:true
		});
		me.callParent(arguments);
	}
});