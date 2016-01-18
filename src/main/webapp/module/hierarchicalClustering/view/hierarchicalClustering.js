var hierarchicalClusteringTableListStore=new Ext.data.Store({
    fields: ['id', 'tableName'],
	proxy : {
		type : 'ajax',
		actionMethods: {
            create : 'POST',
            read   : 'POST', // by default GET
            update : 'POST',
            destroy: 'POST'
		},
		url : Global_Path+'dataClustering/hierarchicalClusteringTableList',
		reader : {
			type : 'json',
			root : 'result'
		}
	},
	autoLoad:false
});




Ext.define('Bjfu.hierarchicalClustering.view.hierarchicalClustering',{
	extend:'Ext.form.Panel',
	bodyPadding: 5,
	allTable:null,
	border:false,
	initComponent: function() {
    	var me = this;
    	hierarchicalClusteringTableListStore.load({
       		params: {
       			allTable:me.allTable
   			}
    });
    	Ext.apply(me, {
    		layout: {
		        type: 'table',
		        columns: 2
		    },
    	    defaults: {  
    	    	labelAlign:'right',
    	        margin: '3 20 3 0'
    	    },
		    defaultType: 'textfield',
    	    items: [{
    	    	xtype : 'datefield',
				fieldLabel : '开始时间',
				altFormats: 'Y-m-d',
				format : 'Y-m-d',
				id : 'startTime',
				name : 'startTime',
				maxValue : new Date(),
				editable : false,
				vtype : 'daterange',
				endDateField :"endTime"
    	    },{
    	    	xtype : 'datefield',
				fieldLabel : '结束时间',
				altFormats: 'Y-m-d',
				format : 'Y-m-d',
				id : 'endTime',
				name : 'endTime',
				maxValue : new Date(),
				vtype : 'daterange',
				editable : false,
				startDateField : "startTime"
    	    }
,{
    	    	id : 'hierarchicalClusteringCenter',
    	    	xtype : 'combo',
    	        fieldLabel : '聚类中心<font color="red">*</font>',
    	        name : 'hierarchicalClusteringCenterId',
    	        store : hierarchicalClusteringTableListStore,
    	        allowBlank : false,
    	        editable : false,
    	        displayField : 'tableName',
    	        valueField : 'id',
    	        emptyText : '请选择...'	,
    	        queryMode:'local'
    	    },{
    	        fieldLabel: '阈值', 
    	        allowBlank : false,
    	        name: 'thresHlod',
    	        listeners:{
	    	        'blur' : function(_this, the, e) {
						var v = _this.getValue();
						var vv = Ext.String.trim(v);
						_this.setValue(vv);			
							if (vv.length > 0) {
								Ext.Ajax.request({
									url : Global_Path+'dataClustering/checkThresHlod',
									params : {
										thresHlod : vv
									},
									success : function(response) {
										var result = Ext.decode(response.responseText);
										if(!result.success){
												Ext.Msg.alert("提示", "请输入正确的数字");
												_this.setValue('');
												return;
										}
									},
									failure: function(response) {
										var result = Ext.decode(response.responseText);
										Ext.Msg.alert('错误', result.__msg);
									}
								});
							}			    
    	        	}
    	        } 
    	    }],
    	    buttonAlign:'center', 
    	    buttons: [{
    			        text: '重置',
    			        handler: function() {
    			            this.up('form').getForm().reset();
    			        }
    			    },{
    			        text: '开始层次聚类(单机)',
    			        formBind: true,
    			        disabled: true,
    			        handler: function() {
    			          	var form = this.up('form').getForm();
    			            var searchJson = JSON.stringify(this.up('form').getForm().getValues());
    			            Ext.Ajax.request({
		    	 	   			url:Global_Path+'dataClustering/hierarchicalClustering',
		    	 	   			method:'post',
		    	 	   			params:{
		    	 	   			searchJson:searchJson,
		    	 	   			allTable:me.allTable
		    	 	   			},
		    	 	   		success: function(response) {
		                    	var	result =  Ext.decode(response.responseText);
		                    	if(result.success){
		                    		Ext.Msg.alert('聚类结果',result.result);
		    						window.close();
		    	 	   			Ext.getCmp('userViewId').store.reload();
		                    	}else{
		                    		Ext.Msg.alert('提示','发生错误');
		                    		window.close();
		                    	}
		                    },
		                    failure: function(form, action) {
		                        Ext.Msg.alert('Failed', action.result.msg);
		                    }
		    	 	   		});
    			            this.up('window').close();
    			        }
    			    },{
    			        text: '开始层次聚类(集群)',
    			        formBind: true,
    			        disabled: true,
    			        handler: function() {
    			          	var form = this.up('form').getForm();
    			            var searchJson = JSON.stringify(this.up('form').getForm().getValues());
    			            Ext.Ajax.request({
		    	 	   			url:Global_Path+'dataClustering/hadoopHierarchicalClustering',
		    	 	   			method:'post',
		    	 	   			params:{
		    	 	   			searchJson:searchJson,
		    	 	   			allTable:me.allTable
		    	 	   			},
		    	 	   		success: function(response) {
		                    	var	result =  Ext.decode(response.responseText);
		                    	if(result.success){
		                    		Ext.Msg.alert('聚类结果',result.result);
		    						window.close();
		    	 	   			Ext.getCmp('userViewId').store.reload();
		                    	}else{
		                    		Ext.Msg.alert('提示','发生错误');
		                    		window.close();
		                    	}
		                    },
		                    failure: function(form, action) {
		                        Ext.Msg.alert('Failed', action.result.msg);
		                    }
		    	 	   		});
    			            this.up('window').close();
    			        }
    			    }]
    	});
    	me.callParent(arguments);
	}
	
});
