Ext.define('userGroupList', {
    extend: 'Ext.data.Store',
    fields: ['id', 'userGroupName'],
	proxy : {
		type : 'ajax',
		actionMethods: {
            create : 'POST',
            read   : 'POST', // by default GET
            update : 'POST',
            destroy: 'POST'
		},
		url : Global_Path+'sysuserGroup/getUserGroupList',
		reader : {
			type : 'json',
			root : 'result'
		}
	}
});


Ext.define('Bjfu.user.view.ModifyUser',{
	extend:'Ext.form.Panel',
	bodyPadding: 5,
	border:false,
	
	userGroupId:null,
	userGroupName:null,
	
	initComponent: function() {
    	var me = this;
    	var userGroupId = me.userGroupId;
    	var userGroupName=me.userGroupName; 

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
    	    },{
    	        fieldLabel: '用户登录名<font color="red">*</font>', //验重
    	        allowBlank : false,
    	        name: 'userLoginName'
    	    },{
    	        fieldLabel: '手机号码', 
    	        name: 'userPhone'
    	    },{
    	        fieldLabel: '邮件',
    	        name: 'email'
    	    },{
    	    	id : 'userGroup',
    	    	xtype : 'combo',
    	        fieldLabel : '所属用户组<font color="red">*</font>',
    	        allowBlank : false,
    	        name : 'userGroup',
    	      //  text:me.userGroupName,
    	        value:me.userGroupId,
    	        store : Ext.create('userGroupList'),
    	        editable : false,
    	        displayField : 'userGroupName',
    	        valueField : 'id',
    	        emptyText : '请选择...'	
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

