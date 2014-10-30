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


Ext.define('Bjfu.indexResource.view.AddIndexResource',{
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
    	        fieldLabel : '中文名称<font color="red">*</font>',//验重
    	        name: 'indexName',
    	        allowBlank : false,
    	        maxLength : 50,
				maxLengthText : '长度不能超过50个字符'    	        
    	    },{
    	    	fieldLabel : '英文名称<font color="red">*</font>',
    	    	name :'indexEnName',
    	    	allowBlank : false,
    	    	maxLength : 50,
 				maxLengthText : '长度不能超过50个字符' 
    	    },{
    	    	fieldLabel : '指标单位<font color="red">*</font>',
    	    	name : 'indexUnit',
    	    	allowBlank : false,
    	    	maxLength : 50,
 				maxLengthText : '长度不能超过50个字符' 
    	    },{
    	    	id : 'userGroup',
    	    	xtype : 'combo',
    	        fieldLabel : '所属资源组<font color="red">*</font>',
    	        name : 'resourceGroupId',
    	        store : Ext.create('resourceGroupList'),
    	        allowBlank : false,
    	        editable : false,
    	        displayField : 'groupName',
    	        valueField : 'id',
    	        emptyText : '请选择...'	
    	    },{
    	    	fieldLabel : '描述',
    	    	name: 'indexMemo',
    	    	allowBlank : true
    	    },{
    	    	fieldLabel:'指标编号',
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
		            	var userGroupValues = form.getValues();
		            		Ext.Ajax.request({
		    	 	   			url:Global_Path+'indexresource/addIndexResource',
		    	 	   			method:'post',
		    	 	   			params:{
		    	 	   					formData:Ext.encode(userGroupValues)
		    	 	   			},
		    	 	   		success: function(response) {
		                    	var	result =  Ext.decode(response.responseText);
		                    	if(result.success){
		                    		Ext.Msg.alert('提示','添加指标成功');
		    						window.close();
		    	 	   			Ext.getCmp('indexResourceListViewId').store.reload();
		                    	}else{
		                    		Ext.Msg.alert('提示','添加指标失败');
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

