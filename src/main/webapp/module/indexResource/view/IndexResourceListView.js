Ext.define('Bjfu.indexResource.view.IndexResourceListView',{
	extend : 'Ext.grid.Panel',
	alias:'widget.IndexResourceListView',
	forceFit : true,
	layout : 'fit',
    autoScroll: true,
	layoutConfig : {
		animate : true
	},
/*    setlType : 'rowmodel',/////////////////可以单行编辑
    plugins: [
              Ext.create('Ext.grid.plugin.RowEditing', {
                  clicksToEdit: 1
              })
          ],*/
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
			/*sorters : ["ProductName", "CatID"],
			filters : [{"property" : "ProductName" ,"value" : 1}],*/
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
			        width : '30%',
			        editor : 'textfield'
			    },{
			        text : '添加人',
			        dataIndex : 'creater_id',
			        width : '8%'
			    },{
			        text : '添加时间',
			        dataIndex : 'createTime',
			        width : '10%'
			    },{
			        text : '修改人',
			        dataIndex : 'modifier_id',
			        width : '8%'
			    },{
			    	text : '修改时间',
			    	dataIndex : 'modifyTime',
			    	width : '10%'
			    },{
			    	text : '删除',
	                xtype: 'actioncolumn',
	                width: 30,
	                sortable: false,
	                menuDisabled: true,
	                width : '4%',
	                items: [{
	                    icon: Global_Path + '/resources/extjs/images/delete.png',
	                    tooltip: '删除',
	                    scope: this,
	                    handler: function(grid, rowIndex){
	                        this.getStore().removeAt(rowIndex);
	                        Ext.Ajax.request({
		    	 	   			url:Global_Path+'indexresource/deleteIndexResource',
		    	 	   			method:'post',
		    	 	   			params:{
		    	 	   					formData:Ext.encode(rowIndex)
		    	 	   			},
		    	 	   		success: function(response) {
		                    	var	result =  Ext.decode(response.responseText);
		                    	if(result.success){
		                    		Ext.Msg.alert('提示','删除指标成功');
		    						window.close();
		    	 	   			Ext.getCmp('userGroupViewId').store.reload();
		                    	}else{
		                    		Ext.Msg.alert('提示','删除指标失败');
		                    		window.close();
		                    	}
		                    },
		                    failure: function(form, action) {
		                        Ext.Msg.alert('Failed', action.result.msg);
		                    }
		    	 	   		});
	                    }
	                }]
	            }],
			tbar : [/*{
				 	fieldLabel: '查询关键词',
					xtype : 'textfield',
					id : 'indexResource.searchWord',
					name : 'searchWord',
					width : 300,
					emptyText : '指标名称'
				},*/{
		            text: '添加指标',
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

/*    onRemoveClick: function(grid, rowIndex){
        this.getStore().removeAt(rowIndex);
    }*/
    
});
