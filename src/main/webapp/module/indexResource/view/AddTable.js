Ext.define('Bjfu.indexResource.view.AddTable',{
	extend:'Ext.form.Panel',
	bodyPadding: 5,
	border:false,
	initComponent: function() {
    	var me = this;
    	
    	Ext.apply(me, {
    		layout: 'column',
    	    defaults: {  
    	    	labelAlign:'right',
    	        layout: 'anchor',
    	        columnWidth:.90,
    	        margin: '3 25 3 0',
    	        defaults: {
    	            anchor: '100%'
    	        }
    	    },
		    defaultType: 'textfield',
    	    items: [{
        	    fieldLabel : '指标英文名<font color="red">*</font>', 
    	        name: 'indexEnName',
    	        allowBlank : false,
    	        hidden:true
    	    	},{
    	        fieldLabel : '年份<font color="red">*</font>',
    	        name: 'year',
    	        allowBlank : false,
    	        maxLength : 4    	        
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
		        text: '保存',
		        formBind: true,
		        disabled: true,
		        handler: function() {
		            var form = this.up('form').getForm();
		            var window = this.up('window');
		            if (form.isValid()) {
		            	var tableValues = form.getValues();
		            		Ext.Ajax.request({
		    	 	   			url:Global_Path+'indexresource/addTable',
		    	 	   			method:'post',
		    	 	   			params:{
		    	 	   					formData:Ext.encode(tableValues)
		    	 	   			},
		    	 	   		success: function(response) {
		                    	var	result =  Ext.decode(response.responseText);
		                    	if(result.success){
		                    		Ext.Msg.alert('提示','添加表成功');
		    						window.close();
		    	 	   			Ext.getCmp('indexResourceListViewId').store.reload();
		                    	}else{
		                    		Ext.Msg.alert('提示','添加表失败');
		                    		window.close();
		                    	}
		                    },
		                    failure: function(form, action) {
		                        Ext.Msg.alert('Failed', action.result.msg);
		                    }
		    	 	   		});
		            }
		        }
		    }]
});

