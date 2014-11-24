Ext.define('Bjfu.log.view.LogListView',{
	extend : 'Ext.grid.Panel',
	alias:'widget.LogListView',
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
	requires : ['Bjfu.log.model.Log'],
	
	initComponent : function() {
		var me = this;
		var gridStore = Ext.create('Ext.data.Store', {
			model : 'Bjfu.log.model.Log',
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
				url : Global_Path+'syslog/systemlogList',
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
					text : '日志编号',
			        dataIndex : 'id',
			        hidden : true
				},
				Ext.create('Ext.grid.RowNumberer',{
			          		header : '序号',
			          		align: 'left',
			          		width : 60
			    }), 
			    { 
			        text : '业务类型',
			        dataIndex : 'businessType',
			        renderer : function (value) {
			        	if (value == "SYS_LOGIN") {
			        		return "系统登录";
			        	} else if (value == "SYS_LOGOUT") {
			        		return "系统退出";
			        	} else if (value == "SYS_OPERATE") {
			        		return "系统操作";
			        	} else if (value == "DATA_OPERATE") {
			        		return "数据操作";
			        	} else {
			        		return "其他业务";
			        	}
			        }
			    },{
			        text : '业务类型',
			        dataIndex : 'businessTypeName',
			        hidden:true
			    },{
			        text : '操作类型',
			        dataIndex : 'operationType',
			        renderer : function (value) {
			        	if (value == "ADD") {
			        		return "增加";
			        	} else if (value == "DELETE") {
			        		return "删除";
			        	} else if (value == "UPDATE") {
			        		return "修改";
			        	} else if (value == "QUERY") {
			        		return "查询";
			        	} else {
			        		return "其他";
			        	}
			        }
			    },{
			        text : '操作类型',
			        dataIndex : 'operateTypeName',
			        hidden:true
			    } ,{
			        text : '用户名',
			        dataIndex : 'userName',
			        width : '10%'
			    },{
			        text : '用户源地址',
			        dataIndex : 'userSourceIp',
			        width : '10%'
			    },{
			        text : '操作内容',
			        dataIndex : 'operateContent',
			        width : '45%'
			    },{
			        text : '操作时间',
			        dataIndex : 'operateTime',
			        width : '15%'
			    }
			],
			tbar : [{
				 	fieldLabel: '查询关键词',
					xtype : 'textfield',
					id : 'log.searchWord',
					name : 'searchWord',
					width : 300,
					emptyText : '用户名'
				},{
			       	text : '查询' ,
			       	icon : Global_Path + '/resources/extjs/images/search.png',
			       	scope : this, 
			       	handler : function(btn) {
			       		var gridStore = btn.up('gridpanel').store;
			       		var searchWord = Ext.getCmp("log.searchWord").getValue();
			       		if (searchWord != "") {
			       			var searchStr = "{'userName' : '"+ searchWord +"'}";
			       			// 缓存查询条件
				       		me.search_cache = searchStr;
			       		} else {
			       			me.search_cache = null;
			       		}
			       }
			    }, "->", {
		    	text:'高级查询',
		    	scope:this,
		    	icon : Global_Path + '/resources/extjs/images/search.png',
	    		handler : function(btn) {
		       		var gridStore = btn.up('gridpanel').store;
		      		var queryForm = Ext.create('Bjfu.log.view.QueryLog');
		  			Ext.create('Ext.window.Window', {
						title : '日志信息高级查询',
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
