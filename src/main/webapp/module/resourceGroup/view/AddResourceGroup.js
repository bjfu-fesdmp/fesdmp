Ext.define('resourceGroupList', {
    extend: 'Ext.data.Store',
    fields: ['id', 'groupName'],
	proxy : {
		type : 'ajax',
		actionMethods: {
            create : 'POST',
            read   : 'POST', // by default GET
            update : 'POST',
            destroy: 'POST'
		},
		url : Global_Path+'resourceGroup/getAllResourceGroupList',
		reader : {
			type : 'json',
			root : 'result'
		}
	}
});




Ext.define('Bjfu.resourceGroup.view.AddResourceGroup',{
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
    	        fieldLabel: '资源组名称<font color="red">*</font>',//验重
    	        name: 'groupName',
    	        allowBlank : false,
    	        maxLength : 50,
				maxLengthText : '长度不能超过50个字符'	        
    	    },{
    	    	fieldLabel:'资源组编号',
    	    	name:'id',
    	    	xtype:'hiddenfield'
    	    },{
    	    	id : 'userGroup',
    	    	xtype : 'combo',
    	        fieldLabel : '父资源组',
    	        name : 'groupParentId',
    	        store : Ext.create('resourceGroupList'),
    	        editable : false,
    	        displayField : 'groupName',
    	        valueField : 'id',
    	        emptyText : '请选择...'	
    	    },{
    	    	fieldLabel:'注释',
    	    	name:'memo',
       	        maxLength : 50,
				maxLengthText : '长度不能超过50个字符', 
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
		            	var resourceGroupValues = form.getValues();
		            		Ext.Ajax.request({
		    	 	   			url:Global_Path+'resourceGroup/addResourceGroup',
		    	 	   			method:'post',
		    	 	   			params:{
		    	 	   					formData:Ext.encode(resourceGroupValues)
		    	 	   			},
		    	 	   		success: function(response) {
		                    	var	result =  Ext.decode(response.responseText);
		                    	if(result.success){
		                    		Ext.Msg.alert('提示','添加资源组成功');
		    						window.close();
		    	 	   			Ext.getCmp('resourceGroupViewId').store.reload();
		                    	}else{
		                    		Ext.Msg.alert('提示','添加资源组失败');
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

