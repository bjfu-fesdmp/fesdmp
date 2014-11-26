Ext.define('Bjfu.user.view.ModifyUser',{
	extend:'Ext.form.Panel',
	bodyPadding: 5,
	border:false,
	
	userGroupId:null,
	userGroupName:null,
	
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
    	        fieldLabel: '用户名称<font color="red">*</font>',//验重
    	        name: 'userName',
    	        allowBlank : false,
    	        maxLength : 50,
				maxLengthText : '长度不能超过50个字符',  
    	        listeners:{
	    	        'blur' : function(_this, the, e) {
						var v = _this.getValue();
						var vv = Ext.String.trim(v);
						_this.setValue(vv);			
							if (vv.length > 0) {
								Ext.Ajax.request({
									url : Global_Path+'sysuser/checkUserName',
									params : {
										userName : vv
									},
									success : function(response) {
										var result = Ext.decode(response.responseText);
										if(!result.success){
												Ext.Msg.alert("提示", "该用户名已经存在");
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
    	    },{
    	        fieldLabel: '用户登录名<font color="red">*</font>', //验重
    	        allowBlank : false,
    	        name: 'userLoginName',
    	        listeners:{
	    	        'blur' : function(_this, the, e) {
						var v = _this.getValue();
						var vv = Ext.String.trim(v);
						_this.setValue(vv);			
							if (vv.length > 0) {
								Ext.Ajax.request({
									url : Global_Path+'sysuser/checkUserLoginName',
									params : {
										userLoginName : vv
									},
									success : function(response) {
										var result = Ext.decode(response.responseText);
										if(!result.success){
												Ext.Msg.alert("提示", "该用户登录名已经存在");
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
    	    },{
    	        fieldLabel: '手机号码', 
    	        name: 'userPhone'
    	    },{
    	        fieldLabel: '邮件',
    	        name: 'email'
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
		    	 	   			url:Global_Path+'sysuser/modifyUser',
		    	 	   			method:'post',
		    	 	   			params:{
		    	 	   					formData:Ext.encode(userValues)
		    	 	   			},
		    	 	   		success: function(response) {
		                    	var	result =  Ext.decode(response.responseText);
		                    	if(result.success){
		                    		Ext.Msg.alert('提示','修改用户成功');
		    						window.close();
		    	 	   			Ext.getCmp('userViewId').store.reload();
		    	 	   			Ext.getCmp('userViewId').store.loadRawData();
		                    	}else{
		                    		Ext.Msg.alert('修改','添加用户失败');
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

