Ext.define('roleList', {
    extend: 'Ext.data.Store',
    fields: ['id', 'roleName'],
	proxy : {
		type : 'ajax',
		actionMethods: {
            create : 'POST',
            read   : 'POST', // by default GET
            update : 'POST',
            destroy: 'POST'
		},
		url : Global_Path+'role/roleList',
		reader : {
			type : 'json',
			root : 'result'
		}
	}
});

Ext.define('Bjfu.userGroup.view.AddUserGroup',{
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
    	        fieldLabel: '用户组名称<font color="red">*</font>',//验重
    	        name: 'userGroupName',
    	        allowBlank : false,
    	        maxLength : 50,
				maxLengthText : '长度不能超过50个字符',    	        
    	    },{
    	    	fieldLabel:'用户组编号',
    	    	name:'id',
    	    	xtype:'hiddenfield'
    	    },{
    	    	id : 'role',
    	    	xtype : 'combo',
    	        fieldLabel : '所属角色<font color="red">*</font>',
    	        allowBlank : false,
    	        name : 'role',
    	        store : Ext.create('roleList'),
    	        editable : false,
    	        displayField : 'roleName',
    	        valueField : 'id',
    	        emptyText : '请选择...'	
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
		            	var userGroupValues = form.getValues();
		            		Ext.Ajax.request({
		    	 	   			url:Global_Path+'sysuserGroup/addUserGroup',
		    	 	   			method:'post',
		    	 	   			params:{
		    	 	   					formData:Ext.encode(userGroupValues)
		    	 	   			},
		    	 	   		success: function(response) {
		                    	var	result =  Ext.decode(response.responseText);
		                    	if(result.success){
		                    		Ext.Msg.alert('提示','添加用户组成功');
		    						window.close();
		    	 	   			Ext.getCmp('userGroupViewId').store.reload();
		                    	}else{
		                    		Ext.Msg.alert('提示','添加用户组失败');
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

