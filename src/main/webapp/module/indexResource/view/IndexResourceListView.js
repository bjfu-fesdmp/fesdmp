Ext.define('Bjfu.indexResource.view.IndexResourceListView',{
	extend : 'Ext.grid.Panel',
	alias:'widget.IndexResourceListView',
	forceFit : true,
	layout : 'fit',
    autoScroll: true,
    setlType : 'rowmodel',/////////////////可以单行编辑
    plugins: [
              Ext.create('Ext.grid.plugin.RowEditing', {
                  clicksToEdit: 1
              })
          ]
	layoutConfig : {
		animate : true
	},
	search_cache: null,	  //用于分页时缓存高级查询条件
	split : true,
	overflowY : 'scroll', //只显示上下滚动的滚动条
	overflowX : 'hidden',
	selType : 'checkboxmodel',	// 单选，复选框
	requires : ['Bjfu.indexResource.model.IndexResource'],
	
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
				url : Global_Path+'indexResource/indexResourceList',//////////////////////待改
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
	//		        hidden:true
			    },{
			        text : '英文名称',
			        dataIndex : 'indexEnName',
			        width : '15%',
			        editor: {
		                xtype: 'textfield',
		                allowBlank: false
		            }
	//		        hidden:true
			    } ,{
			        text : '描述',
			        sortable : false,
			        dataIndex : 'indexMemo,
			        width : '30%'
			        editor : 'textfield'
			    },{
			        text : '添加人',
			        dataIndex : 'createrId',
			        width : '10%'
			    },{
			        text : '添加时间',
			        dataIndex : 'createTime',
			        width : '10%'
			    },{
			        text : '修改人',
			        dataIndex : 'modifier_id',
			        width : '10%'
			    },{
			    	text : '修改时间',
			    	dataIndex : 'modifyTime',
			    	width : '10%'
			    }
			],
			tbar : [{
				 	fieldLabel: '查询关键词',
					xtype : 'textfield',
					id : 'indexResource.searchWord',
					name : 'searchWord',
					width : 300,
					emptyText : '指标名称'
				},{
			       	text : '查询' ,
			       	icon : Global_Path + '/resources/extjs/images/search.png',
			       	scope : this, 
			       	handler : function(btn) {
			       		var gridStore = btn.up('gridpanel').store;
			       		var searchWord = Ext.getCmp("indexResource.searchWord").getValue();
			       		if (searchWord != "") {
			       			var searchStr = "{'indexName' : '"+ searchWord +"'}";
			       			// 缓存查询条件
				       		me.search_cache = searchStr;
			       		} else {
			       			me.search_cache = null;
			       		}
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
	}
});
