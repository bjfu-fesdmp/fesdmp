Ext.define('Bjfu.userResourceGroup.view.ResourceGroupView',{
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
				url : Global_Path+'resourceGroup/resourceGroupOfUserList',
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
			        width:'40%',
			        dataIndex : 'groupName'
			    },{
			        text : '资源组描述',
			        width:'60%',
			        dataIndex : 'memo'
			    },{
			        text : '叶子',
			        dataIndex : 'leaf',
			        hidden : true
			    }
			],
			tbar : [{
		          text: '为用户添加资源组',
		          scope:this,
		          icon:Global_Path+'/resources/extjs/images/add.png',
		          handler : function(){
		        	var addForm = Ext.create('Bjfu.userResourceGroup.view.AddResourceGroupForUser',{
		        	});
		        	Ext.create('Ext.window.Window',{
		        		title:'为用户添加资源组',
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
		        	text: '为用户删除资源组' ,
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
			        							url:Global_Path+'resourceGroup/deleteResourceGroupForUser',
												params:{id:id,userId:userId
													},
												method:'POST',
												timeout:2000,
												success:function(response,opts){
							                    	var	result =  Ext.decode(response.responseText);
						                    		Ext.Msg.alert('提示','为用户删除资源组成功');
						    						window.close();
							    	 	   			Ext.getCmp('resourceGroupViewId').store.reload();
							    	 	   			Ext.getCmp('resourceGroupViewId').store.loadRawData();
							                    	if(result.success){
														Ext.Array.each(record,function(data){
															st.remove(data);
														});
							                    	}else{
							                    		Ext.Msg.alert('提示','为用户删除资源组失败');
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
