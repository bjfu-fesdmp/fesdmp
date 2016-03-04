Ext.define('Bjfu.dataClustering.view.AllTableDisplayView',{
	extend : 'Ext.grid.Panel',
	rootVisible: false,
	displayField:'text',
	selType : 'checkboxmodel',	// 单选，复选框
	requires : ['Bjfu.dataClustering.model.AllTableDisplay'],
	initComponent : function() {
		var me = this;
		var gridStore = Ext.create('Ext.data.Store', {
			model : 'Bjfu.dataClustering.model.AllTableDisplay',
			proxy : {
				type : 'ajax',
				actionMethods: {
	                create : 'POST',
	                read   : 'POST', // by default GET
	                update : 'POST',
	                destroy: 'POST'
				},
				url : Global_Path+'dataDisplay/hierarchicalClusteringAllTableList',
				reader : {
					type : 'json',
					root : 'result'
				}
			},
			autoLoad : true
		});


		Ext.apply(me, {
			store : gridStore,
			columns : [
			           Ext.create('Ext.grid.RowNumberer',{
          		header : '序号',
          		align: 'left',
          		width : 60
    }), 
			    {
			    	flex : 1,
					sortable : true,
			        text : '数据表',
			        dataIndex : 'text'
			        
			    }, {
					text : '编号',
					hidden:true,
					sortable : true,
					dataIndex : 'id'
				}
			],
		  tbar : [{
		       text: '单机层次聚类',
		       scope:this, 
		        icon:Global_Path+'/resources/extjs/images/delete.png',
		          handler : function(o){
			         var tableName=gridStore.baseParams.tableName;
		      			Ext.Ajax.request({
		      				url : Global_Path+'sysuser/checkhierarchicalClusteringFunctionIfForbid',
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
							     	if(record.length<1)
							     		{
							     			Ext.Msg.alert('提示','请至少选择一条记录！');
							     			return;
							     		}else if(record.length>7)
							     		{
							     			Ext.Msg.alert('提示','最多选择七条记录！');
							     			return;
							     		}
							     		{
							        		//1.先得到ID的数据(domtId)
							        		var st = gird.getStore();
							        		var ids = [];
							        		Ext.Array.each(record,function(data){
							        			ids.push(data.get('id'));
							        			Ext.Msg.confirm("提示","确定合并这些数据表？",function(btn){
							        				if(btn=='yes'){
							        						Ext.Ajax.request({
							        							url:Global_Path+'dataClustering/tableSelect',
																params:{ids:ids.join(","),
																	tableName:tableName},
																method:'POST',
																timeout:2000,
																success:function(response,opts){
											                    	var	result =  Ext.decode(response.responseText);
											                    	if(result.success){
											                    		var allTable=result.result;
											                    		
											        		      		var partitionClusteringForm = Ext.create('Bjfu.dataClustering.view.KMeans&KMedoids',{
											        		      			allTable:allTable
											        		        	});
											        		  			Ext.create('Ext.window.Window', {
											        						title : '划分聚类',
											        			       		height : 250,
											        			       		width:400,
											        			       		closable : true,
											        			       		closeAction : 'destroy',
											        			       		border : false,
											        			       		modal : true,
											        			       		resizable : false,
											        			       		layout : 'fit',
											        			       		items : [partitionClusteringForm],
											        			       		listeners : {
											        							'close' : function(){
											        								me.search_cache = JSON.stringify(queryForm.getForm().getValues());
											        								this.destroy();
											        							}
											        						}
											        			       	}).show();
											                    		
											                    	}
											                    	else
											                    		Ext.Msg.alert('提示','发生错误');
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
				       text: '集群层次聚类',
				       scope:this, 
				        icon:Global_Path+'/resources/extjs/images/delete.png',
				          handler : function(o){
					         var tableName=gridStore.baseParams.tableName;
				      			Ext.Ajax.request({
				      				url : Global_Path+'sysuser/checkhierarchicalClusteringFunctionIfForbid',
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
									     	if(record.length<1)
									     		{
									     			Ext.Msg.alert('提示','请至少选择一条记录！');
									     			return;
									     		}else if(record.length>7)
									     		{
									     			Ext.Msg.alert('提示','最多选择七条记录！');
									     			return;
									     		}
									     		{
									        		//1.先得到ID的数据(domtId)
									        		var st = gird.getStore();
									        		var ids = [];
									        		Ext.Array.each(record,function(data){
									        			ids.push(data.get('id'));
									        			Ext.Msg.confirm("提示","确定合并这些数据表？",function(btn){
									        				if(btn=='yes'){
									        						Ext.Ajax.request({
									        							url:Global_Path+'dataClustering/tableSelect',
																		params:{ids:ids.join(","),
																			tableName:tableName},
																		method:'POST',
																		timeout:2000,
																		success:function(response,opts){
													                    	var	result =  Ext.decode(response.responseText);
													                    	if(result.success){
													                    		var allTable=result.result;
													                    		
													        		      		var partitionClusteringForm = Ext.create('Bjfu.dataClustering.view.HadoopKMeans&KMedoids',{
													        		      			allTable:allTable
													        		        	});
													        		  			Ext.create('Ext.window.Window', {
													        						title : '划分聚类',
													        			       		height : 250,
													        			       		width:400,
													        			       		closable : true,
													        			       		closeAction : 'destroy',
													        			       		border : false,
													        			       		modal : true,
													        			       		resizable : false,
													        			       		layout : 'fit',
													        			       		items : [partitionClusteringForm],
													        			       		listeners : {
													        							'close' : function(){
													        								me.search_cache = JSON.stringify(queryForm.getForm().getValues());
													        								this.destroy();
													        							}
													        						}
													        			       	}).show();
													                    		
													                    	}
													                    	else
													                    		Ext.Msg.alert('提示','发生错误');
									        							}
									        						})
									        				}
									        			})
									        		});}
									     	
									     	
									     	
									     	
				      					}
				      				}
				      			});

				        	}
				        }],
			listeners:{
				scope : this,
				checkchange :function(node, checked) {
					node.checked = checked;
					var records = me.getView().getChecked();
					for (var i = 0; i < records.length; i++) {
						if (records[i].get('id') != node.get('id')) {
							records[i].set("checked" , false);
						}
					}
				},
	        	'itemclick' : function(view, record, item, index, e){
	        		var yearLocResId=gridStore.baseParams.tableName;
	        		var indexResourceId=record.get("id");
					
	        		 Ext.getCmp("dataDisplayId").getStore().baseParams= {
	        			 yearLocResId: yearLocResId,
	        			 indexResourceId:indexResourceId
	           			};
		            Ext.getCmp("dataDisplayId").getStore().loadPage(1, {
	               		params: {
	               			yearLocResId: yearLocResId,
	               			indexResourceId:indexResourceId
	           			}
	            });
	        	}
			},
		loadMask:true
		});

		me.callParent(arguments);
	}
});
