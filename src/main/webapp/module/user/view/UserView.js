Ext.define('Bjfu.user.view.UserView',{
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
	selType : 'checkboxmodel',	// 单选，复选框
	requires : ['Bjfu.user.model.User'],
	
	initComponent : function() {
		var me = this;
		var gridStore = Ext.create('Ext.data.Store', {
			model : 'Bjfu.user.model.User',
			pageSize : 25,
			proxy : {
				type : 'ajax',
				actionMethods: {
	                create : 'POST',
	                read   : 'POST', // by default GET
	                update : 'POST',
	                destroy: 'POST'
				},
				url : Global_Path+'sysuser/allUserList',
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
			        dataIndex : 'userName',
			    },{
			        text : '用户登陆名',
			        dataIndex : 'userLoginName',
			    },{
			        text : '超级管理员',
			        dataIndex: 'isAdmin',
			        renderer : function (value) {
			        	if (value == "1") {
			        		return "是";
			        	} else {
			        		return "否";
			        	}
			        }
			    },{
			        text : '邮箱',
			        dataIndex : 'email',
			        width : '10%'
			    },{
			        text : '电话',
			        dataIndex : 'userPhone',
			        width : '10%'
			    },
//			    {
//			        text : '用户状态',
//			        dataIndex : 'userStatus',
//			        renderer : function (value) {
//			        	if (value == "1") {
//			        		return "正常";
//			        	} else {
//			        		return "锁定";
//			        	}
//			        }
//			    },
			    {
			        text : '创建者',
			        dataIndex : 'creater',
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
		        	var addForm = Ext.create('Bjfu.user.view.AddUser',{
		        	});
		        	Ext.create('Ext.window.Window',{
		        		title:'新增用户信息',
		        		closable:true,
		        		closeAction:'destroy',
		        		modal:true,
		        		resizable:false,
		        	    border:false,
		        		width:650,
		        		height:430,
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
					        		var userId = record[0].data.id;
					        	
						   			var modifyForm = Ext.create('Bjfu.user.view.ModifyUser',{
										userId:userId,
												});
					        	modifyForm.loadRecord(record[0]);
					        	Ext.create('Ext.window.Window',{
					        		title:'修改用户界面',
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
							   if(record.length>1||record.length==0)
						   		{
							   		Ext.Msg.alert('提示','请选择一条记录！');
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
				        							url:Global_Path+'sysuser/deleteUser',
													params:{ids:ids.join(",")},
													method:'POST',
													timeout:2000,
													success:function(response,opts){
								                    	var	result =  Ext.decode(response.responseText);
								                    	if(result.success){
															Ext.Array.each(record,function(data){
																st.remove(data);
															});
								                    		Ext.Msg.alert('提示','删除用户成功');
								    	 	   			Ext.getCmp('userViewId').store.reload();
								                    	}else{
								                    		Ext.Msg.alert('提示','该用户是超级管理员无法删除');
								                    	}
				        							}
				        						})
				        				}
				        			})
				        		});
				        	}    
			    		}
			        },{
						   text: '重置密码',
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
						        		var userId = record[0].data.id;
						        	
							   			var modifyPswForm = Ext.create('Bjfu.user.view.ModifyPassword',{
											userId:userId,
													});
							   			modifyPswForm.loadRecord(record[0]);
						        	Ext.create('Ext.window.Window',{
						        		title:'重置密码界面',
						        		closable:true,
						        		closeAction:'destroy',
						        		modal:true,
						        		border:false,
						        		resizable:false,
						        		width:400,
						        		height:250,
						        		layout:'fit',
						        		items:[modifyPswForm]
						        	}).show();
						        	
						        	}
						        	}
						        }, "->", {
		    	text:'高级查询',
		    	scope:this,
		    	icon : Global_Path + '/resources/extjs/images/search.png',
	    		handler : function(btn) {
		       		var gridStore = btn.up('gridpanel').store;
		      		var queryForm = Ext.create('Bjfu.user.view.QueryUser');
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
	        		
	        		 Ext.getCmp("indexResourceListViewId").getStore().baseParams= {
	        			 userId: userId
	           			};
	        		
		            Ext.getCmp("indexResourceListViewId").getStore().load({
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
