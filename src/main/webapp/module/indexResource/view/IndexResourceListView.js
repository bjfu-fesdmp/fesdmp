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
          ],
	layoutConfig : {
		animate : true
	},
	search_cache: null,	  //用于分页时缓存高级查询条件
	split : true,
	overflowY : 'scroll', //只显示上下滚动的滚动条
	overflowX : 'hidden',
	selType : 'checkboxmodel',	// 单选，复选框
	requires : [
	            'Bjfu.indexResource.model.IndexResource',
	            'Ext.selection.CellModel',
	            'Ext.grid.*',
	            'Ext.data.*',
	            'Ext.util.*',
	            'Ext.form.*'
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
	                    handler: this.onRemoveClick
	                }]
	            }],
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
			    }, "->", {
		            text: '添加指标',
		            scope: this,
		            handler: this.onAddClick
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
	
	onAddClick: function(){
		
        var rec = new Bjfu.indexResource.model.IndexResource({
            id: '',
            indexName: '',
            indexEnName: '',
            indexUnit: '',
            indexMemo: '',
            creater_id: 0,
            createTime: Ext.Date.clearTime(new Date()),
      //    createTime:  Ext.Date.format(dt, Ext.Date.patterns.ISO8601Long),
            modifier_id: 0,
            modifyTime: '1970-01-01 00:00:00'
        });

        this.getStore().insert(0, rec);
        this.cellEditing.startEditByPosition({
            row: 0,
            column: 0
        });
    },

    onRemoveClick: function(grid, rowIndex){
        this.getStore().removeAt(rowIndex);
    }
    
});
