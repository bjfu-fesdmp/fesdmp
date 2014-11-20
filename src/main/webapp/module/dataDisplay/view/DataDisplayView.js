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
				        },{ 
				        	text: '模板下载' ,
				        	icon:Global_Path+'/resources/extjs/images/bottom2.gif',
				        	scope:this,
		                    handler: function(btn) {
		                    	var ids = [];
		                    	 Ext.Msg.show({
		                    		  title:'确认导出',
		                    		     msg: '您确定导出模板?',
		                    		     buttons: Ext.Msg.YESNO,
		                    		     width : 250,
		                    		     fn: function(btn){
		                    		   if(btn=='yes'){ 
		                    		             Ext.Ajax.request({
		                    		             url : Global_Path+'dataDisplay/downloadTemplate',
		                    		             params:{ids:ids.join(",")},
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
				        }, {
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