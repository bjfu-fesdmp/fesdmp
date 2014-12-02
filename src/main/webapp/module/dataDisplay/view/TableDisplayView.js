Ext.define('Bjfu.dataDisplay.view.TableDisplayView',{
	extend:'Ext.tree.Panel',
	rootVisible: false,
	displayField:'text',
	requires : ['Bjfu.dataDisplay.model.TableDisplay'],
	initComponent : function() {
		var me = this;
		var gridStore = Ext.create('Ext.data.TreeStore', {
			id : 'table.tree',
			model : 'Bjfu.dataDisplay.model.TableDisplay',
			nodeParam : 'parentId',
			proxy : {
				type : 'ajax',
				actionMethods: {
	                create : 'POST',
	                read   : 'POST', // by default GET
	                update : 'POST',
	                destroy: 'POST'
				},
				url : Global_Path+'dataDisplay/tableList',
				reader : {
					type : 'json',
					root : 'result'
				}
			},
			root: {
				   nodeType: 'async',
				   id : '0',
				   expanded: true
		    },
			autoLoad : true
		});


		Ext.apply(me, {
			store : gridStore,
			columns : [
			    {
			    	xtype : 'treecolumn',
			    	flex : 1,
					sortable : true,
			        text : '数据表',
			        dataIndex : 'text'
			        
			    }, {
					text : '编号',
					hidden:true,
					sortable : true,
					dataIndex : 'id'
				},{
					text : '父级导航',
					hidden:true,
					dataIndex : 'parentId'
				}
			],
			tbar : [{ 
			        	text: '删除' ,
			        	icon:Global_Path+'/resources/extjs/images/delete.png',
			        	scope:this,
			        	handler : function(o){
		                	var gird = o.ownerCt.ownerCt;
					    	var record = gird.getSelectionModel().getSelection();		        	
							   if(record.length>1||record.length==0)
						   		{
							   		Ext.Msg.alert('提示','请选择一条记录！');
							   		return;
				        	}else{
				        		//1.先得到ID的数据(domtId)
				        		var st = gird.getStore();
				        		Ext.Array.each(record,function(record,data){
				        			var name=record.get("text");
				        			Ext.Ajax.request({
					      				url : Global_Path+'dataDisplay/CheckIsTable',
					      				params:{tableName: name},
					      				success : function(response) {
					      					var result = Ext.decode(response.responseText);
					      					if(!result.success){
					      						Ext.Msg.alert('提示','请选择正确的表');
					      						return;
					      					}
					      					else{
					      						Ext.Ajax.request({
					    		      				url : Global_Path+'sysuser/checkFunctionIfForbid',
					    		      				params:{
					    		      					tableName:name
					    		      					},
					    		      				success : function(response) {
					    		      					var result = Ext.decode(response.responseText);
					    		      					if(result.success){
					    		      						Ext.Msg.alert('提示','您并没有获得该权限');
					    		      						return;
					    		      					}
					    		      					else{
					    		      						Ext.Msg.confirm("提示","确定删除所选记录吗？",function(btn){
										        				if(btn=='yes'){
										        						Ext.Ajax.request({
										        							url:Global_Path+'dataDisplay/deleteTable',
																			params:{tableName: name},
																			method:'POST',
																			timeout:2000,
																			success:function(response,opts){
														                    	var	result =  Ext.decode(response.responseText);
														                    	if(result.success){
														                    		Ext.Msg.alert('提示','删除成功');
														    						window.close();
														    	 	   		Ext.getCmp('tableDisplayId').store.reload();
														    	 	   		Ext.getCmp('dataDisplayId').store.removeAll();
														    	 	   		
														                    	}else{
														                    		Ext.Msg.alert('提示','无法删除');
														                    		window.close();
														                    	}
										        							}
										        						})
										        				}
										        			})

					    		      					}
					    		      				}
					    		      			});
							        			
						    		}
				        			}
				        		});
				        			
				        		});
				        	}    
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
	        		var name=record.get("text");
	        		
	        		 Ext.getCmp("dataDisplayId").getStore().baseParams= {
	               			tableName: name
	           			};
		            Ext.getCmp("dataDisplayId").getStore().loadPage(1, {
	               		params: {
	               			tableName: name
	           			}
	            });
	        	}
			},
		loadMask:true
		});

		me.callParent(arguments);
	}
});
