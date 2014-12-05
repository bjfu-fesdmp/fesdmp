Ext.define('Bjfu.userIndexResource.view.IndexResourceListView',{
	extend : 'Ext.grid.Panel',
	alias:'widget.IndexResourceListView',
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
				url : Global_Path+'indexresource/indexResourceOfUserList',
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
			        text : '所属资源组',
			        dataIndex : 'resourceGroupName',
			        width : '15%',
			        editor : 'textfield'
			    }],
			tbar : [{
		          text: '为用户添加指标资源',
		          scope:this,
		          icon:Global_Path+'/resources/extjs/images/add.png',
		          handler : function(){
		        	var addForm = Ext.create('Bjfu.userIndexResource.view.AddIndexResourceForUser',{
		        	});
		        	Ext.create('Ext.window.Window',{
		        		title:'为用户添加指标资源',
		        		closable:true,
		        		closeAction:'destroy',
		        		modal:true,
		        		resizable:false,
		        	    border:false,
		        		width:450,
		        		height:230,
		        		layout:'fit',
		        		items:[addForm]
		        	}).show();
		        	} 
		        },{
		        	text: '为用户删除指标资源' ,
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
			        		var id = null;
			        		var userId=st.baseParams;
			        		Ext.Array.each(record,function(data){
			        			id=data.get('id');
			        			Ext.Msg.confirm("提示","确定删除所选记录吗？",function(btn){
			        				if(btn=='yes'){
			        						Ext.Ajax.request({
			        							url:Global_Path+'indexresource/deleteIndexResourceForUser',
												params:{id:id,userId:userId
													},
												method:'POST',
												timeout:2000,
												success:function(response,opts){
							                    	var	result =  Ext.decode(response.responseText);
						                    		Ext.Msg.alert('提示','为用户删除指标资源成功');
							    	 	   			Ext.getCmp('indexResourceListViewId').store.reload();
							    	 	   			Ext.getCmp('indexResourceListViewId').store.loadRawData();
							                    	if(result.success){
														Ext.Array.each(record,function(data){
															st.remove(data);
														});
							                    	}else{
							                    		Ext.Msg.alert('提示','为用户删除指标资源失败');
							                    	}
			        							}
			        						})
			        				}
			        			})
			        		});
			        	}    
		    		}
		        }
		        ],
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
