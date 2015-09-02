


Ext.define('Bjfu.unionDataClustering.view.DataDisplayView',{
	extend : 'Ext.grid.Panel',
	id:'unionDataClusteringView',
	alias:'widget.UnionDataClusteringView',
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
	requires : ['Bjfu.unionDataClustering.model.DataDisplay'],
	
	initComponent : function() {
		var me = this;
		var gridStore = Ext.create('Ext.data.Store', {
			model : 'Bjfu.unionDataClustering.model.DataDisplay',
			pageSize : 25,
			proxy : {
				type : 'ajax',
				actionMethods: {
	                create : 'POST',
	                read   : 'POST', // by default GET
	                update : 'POST',
	                destroy: 'POST'
				},
				url : Global_Path+'dataDisplay/unionDataDisplayList',
				reader : {
					type : 'json',
					root : 'result',
					idProperty : 'id',
					totalProperty : 'pageCount'
				},
				autoload:false
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
			    }, {
			        text : '站点',
			        dataIndex : 'station',
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
			tbar : [
			       { 
		          text: 'k-means聚类',
		          id:'KMeans',
		          scope:this,
		          icon:Global_Path+'/resources/extjs/images/up2.gif',
		          handler : function(btn){
		        	var tableName=gridStore.baseParams.tableName;
                  	var grid = btn.up('gridpanel').store;
                	var ids = [];
                	
                	grid.each(function (record) {  
                		ids.push(record.get('data')); 
                	}); 
		      			Ext.Ajax.request({
		      				url : Global_Path+'sysuser/checkUnionFunctionIfForbid',
		      				params:{
		      					tableName:tableName
		      					},
		      				success : function(response) {
		      					var result = Ext.decode(response.responseText);
		      					if(result.success){
		      						Ext.Msg.alert('提示','您并没有获得该权限');
		      						return;
		      					}
		      					else{
		      			        	var KMeansForm = Ext.create('Bjfu.unionDataClustering.view.KMeans',{
		      			        		tableName:tableName,
		      			        		ids:ids.join(",")
		      			        	});
		      			        	  
		      			        	Ext.create('Ext.window.Window',{
		      			        		title:'k-means聚类',
		      			        		closable:true,
		      			        		closeAction:'destroy',
		      			        		modal:true,
		      			        		resizable:false,
		      			        	    border:false,
		      			        		width:300,
		      			        		height:150,
		      			        		layout:'fit',
		      			        		items:[KMeansForm]
		      			        	}).show();
		      			        	
		      			        	
		      					}
		      				}
		      			});

		        	} 
		        },{ 
			          text: 'k-medoids聚类',
			          id:'Kedoids',
			          scope:this,
			          icon:Global_Path+'/resources/extjs/images/up2.gif',
			          handler : function(btn){
			        	var tableName=gridStore.baseParams.tableName;
	                  	var grid = btn.up('gridpanel').store;
	                	var ids = [];
	                	
	                	grid.each(function (record) {  
	                		ids.push(record.get('data')); 
	                	}); 
			      			Ext.Ajax.request({
			      				url : Global_Path+'sysuser/checkUnionFunctionIfForbid',
			      				params:{
			      					tableName:tableName
			      					},
			      				success : function(response) {
			      					var result = Ext.decode(response.responseText);
			      					if(result.success){
			      						Ext.Msg.alert('提示','您并没有获得该权限');
			      						return;
			      					}
			      					else{
			      			        	var KMedoidsForm = Ext.create('Bjfu.unionDataClustering.view.KMedoids',{
			      			        		tableName:tableName,
			      			        		ids:ids.join(",")
			      			        	});
			      			        	  
			      			        	Ext.create('Ext.window.Window',{
			      			        		title:'k-medoids聚类',
			      			        		closable:true,
			      			        		closeAction:'destroy',
			      			        		modal:true,
			      			        		resizable:false,
			      			        	    border:false,
			      			        		width:300,
			      			        		height:150,
			      			        		layout:'fit',
			      			        		items:[KMedoidsForm]
			      			        	}).show();
			      					}
			      				}
			      			});

			        	} 
			        },"->", {
		    	text:'高级查询',
		    	scope:this,
		    	icon : Global_Path + '/resources/extjs/images/search.png',
	    		handler : function(btn) {
		       		var gridStore = btn.up('gridpanel').store;
		       		var tableName=gridStore.baseParams.tableName;
		      		var queryForm = Ext.create('Bjfu.unionDataClustering.view.QueryData',{
		        		tableName:tableName
		        	});
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
			loadMask:true
		});
		me.callParent(arguments);
	}
});