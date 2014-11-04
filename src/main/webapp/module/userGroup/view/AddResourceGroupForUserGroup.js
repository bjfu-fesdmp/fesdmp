var resourceGroupListStore=new Ext.data.Store({
    fields: ['id', 'groupName'],
	proxy : {
		type : 'ajax',
		actionMethods: {
            create : 'POST',
            read   : 'POST', // by default GET
            update : 'POST',
            destroy: 'POST'
		},
		url : Global_Path+'resourceGroup/getResourceGroupListNotInThisUserGroup',
		reader : {
			type : 'json',
			root : 'result'
		}
	},
	autoLoad:false
});
Ext.define('userGroupList', {
    extend: 'Ext.data.Store',
    fields: ['userGroupId', 'userGroupName'],
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
Ext.define('Bjfu.userGroup.view.AddResourceGroupForUserGroup',{
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
    	    	id : 'userGroup',
    	    	xtype : 'combo',
    	        fieldLabel : '用户组<font color="red">*</font>',
    	        name : 'userGroupId',
    	        store : Ext.create('userGroupList'),
    	        allowBlank : false,
    	        editable : false,
    	        displayField : 'userGroupName',
    	        valueField : 'userGroupId',
    	        emptyText : '请选择...'	,
    	        listeners : { //监听该下拉列表的选择事件
    	            select : function(combo, record, index) {
    	            	Ext.getCmp('resourceGroup').clearValue();
    	            	var userGroupId=combo.getValue();
    	                resourceGroupListStore.load({
    	               		params: {
    	               			userGroupId: userGroupId
    	           			}
    	            });
    	            }
    	    
    	        }
    	    },{
    	    	id : 'resourceGroup',
    	    	xtype : 'combo',
    	        fieldLabel : '资源组<font color="red">*</font>',
    	        name : 'resourceGroupId',
    	        store : resourceGroupListStore,
    	        allowBlank : false,
    	        editable : false,
    	        displayField : 'groupName',
    	        valueField : 'id',
    	        emptyText : '请选择...'	,
    	        queryMode:'local'
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
		            	var UserGroupValues = form.getValues();
		            		Ext.Ajax.request({
		    	 	   			url:Global_Path+'resourceGroup/addResourceGroupForUserGroup',
		    	 	   			method:'post',
		    	 	   			params:{
		    	 	   					formData:Ext.encode(UserGroupValues)
		    	 	   			},
		    	 	   		success: function(response) {
		                    	var	result =  Ext.decode(response.responseText);
		                    	if(result.success){
		                    		Ext.Msg.alert('提示','为用户组添加资源组成功');
		    						window.close();
		    	 	   			Ext.getCmp('resourceGroup').store.reload();
		    	 	   			Ext.getCmp('resourceGroup').store.loadRawData();
		    	 	   			
		                    	}else{
		                    		Ext.Msg.alert('提示','为用户组添加资源组失败');
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

