Ext.define('Bjfu.indexResource.view.ModifyIndexResource',{
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
    	    	fieldLabel : '英文名称',
    	    	name :'indexEnName',
    	    	allowBlank : false,
    	    	maxLength : 50,
 				maxLengthText : '长度不能超过50个字符' 
    	    },{
    	    	fieldLabel : '指标单位',
    	    	name : 'indexUnit',
    	    	allowBlank : false,
    	    	maxLength : 50,
 				maxLengthText : '长度不能超过50个字符' 
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
		            	var indexResourceValues = form.getValues();
		            		Ext.Ajax.request({
		    	 	   			url:Global_Path+'indexresource/modifyIndexResource',
		    	 	   			method:'post',
		    	 	   			params:{
		    	 	   					formData:Ext.encode(indexResourceValues)
		    	 	   			},
		    	 	   		success: function(response) {
		                    	var	result =  Ext.decode(response.responseText);
		                    	if(result.success){
		                    		Ext.Msg.alert('提示','修改指标成功');
		    						window.close();
		    	 	   			Ext.getCmp('userGroupViewId').store.reload();
		    	 	   			Ext.getCmp('userGroupViewId').store.loadRawData();
		                    	}else{
		                    		Ext.Msg.alert('提示','修改指标失败');
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

