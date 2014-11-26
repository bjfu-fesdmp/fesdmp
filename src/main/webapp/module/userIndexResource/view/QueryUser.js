Ext.define('Bjfu.userIndexResource.view.QueryUser',{
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
    	        id : 'userLoginName',
    	    	xtype : 'textfield',
    	        fieldLabel: '用户登录名',
    	        name: 'userLoginName'
    	    },{
    	    	id : 'email',
    	    	xtype : 'textfield',
    	        fieldLabel: '邮箱',
    	        name: 'email'
    	    },{
    	    	id : 'userPhone',
    	    	xtype : 'textfield',
    	        fieldLabel: '电话',
    	        name: 'userPhone'
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
		            Ext.getCmp("userViewId").getStore().loadPage(1, {
		               		params: {
		           				searchJson: searchJson
		           			}
		            });
		            this.up('window').close();
		        }
		    }]
});

