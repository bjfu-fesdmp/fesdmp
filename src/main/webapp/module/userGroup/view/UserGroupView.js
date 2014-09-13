Ext.define('Bjfu.userGroup.view.UserGroupView',{
	extend : 'Ext.grid.Panel',
	alias:'widget.UserGroupView',
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
	requires : ['Bjfu.userGroup.model.UserGroup'],
	
	initComponent : function() {
		var me = this;
		var gridStore = Ext.create('Ext.data.Store', {
			model : 'Bjfu.userGroup.model.UserGroup',
			pageSize : 25,
			proxy : {
				type : 'ajax',
				actionMethods: {
	                create : 'POST',
	                read   : 'POST', // by default GET
	                update : 'POST',
	                destroy: 'POST'
				},
				url : Global_Path+'sysuserGroup/userGroupList',
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
					text : '用户组id',
			        dataIndex : 'id',
			        hidden : true
				},
				Ext.create('Ext.grid.RowNumberer',{
			          		header : '序号',
			          		align: 'left',
			          		width : 60
			    }),{
			        text : '用户组名',
			        dataIndex : 'userGroupName',
			    },{
			        text : '创建者id',
			        dataIndex : 'createrId',
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
		        	var addForm = Ext.create('Bjfu.userGroup.view.AddUserGroup',{
		        	});
		        	Ext.create('Ext.window.Window',{
		        		title:'新增用户信息',
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
			        	
			        	var modifyForm = Ext.create('Bjfu.userGroup.view.ModifyUserGroup',{
										});
			        	modifyForm.loadRecord(record[0]);
			        	Ext.create('Ext.window.Window',{
			        		title:'修改用户组界面',
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
			        							url:Global_Path+'sysuserGroup/deleteUserGroup',
												params:{ids:ids.join(",")},
												method:'POST',
												timeout:2000,
												success:function(response,opts){
							                    	var	result =  Ext.decode(response.responseText);
							                    	if(result.success){
														Ext.Array.each(record,function(data){
															st.remove(data);
														});
							                    		Ext.Msg.alert('提示','删除用户组成功');
							    						window.close();
							    	 	   			Ext.getCmp('userGroupViewId').store.reload();
							                    	}else{
							                    		Ext.Msg.alert('提示','该用户组下有用户，无法删除');
							                    		window.close();
							                    	}
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
		      		var queryForm = Ext.create('Bjfu.userGroup.view.QueryUserGroup');
		  			Ext.create('Ext.window.Window', {
						title : '用户组信息高级查询',
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
