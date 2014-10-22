Ext.define('Bjfu.indexResource.view.IndexResourceListView',{
	extend : 'Ext.grid.Panel',
	alias:'widget.IndexResourceListView',
	forceFit : true,
	layout : 'fit',
    autoScroll: true,
	layoutConfig : {
		animate : true
	},
//    setlType : 'rowmodel',/////////////////可以单行编辑
//    plugins: [
//              Ext.create('Ext.grid.plugin.RowEditing', {
//            	  pluginId:'rowEditing', 
//        	      saveBtnText: '保存', 
//        	      cancelBtnText: "取消", 
//        	      autoCancel: false, 
//        	      clicksToEdit: 2
//              })
//          ],
	search_cache: null,	  //用于分页时缓存高级查询条件
	split : true,
	overflowY : 'scroll', //只显示上下滚动的滚动条
	overflowX : 'hidden',
	selType : 'checkboxmodel',	// 单选，复选框
	requires : [
	            'Bjfu.indexResource.model.IndexResource'
	           
/*	             ,'Ext.selection.CellModel',
	            'Ext.grid.*',
	            'Ext.data.*',
	            'Ext.util.*',
	            'Ext.form.*'*/
	            ],
	
	initComponent : function() {
		var me = this;
		var rowEditing = Ext.create('Ext.grid.plugin.RowEditing',{
			pluginId:'rowEditing', 
	        saveBtnText: '保存', 
	        cancelBtnText: "取消", 
	        autoCancel: false, 
	        clicksToEdit:2
		});
		var gridStore = Ext.create('Ext.data.Store', {
			model : 'Bjfu.indexResource.model.IndexResource',
			pageSize : 25,
			proxy : {
				type : 'ajax',
				actionMethods: {
	                create : 'POST',
	                read   : 'POST', // by default GET
	                update : 'POST',
	                destroy: 'POST'
				},
				/*extraParams: {  
	                searchJson : '{ismpewStatus : 1}'
	            },*/  
				url : Global_Path+'indexresource/indexResourceList',//////////////////////待改
				reader : {
					type : 'json',
					root : 'result',
					idProperty : 'id',
					totalProperty : 'pageCount'
				}
			},
			listeners : {
				'beforeload': function(store, operation, eOpts) {
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
					text : '指标编号',
			        dataIndex : 'id',
			        hidden : true
				},
				Ext.create('Ext.grid.RowNumberer',{
			          		header : '序号',
			          		align: 'left',
			          		width : 60
			    }), 
			    {
			        text : '中文名称',
			        dataIndex : 'indexName',
			        width : '15%',
			        editor: {
		                xtype: 'textfield',
		                allowBlank: false
		            }
			    },{
			        text : '英文名称',
			        dataIndex : 'indexEnName',
			        width : '15%',
			        editor: {
		                xtype: 'textfield',
		                allowBlank: false
		            }
			    } ,{
			        text : '描述',
			        sortable : false,
			        dataIndex : 'indexMemo',
			        width : '15%',
			        editor : 'textfield'
			    },{
			        text : '单位',
			        sortable : false,
			        dataIndex : 'indexUnit',
			        width : '15%',
			        editor : 'textfield'
			    },{
			        text : '添加人',
			        dataIndex : 'creater_id',
			        width : '8%'
			    },{
			        text : '添加时间',
			        dataIndex : 'createTime',
			        width : '15%'
			    },{
			        text : '修改人',
			        dataIndex : 'modifier_id',
			        width : '8%'
			    },{
			    	text : '修改时间',
			    	dataIndex : 'modifyTime',
			    	width : '15%'
			    }],
			tbar : [{
		            text: '新增',
			          scope:this,
			          icon:Global_Path+'/resources/extjs/images/add.png',
			          handler : function(){
			        	var addForm = Ext.create('Bjfu.indexResource.view.AddIndexResource',{
			        	});
			        	Ext.create('Ext.window.Window',{
			        		title:'添加指标',
			        		closable:true,
			        		closeAction:'destroy',
			        		modal:true,
			        		resizable:false,
			        	    border:false,
			        		width:300,
			        		height:230,
			        		layout:'fit',
			        		items:[addForm]
			        	}).show();
			          } 
			    },{
				       text: '修改',
				       scope:this, 
				        icon:Global_Path+'/resources/extjs/images/update.png',
				          handler : function(o){
				        	 var gird = o.ownerCt.ownerCt;
						     var record = gird.getSelectionModel().getSelection();
						     	if(record.length>1||record.length==0)
						     		{
						     			Ext.Msg.alert('提示','请选择一条记录！');
						     			return;
						     		}else{
				        	
				        	var modifyForm = Ext.create('Bjfu.indexResource.view.ModifyIndexResource',{
											});
				        	modifyForm.loadRecord(record[0]);
				        	Ext.create('Ext.window.Window',{
				        		title:'修改指标界面',
				        		closable:true,
				        		closeAction:'destroy',
				        		modal:true,
				        		border:false,
				        		resizable:false,
				        		width:400,
				        		height:250,
				        		layout:'fit',
				        		items:[modifyForm]
				        	}).show();
				        	
				        	}
				        	}
				    },{ 
		        	text: '删除' ,
		        	icon:Global_Path+'/resources/extjs/images/delete.png',
		        	scope:this,
		        	handler : function(o){
	                	var gird = o.ownerCt.ownerCt;
				    	var record = gird.getSelectionModel().getSelection();		        	
			        	if(record.length==0)
			        		{
			        		Ext.Msg.alert('提示','请选择删除的记录！');
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
			        							url:Global_Path+'indexresource/deleteIndexResource',
												params:{ids:ids.join(",")},
												method:'POST',
												timeout:2000,
												success:function(response,opts){
													Ext.Array.each(record,function(data){
														st.remove(data);
													});
													Ext.getCmp('indexResourceListViewId').store.reload();
			        							}
			        						})
			        				}
			        			})
			        		});
			        	}    
		    		}
		        }, "->" ,{
			       	text : '高级查询' ,
			       	scope: this,
			    	icon : Global_Path + '/resources/extjs/images/search.png',
		    		handler : function(btn) {
			       		var gridStore = btn.up('gridpanel').store;
			      		var queryForm = Ext.create('Bjfu.indexResource.view.QueryIndexResource');
			  			Ext.create('Ext.window.Window', {
							title : '指标高级查询',
				       		height : 250,
				       		width : 400,
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
			loadMask:true,
			bbar : Ext.create('Ext.toolbar.Paging', {
					width : '100%',
					store : gridStore,
					displayInfo : true,
					displayMsg : '显示 {0} - {1} 条，共计 {2} 条',
					emptyMsg : "没有数据"
			})
		});
		
		me.callParent(arguments);
	}, 
});
