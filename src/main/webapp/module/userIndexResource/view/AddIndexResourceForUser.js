Ext.define('locationList', {
    extend: 'Ext.data.Store',
    fields: ['id', 'locationName'],
	proxy : {
		type : 'ajax',
		actionMethods: {
            create : 'POST',
            read   : 'POST', // by default GET
            update : 'POST',
            destroy: 'POST'
		},
		url : Global_Path+'location/getAllLocationList',
		reader : {
			type : 'json',
			root : 'result'
		}
	}
});
var indexResourceListStore=new Ext.data.Store({
    fields: ['id', 'indexName'],
	proxy : {
		type : 'ajax',
		actionMethods: {
            create : 'POST',
            read   : 'POST', // by default GET
            update : 'POST',
            destroy: 'POST'
		},
		url : Global_Path+'indexresource/getIndexResourceListNotInThisUser',
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
		url : Global_Path+'resourceGroup/getResourceGroupListOfNowUserAndLocation',
		reader : {
			type : 'json',
			root : 'result'
		}
	},
	autoLoad:false
});
Ext.define('Bjfu.userIndexResource.view.AddIndexResourceForUser',{
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
    	            	Ext.getCmp('indexResource').clearValue();
    	            	Ext.getCmp('ResourceGroup').clearValue();
    	            	
    	            }
    	    
    	        }
    	    },{
    	    	id : 'location',
    	    	xtype : 'combo',
    	        fieldLabel : '所属区域<font color="red">*</font>',
    	        name : 'locationId',
    	        store : Ext.create('locationList'),
    	        allowBlank : false,
    	        editable : false,
    	        displayField : 'locationName',
    	        valueField : 'id',
    	        emptyText : '请选择...',	
    	        listeners:{
    	            select : function(combo, record, index) {
    	            	Ext.getCmp('indexResource').clearValue();
    	            	Ext.getCmp('ResourceGroup').clearValue();
    	            	var locationId=combo.getValue();
    	                resourceGroupListStore.load({
    	               		params: {
    	               			locationId:locationId
    	           			}
    	            });
    	            }
    	        }		
    	        },{
    	    	id : 'ResourceGroup',
    	    	xtype : 'combo',
    	        fieldLabel : '资源组<font color="red">*</font>',
    	        name : 'resourceGroupId',
    	        store : resourceGroupListStore,
    	        allowBlank : false,
    	        editable : false,
    	        displayField : 'groupName',
    	        valueField : 'id',
    	        emptyText : '请选择...'	,
    	        queryMode:'local',
    	        listeners : { //监听该下拉列表的选择事件
    	            select : function(combo, record, index) {
    	            	Ext.getCmp('indexResource').clearValue();
    	            	var userId=Ext.getCmp('user').getValue();
    	            	var resourceGroupId=combo.getValue();
    	            	indexResourceListStore.load({
    	               		params: {
    	               			userId: userId,
    	               			resourceGroupId:resourceGroupId
    	           			}
    	            });
    	            }
    	    
    	        }
    	    },{
    	    	id : 'indexResource',
    	    	xtype : 'combo',
    	        fieldLabel : '指标资源<font color="red">*</font>',
    	        name : 'indexResourceId',
    	        store : indexResourceListStore,
    	        allowBlank : false,
    	        editable : false,
    	        displayField : 'indexName',
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
		    	 	   			url:Global_Path+'indexresource/addIndexResourceForUser',
		    	 	   			method:'post',
		    	 	   			params:{
		    	 	   					formData:Ext.encode(UserGroupValues)
		    	 	   			},
		    	 	   		success: function(response) {
		                    	var	result =  Ext.decode(response.responseText);
		                    	if(result.success){
		                    		Ext.Msg.alert('提示','为用户组添加资源组成功');
		    						window.close();
		    	 	   			Ext.getCmp('indexResourceListViewId').store.reload();
		    	 	   			Ext.getCmp('indexResourceListViewId').store.loadRawData();
		    	 	   			
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

