Ext.define('Bjfu.resourceGroup.view.QueryResourceGroup',{
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
    	    	id : 'groupName',
    	    	xtype : 'textfield',
    	        fieldLabel: '资源组名',
    	        name: 'groupName'
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
		            Ext.getCmp("resourceGroupViewId").getStore().loadPage(1, {
		               		params: {
		           				searchJson: searchJson
		           			}
		            });
		            this.up('window').close();
		        }
		    }]
});

