Ext.define('Bjfu.resourceGroup.view.ResourceGroupView',{
	extend : 'Ext.tree.Panel',
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
				url : Global_Path+'resourceGroup/resourceGroupList',
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
			    }
			],
			tbar : [{ 
		          text: '新增',
		          scope:this,
		          icon:Global_Path+'/resources/extjs/images/add.png',
		          handler : function(){
		        	var addForm = Ext.create('Bjfu.resourceGroup.view.AddResourceGroup',{
		        	});
		        	Ext.create('Ext.window.Window',{
		        		title:'新增资源组',
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
				        	
				        	var modifyForm = Ext.create('Bjfu.resourceGroup.view.ModifyResourceGroup',{
											});
				        	modifyForm.loadRecord(record[0]);
				        	Ext.create('Ext.window.Window',{
				        		title:'修改资源组界面',
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
			        							url:Global_Path+'resourceGroup/deleteResourceGroup',
												params:{ids:ids.join(",")},
												method:'POST',
												timeout:2000,
												success:function(response,opts){
													Ext.Array.each(record,function(data){
														st.remove(data);
													});
													Ext.getCmp('resourceGroupViewId').store.reload();
			        							}
			        						})
			        				}
			        			})
			        		});
			        	}    
		    		}
		        }
		        ],
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
		        		var resourceGroupId=record.get("id");
		        		 Ext.getCmp("indexResourceListViewId").getStore().baseParams= {
		        			 resourceGroupId: resourceGroupId
		           			};
			            Ext.getCmp("indexResourceListViewId").getStore().loadPage(1, {
		               		params: {
		               			resourceGroupId: resourceGroupId
		           			}
		            });
		        	}
				},
			loadMask:true,
		});
		
		me.callParent(arguments);
	}
});
