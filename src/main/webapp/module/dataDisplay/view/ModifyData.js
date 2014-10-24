Ext.define('Bjfu.dataDisplay.view.ModifyData',{
	extend:'Ext.form.Panel',
	bodyPadding: 5,
	border:false,
	tableName:null,
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
    	        fieldLabel: '数据值<font color="red">*</font>',
    	        name: 'data',
    	        allowBlank : false,
    	        maxLength : 50,
				maxLengthText : '长度不能超过50个字符'   	        
    	    },{
    	    	fieldLabel:'数据编号',
    	    	name:'id',
    	    	xtype:'hiddenfield'
    	    }], 
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
		            	var dataValues = form.getValues();
		            		Ext.Ajax.request({
		    	 	   			url:Global_Path+'dataDisplay/modifyData',
		    	 	   			method:'post',
		    	 	   			params:{
		    	 	   					formData:Ext.encode(dataValues),
		    	 	   					tableName:me.tableName
		    	 	   			},
		    	 	   		success: function(response) {
		                    	var	result =  Ext.decode(response.responseText);
		                    	if(result.success){
		                    		Ext.Msg.alert('提示','修改数据成功');
		    						window.close();
		    	 	   			Ext.getCmp('dataDisplayId').store.reload();
		    	 	   			Ext.getCmp('dataDisplayId').store.loadRawData();
		                    	}else{
		                    		Ext.Msg.alert('提示','修改数据失败');
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
    	me.callParent(arguments);
	},
	buttonAlign:'center'
});

