Ext.define('Bjfu.indexResource.view.QueryIndexResource',{
	extend: 'Ext.form.Panel',
	bodyPadding: 5,
	border: false,
	initComponent: function() {
		var me = this;
		
		Ext.apply(me,{
			layout: {
				type : 'table',
				column: 2
			},
			defaults: {  
    	    	labelAlign:'right',
    	        margin: '3 20 3 0'
    	    },
    	    defaultType: 'textfield',
    	    items: [{
    	    	id: 'indexName',
    	    	xtype: 'textfield',
    	    	fieldLabel: '指标中文名',
    	    	name: 'indexName'
    	    },{
    	    	id: 'indexEnName',
    	    	xtype: 'textfield',
    	    	fieldLabel: '英文指标名',
    	    	name: 'indexEnName'
    	    }]
		});
		me.callParent(arguments);
	},
	buttonAlign: 'center',
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
			Ext.getCmp("indexResourceListViewId").getStore().loadPage(1,{
				params: {
       				searchJson: searchJson
       			}
			});
			this.up('window').close();
		}
	}]
});