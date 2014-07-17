Ext.define('Bjfu.log.view.QueryLog',{
	extend:'Ext.form.Panel',
	bodyPadding: 5,
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
    	    	id : 'userName',
    	    	xtype : 'textfield',
    	        fieldLabel: '用户名',
    	        name: 'userName'
    	    },{
    	        id : 'userSourceIp',
    	    	xtype : 'textfield',
    	        fieldLabel: '用户源地址',
    	        name: 'userSourceIp'
    	    },{
    	    	id : 'businessType',
    	    	xtype : 'combo',
    	        fieldLabel : '业务类型',
    	        name : 'businessType',
    	        store : Ext.create('Bjfu.fesdmp.BusinessType'),
    	        editable : false,
    	       // mode : 'remote',
    	        displayField : 'name',
    	        valueField : 'val',
    	        emptyText : '请选择...'
    	    },{
    	    	id : 'operationType',
    	    	xtype : 'combo',
    	        fieldLabel: '操作类型',
    	        name: 'operationType',
    	        store : Ext.create('Bjfu.fesdmp.OperatitonType'),
    	        editable : false,
    	      //  mode : 'remote',
    	        displayField : 'name',
    	        valueField : 'val',
    	        emptyText : '请选择...'
    	    },{
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
    	    },{
    	    	id : 'operateContent',
    	    	xtype : 'textfield',
    	        fieldLabel: '操作内容',
    	        name: 'operateContent'
    	    }]
    	});
    	me.callParent(arguments);
	},
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
		            Ext.getCmp("logListViewId").getStore().loadPage(1, {
		               		params: {
		           				searchJson: searchJson
		           			}
		            });
		            this.up('window').close();
		        }
		    }]
});

