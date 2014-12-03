Ext.apply(Ext.form.VTypes, {
	password : function(val, field) {
		var pwd = field.compareTo;
		return (val == Ext.getCmp(pwd).getValue());
		},
	passwordText : "确认密码不一致！！！"
});

Ext.define('Bjfu.user.view.ModifyPassword',{
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
    	        fieldLabel: '新登录密码<font color="red">*</font>',
    	        name: 'password',
    	        id: 'passwordId',
    	        inputType : 'password',
    	        allowBlank : false,
				blankText : "不能为空，请填写",
				maxLength : 30
    	    },{
    	        fieldLabel: '确认密码<font color="red">*</font>',
    	        name: 'checkPwd',
    	        inputType : 'password',
    	        vtype : 'password',
				compareTo : 'passwordId',
    	        allowBlank : false,
				blankText : "不能为空，请填写",
				maxLength : 30
    	    },{
    	    	fieldLabel:'用户编号',
    	    	name:'id',
    	    	xtype:'hiddenfield'
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
		            	var userValues = form.getValues();
		            		Ext.Ajax.request({
		    	 	   			url:Global_Path+'sysuser/modifyPassword',
		    	 	   			method:'post',
		    	 	   			params:{
		    	 	   					formData:Ext.encode(userValues)
		    	 	   			},
		    	 	   		success: function(response) {
		                    	var	result =  Ext.decode(response.responseText);
		                    	if(result.success){
		                    		Ext.Msg.alert('提示','修改密码成功');
		    						window.close();
		                    	}else{
		                    		Ext.Msg.alert('修改','添加密码失败');
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

