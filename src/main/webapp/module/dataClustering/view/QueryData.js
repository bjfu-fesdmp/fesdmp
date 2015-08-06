Ext.define('Bjfu.dataClustering.view.QueryData',{
	extend:'Ext.form.Panel',
	bodyPadding: 5,
	tableName:null,
	border:false,
	initComponent: function() {
    	var me = this;
    	
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
    			           				searchJson: searchJson
    			           			}
    			            });
    			            this.up('window').close();
    			        }
    			    }]
    	});
    	me.callParent(arguments);
	}
	
});

