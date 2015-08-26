


Ext.define('Bjfu.dataClustering.view.DataDisplayView',{
	extend : 'Ext.grid.Panel',
	id:'dataClusteringView',
	alias:'widget.DataClusteringView',
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
	requires : ['Bjfu.dataClustering.model.DataDisplay'],
	
	initComponent : function() {
		var me = this;
		var gridStore = Ext.create('Ext.data.Store', {
			model : 'Bjfu.dataClustering.model.DataDisplay',
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
				
					},
			       { 
		          text: 'k-means聚类',
		          id:'KMeans',
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
		      				success : function(response) {
		      					var result = Ext.decode(response.responseText);
		      					if(result.success){
		      						Ext.Msg.alert('提示','您并没有获得该权限');
		      						return;
		      					}
		      					else{
		      			        	var KMeansForm = Ext.create('Bjfu.dataClustering.view.KMeans',{
		      			        		tableName:tableName,
		      			        		ids:ids.join(",")
		      			        	});
		      			        	  
		      			        	Ext.create('Ext.window.Window',{
		      			        		title:'k-means聚类',
		      			        		closable:true,
		      			        		closeAction:'destroy',
		      			        		modal:true,
		      			        		resizable:false,
		      			        	    border:false,
		      			        		width:300,
		      			        		height:150,
		      			        		layout:'fit',
		      			        		items:[KMeansForm]
		      			        	}).show();
		      					}
		      				}
		      			});

		        	} 
		        },{ 
			          text: 'k-medoids聚类',
			          id:'Kedoids',
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
			      				success : function(response) {
			      					var result = Ext.decode(response.responseText);
			      					if(result.success){
			      						Ext.Msg.alert('提示','您并没有获得该权限');
			      						return;
			      					}
			      					else{
			      			        	var KMedoidsForm = Ext.create('Bjfu.dataClustering.view.KMedoids',{
			      			        		tableName:tableName,
			      			        		ids:ids.join(",")
			      			        	});
			      			        	  
			      			        	Ext.create('Ext.window.Window',{
			      			        		title:'k-medoids聚类',
			      			        		closable:true,
			      			        		closeAction:'destroy',
			      			        		modal:true,
			      			        		resizable:false,
			      			        	    border:false,
			      			        		width:300,
			      			        		height:150,
			      			        		layout:'fit',
			      			        		items:[KMedoidsForm]
			      			        	}).show();
			      					}
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
		      		var queryForm = Ext.create('Bjfu.dataClustering.view.QueryData',{
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