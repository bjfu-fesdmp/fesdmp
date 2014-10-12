Ext.define('Bjfu.dataDisplay.view.TableDisplayView',{
	extend : 'Ext.grid.Panel',
	alias:'widget.TableDisplayView',
    title:'数据表',
    split:true,//显示分隔条  
    collapsible:true,
	forceFit : true,
	layout : 'fit',
	selType : 'rowmodel',	// 单选，复选框
    autoScroll: true,
	layoutConfig : {
		animate : true
	},
	search_cache: null,	  //用于分页时缓存高级查询条件
	split : true,
	overflowY : 'scroll', //只显示上下滚动的滚动条
	overflowX : 'hidden',
	requires : ['Bjfu.dataDisplay.model.TableDisplay'],
	initComponent : function() {
		var me = this;
		var gridStore = Ext.create('Ext.data.Store', {
			model : 'Bjfu.dataDisplay.model.TableDisplay',
			pageSize : 25,
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
			        text : '表',
			        dataIndex : 'name'
			        
			    }
			],
			loadMask:true,
			listeners:{
	        	'itemclick' : function(view, record, item, index, e){
	        		var name=record.get("name");
		            Ext.getCmp("dataDisplayId").getStore().loadPage(1, {
	               		params: {
	               			tableName: name
	           			}
	            });
	        	}
			}

		});

		me.callParent(arguments);
	}
});
