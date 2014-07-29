Ext.define('Bjfu.user.view.AddUser',{
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
    	        columnWidth:.50,
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
									url : Global_Path + '/smp/user!checkUserName',
									params : {
										userName : vv
									},
									success : function(response) {
										var result = Ext.decode(response.responseText);
										if(result.success){
											if(result.__msg == 'false'){
												_this.setValue('');
												Ext.Msg.alert("提示", "该用户名已经存在");
												return;
												}
										}else{
												Ext.Msg.alert("错误",result.__msg);
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
    	        fieldLabel: '用户登录名', 
    	        name: 'userLogname'
    	    },{
    	        fieldLabel: '手机号码', 
    	        name: 'phone'
    	    },{
    	        fieldLabel: '邮件',
    	        name: 'email'
    	    },{
    	        fieldLabel: '登录密码<font color="red">*</font>',
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
    	    },{
    	    	fieldLabel:'锁定状态',
    	    	name:'status',
    	    	xtype:'hiddenfield',
    	    	value:1
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
		    	 	   			url:Global_Path+'/smp/user!addUserList',
		    	 	   			method:'post',
		    	 	   			params:{
		    	 	   					formData:Ext.encode(userValues)
		    	 	   			},
		    	 	   		success: function(response) {
		                    	var	result =  Ext.decode(response.responseText);
		                    	if(result.success){
		                    		Ext.Msg.alert('提示',result.__msg);
		    						window.close();
		    	 	   			Ext.getCmp('userGrid').store.reload();
		    	 	   			Ext.getCmp('userGrid').store.loadRawData();
		                    	}else{
		                    		Ext.Msg.alert('提示',result.__msg);
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

