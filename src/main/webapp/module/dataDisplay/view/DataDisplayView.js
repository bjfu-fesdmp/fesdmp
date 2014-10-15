Ext.define('Bjfu.dataDisplay.view.DataDisplayView',{
	extend : 'Ext.grid.Panel',
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
		var table_name=me.table_name;
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
		          scope:this,
		          icon:Global_Path+'/resources/extjs/images/up2.gif',
		          handler : function(){
		        	var upLoadForm = Ext.create('Bjfu.dataDisplay.view.FileUpload',{
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
		        },"->", {
		    	text:'高级查询',
		    	scope:this,
		    	icon : Global_Path + '/resources/extjs/images/search.png',
	    		handler : function(btn) {
		       		var gridStore = btn.up('gridpanel').store;
		      		var queryForm = Ext.create('Bjfu.log.view.QueryLog');
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
