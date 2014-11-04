Ext.define('Bjfu.role.view.RoleView',{
	extend : 'Ext.grid.Panel',
	alias:'widget.RoleView',
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
	requires : ['Bjfu.role.model.Role'],
	
	initComponent : function() {
		var me = this;
		var gridStore = Ext.create('Ext.data.Store', {
			model : 'Bjfu.role.model.Role',
			pageSize : 25,
			proxy : {
				type : 'ajax',
				actionMethods: {
	                create : 'POST',
	                read   : 'POST', // by default GET
	                update : 'POST',
	                destroy: 'POST'
				},
				url : Global_Path+'role/roleList',//
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
					text : '角色id',
			        dataIndex : 'id',
			        hidden : true
				},
				Ext.create('Ext.grid.RowNumberer',{
			          		header : '序号',
			          		align: 'left',
			          		width : 60
			    }),{
			        text : '角色名',
			        dataIndex : 'roleName',
			    },{
			    	text : '角色描述',
			    	dataIndex : 'roleDiscription'
			    },{
			        text : '创建者id',
			        dataIndex : 'creater_id',
			        width : '10%'
			    },{
			        text : '创建时间',
			        dataIndex : 'createTime',
			        width : '10%'
			    }
			],
			tbar : [{ 
		          text: '新增',
		          scope:this,
		          icon:Global_Path+'/resources/extjs/images/add.png',
		          handler : function(){
		        	var addForm = Ext.create('Bjfu.role.view.AddRole',{
		        	});
		        	Ext.create('Ext.window.Window',{
		        		title:'新增角色',
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
			        							url:Global_Path+'role/deleteRole',///////////////////////
												params:{ids:ids.join(",")},
												method:'POST',
												timeout:2000,
												success:function(response,opts){
													Ext.Array.each(record,function(data){
														st.remove(data);
													});
													Ext.getCmp('roleViewId').store.reload();//////////////////////
			        							}
			        						})
			        				}
			        			})
			        		});
			        	}    
		    		}
		        }, "->", {
		    	text:'高级查询',
		    	scope:this,
		    	icon : Global_Path + '/resources/extjs/images/search.png',
	    		handler : function(btn) {
		       		var gridStore = btn.up('gridpanel').store;
		      		var queryForm = Ext.create('Bjfu.role.view.QueryRole');
		  			Ext.create('Ext.window.Window', {
						title : '角色信息高级查询',
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
	        		
	        		var roleId=record.get("id");
	        		
	        		 Ext.getCmp("resourceGroupViewId").getStore().baseParams= {
	        			 roleId: roleId
	           			};
	        		
		            Ext.getCmp("resourceGroupViewId").getStore().load({
	               			params: {
	               				roleId: roleId
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
