Ext.define('Bjfu.userResourceGroup.view.UserView',{
	extend : 'Ext.grid.Panel',
	alias:'widget.UserView',
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
	//selType : 'checkboxmodel',	// 单选，复选框
	requires : ['Bjfu.userResourceGroup.model.User'],
	
	initComponent : function() {
		var me = this;
		var gridStore = Ext.create('Ext.data.Store', {
			model : 'Bjfu.userResourceGroup.model.User',
			pageSize : 25,
			proxy : {
				type : 'ajax',
				actionMethods: {
	                create : 'POST',
	                read   : 'POST', // by default GET
	                update : 'POST',
	                destroy: 'POST'
				},
				url : Global_Path+'sysuser/userList',
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
					text : '用户id',
			        dataIndex : 'id',
			        hidden : true
				},
				Ext.create('Ext.grid.RowNumberer',{
			          		header : '序号',
			          		align: 'left',
			          		width : 60
			    }),{
			        text : '用户名',
			        dataIndex : 'userName'
			    },{
			        text : '用户登陆名',
			        dataIndex : 'userLoginName'
			    },{
			        text : '邮箱',
			        dataIndex : 'email',
			        width : '30%'
			    },{
			        text : '电话',
			        dataIndex : 'userPhone',
			        width : '30%'
			    }
			],
			tbar : [{
		    	text:'高级查询',
		    	scope:this,
		    	icon : Global_Path + '/resources/extjs/images/search.png',
	    		handler : function(btn) {
		       		var gridStore = btn.up('gridpanel').store;
		      		var queryForm = Ext.create('Bjfu.userResourceGroup.view.QueryUser');
		  			Ext.create('Ext.window.Window', {
						title : '用户高级查询',
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
	        		
	        		var userId=record.get("id");
	        		
	        		 Ext.getCmp("resourceGroupViewId").getStore().baseParams= {
	        			 userId: userId
	           			};
	        		
		            Ext.getCmp("resourceGroupViewId").getStore().load({
	               			params: {
	               				userId: userId
	               			}
		            	});
		            
	        	}
			},
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
