Ext.define('Bjfu.location.view.LocationListView',{
	extend : 'Ext.grid.Panel',
	alias:'widget.LocationListView',
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
	requires : ['Bjfu.location.model.Location'],
	
	initComponent : function() {
		var me = this;
		var gridStore = Ext.create('Ext.data.Store', {
			model : 'Bjfu.location.model.Location',
			pageSize : 25,
			proxy : {
				type : 'ajax',
				actionMethods: {
	                create : 'POST',
	                read   : 'POST', // by default GET
	                update : 'POST',
	                destroy: 'POST'
				},
				/*extraParams: {  
	                searchJson : '{ismpewStatus : 1}'
	            },*/  
				url : Global_Path+'location/locationList',
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
			/*sorters : ["ProductName", "CatID"],
			filters : [{"property" : "ProductName" ,"value" : 1}],*/
			autoLoad : true
		});
		
		Ext.apply(me, {
			store : gridStore,
			forceFit:true,
			columns : [
				{
					text : '区域编号',
			        dataIndex : 'id',
			        hidden : true
				},
				Ext.create('Ext.grid.RowNumberer',{
			          		header : '序号',
			          		align: 'left',
			          		width : 60
			    }),{
			        text : '区域名称',
			        dataIndex : 'locationName',
			        width : '30%'
			    },{
			        text : '注释',
			        dataIndex : 'memo',
			        width : '70%'
			    }
			],
			tbar : [{ 
		          text: '新增',
		          scope:this,
		          icon:Global_Path+'/resources/extjs/images/add.png',
		          handler : function(){
		        	var addForm = Ext.create('Bjfu.location.view.AddLocation',{
		        	});
		        	Ext.create('Ext.window.Window',{
		        		title:'新增区域信息',
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
					        		var locationId = record[0].data.id;
					        	
						   			var modifyForm = Ext.create('Bjfu.location.view.ModifyLocation',{
						   				locationId:locationId,
												});
					        	modifyForm.loadRecord(record[0]);
					        	Ext.create('Ext.window.Window',{
					        		title:'修改区域界面',
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
				        							url:Global_Path+'location/deleteLocation',
													params:{ids:ids.join(",")},
													method:'POST',
													timeout:2000,
													success:function(response,opts){
								                    	var	result =  Ext.decode(response.responseText);
								                    	if(result.success){
															Ext.Array.each(record,function(data){
																st.remove(data);
															});
								                    		Ext.Msg.alert('提示','删除区域成功');
								    	 	   			Ext.getCmp('locationListViewId').store.reload();
								                    	}else{
								                    		Ext.Msg.alert('提示','该区域无法删除');
								                    	}
				        							}
				        						})
				        				}
				        			})
				        		});
				        	}    
			    		}
			        },"->"
			        /*			        ,{
		    	text:'高级查询',
		    	scope:this,
		    	icon : Global_Path + '/resources/extjs/images/search.png',
	    		handler : function(btn) {
		       		var gridStore = btn.up('gridpanel').store;
		      		var queryForm = Ext.create('Bjfu.location.view.QueryLocation');
		  			Ext.create('Ext.window.Window', {
						title : '区域信息高级查询',
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
			}
			     */   
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
	}
});
