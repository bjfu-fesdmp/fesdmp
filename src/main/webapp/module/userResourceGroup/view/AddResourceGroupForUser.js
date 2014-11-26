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
		url : Global_Path+'resourceGroup/getResourceGroupListNotInThisUser',
		reader : {
			type : 'json',
			root : 'result'
		}
	},
	autoLoad:false
});
Ext.define('userList', {
    extend: 'Ext.data.Store',
    fields: ['id', 'userName'],
	proxy : {
		type : 'ajax',
		actionMethods: {
            create : 'POST',
            read   : 'POST', // by default GET
            update : 'POST',
            destroy: 'POST'
		},
		url : Global_Path+'sysuser/userList',
		reader : {
			type : 'json',
			root : 'result'
		}
	}
});
Ext.define('Bjfu.userResourceGroup.view.AddResourceGroupForUser',{
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
    	    	id : 'user',
    	    	xtype : 'combo',
    	        fieldLabel : '用户<font color="red">*</font>',
    	        name : 'userId',
    	        store : Ext.create('userList'),
    	        allowBlank : false,
    	        editable : false,
    	        displayField : 'userName',
    	        valueField : 'id',
    	        emptyText : '请选择...'	,
    	        listeners : { //监听该下拉列表的选择事件
    	            select : function(combo, record, index) {
    	            	Ext.getCmp('resourceGroup').clearValue();
    	            	var userId=combo.getValue();
    	                resourceGroupListStore.load({
    	               		params: {
    	               			userId: userId
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
		            	var UserValues = form.getValues();
		            		Ext.Ajax.request({
		    	 	   			url:Global_Path+'resourceGroup/addResourceGroupForUser',
		    	 	   			method:'post',
		    	 	   			params:{
		    	 	   					formData:Ext.encode(UserValues)
		    	 	   			},
		    	 	   		success: function(response) {
		                    	var	result =  Ext.decode(response.responseText);
		                    	if(result.success){
		                    		Ext.Msg.alert('提示','为用户添加资源组成功');
		    						window.close();
		    	 	   			Ext.getCmp('resourceGroupViewId').store.reload();
		    	 	   			Ext.getCmp('resourceGroupViewId').store.loadRawData();
		    	 	   			
		                    	}else{
		                    		Ext.Msg.alert('提示','为用户添加资源组失败');
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

