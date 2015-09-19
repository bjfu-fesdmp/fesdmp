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
    	    	id : 'hierarchicalClusteringTable',
    	    	xtype : 'combo',
    	        fieldLabel : '数据表<font color="red">*</font>',
    	        name : 'hierarchicalClusteringTableId',
    	        store : hierarchicalClusteringTableListStore,
    	        allowBlank : false,
    	        editable : false,
    	        displayField : 'tableName',
    	        valueField : 'id',
    	        emptyText : '请选择...'	,
    	      //  queryMode:'local',
    	    }],
    	    buttonAlign:'center', 
    	    buttons: [{
    			        text: '重置',
    			        handler: function() {

    			    		
    			            this.up('form').getForm().reset();
    			        }
    			    },{
    			        text: '查询',
    			        formBind: true,
    			        disabled: true,
    			        handler: function() {
    			          	var form = this.up('form').getForm();
    			            var searchJson = JSON.stringify(this.up('form').getForm().getValues());
    			            Ext.getCmp("dataClusteringId").getStore().loadPage(1, {
    			               		params: {
    			           				searchJson: searchJson,
    			           				tableName:me.tableName
    			           			}
    			            });
    			            this.up('window').close();
    			        }
    			    }]
    	});
    	me.callParent(arguments);
	}
	
});
