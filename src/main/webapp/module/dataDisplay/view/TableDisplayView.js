Ext.define('Bjfu.dataDisplay.view.TableDisplayView',{
	extend:'Ext.tree.Panel',
	rootVisible: false,
	displayField:'text',
	requires : ['Bjfu.dataDisplay.model.TableDisplay'],
	initComponent : function() {
		var me = this;
		var gridStore = Ext.create('Ext.data.TreeStore', {
			id : 'table.tree',
			model : 'Bjfu.dataDisplay.model.TableDisplay',
			nodeParam : 'parentId',
			proxy : {
				type : 'ajax',
				actionMethods: {
	                create : 'POST',
	                read   : 'POST', // by default GET
	                update : 'POST',
	                destroy: 'POST'
				},
				url : Global_Path+'dataDisplay/tableList',
				reader : {
					type : 'json',
					root : 'result'
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
			columns : [
			    {
			    	xtype : 'treecolumn',
			    	flex : 1,
					sortable : true,
			        //text : '数据表',
			        dataIndex : 'text'
			        
			    }, {
					text : '编号',
					hidden:true,
					sortable : true,
					dataIndex : 'id'
				},{
					text : '父级导航',
					hidden:true,
					dataIndex : 'parentId'
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
	        		var name=record.get("text");
	        		
	        		 Ext.getCmp("dataDisplayId").getStore().baseParams= {
	               			tableName: name
	           			};
		            Ext.getCmp("dataDisplayId").getStore().loadPage(1, {
	               		params: {
	               			tableName: name
	           			}
	            });
	        	}
			},
		loadMask:true
		});

		me.callParent(arguments);
	}
});
