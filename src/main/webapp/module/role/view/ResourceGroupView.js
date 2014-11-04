Ext.define('Bjfu.role.view.ResourceGroupView',{
	extend : 'Ext.tree.Panel',
	forceFit : true,
	layout : 'fit',
    autoScroll: true,
	layoutConfig : {
		animate : true
	},
	rootVisible: false,
	displayField:'groupName',
	requires : ['Bjfu.resourceGroup.model.ResourceGroup'],
	initComponent : function() {
		var me = this;
		var gridStore = Ext.create('Ext.data.TreeStore', {
			id : 'resourceGroup.tree',
			model : 'Bjfu.resourceGroup.model.ResourceGroup',
			nodeParam : 'groupParentId',
			proxy : {
				type : 'ajax',
				actionMethods: {
	                create : 'POST',
	                read   : 'POST', // by default GET
	                update : 'POST',
	                destroy: 'POST'
				},
				url : Global_Path+'resourceGroup/resourceGroupOfRoleList',
				reader : {
					type : 'json',
					root : 'result',
				}
			},
			root: {
				   nodeType: 'async',
				   id : '0',
				   expanded: true
		    },
			autoLoad : true
		});
		
		Ext.apply(me, {
			store : gridStore,
			forceFit:true,
			columns : [
				{
					text : '资源组id',
					sortable : true,
			        dataIndex : 'id',
			        hidden : true
				},{
			        text : '父Id',
			        dataIndex : 'groupParentId',
			        hidden : true
			    },{
			    	xtype : 'treecolumn',
			    	flex : 1,
					sortable : true,
			        text : '资源组名',
			        dataIndex : 'groupName'
			    },{
			        text : '资源组描述',
			        dataIndex : 'memo'
			    },{
			        text : '叶子',
			        dataIndex : 'leaf',
			        hidden : true
			    }
			],
			tbar : [{
		          text: '为角色添加资源组',
		          scope:this,
		          icon:Global_Path+'/resources/extjs/images/add.png',
		          handler : function(){
		        	var addForm = Ext.create('Bjfu.role.view.AddResourceGroupForRole',{
		        	});
		        	Ext.create('Ext.window.Window',{
		        		title:'为角色添加资源组',
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
		        	text: '为角色删除资源组' ,
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
			        		var roleId=gird.getStore().baseParams;
			        		Ext.Array.each(record,function(data){
			        			id=data.get('id');
			        			Ext.Msg.confirm("提示","确定删除所选记录吗？",function(btn){
			        				if(btn=='yes'){
			        						Ext.Ajax.request({
			        							url:Global_Path+'role/deleteResourceGroupForRole',
												params:{id:id,roleId:roleId
													},
												method:'POST',
												timeout:2000,
												success:function(response,opts){
							                    	var	result =  Ext.decode(response.responseText);
							                    	if(result.success){
														Ext.Array.each(record,function(data){
															st.remove(data);
														});
							                    		Ext.Msg.alert('提示','为角色删除资源组成功');
							    						window.close();
							    						Ext.getCmp('roleViewId').store.reload();
								    	 	   			Ext.getCmp('resourceGroup').store.reload();
							    	 	   			
							                    	}else{
							                    		Ext.Msg.alert('提示','为角色删除资源组失败');
							                    		window.close();
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
		});
		
		me.callParent(arguments);
	}
});