Ext.define('Bjfu.location.view.ModifyLocation',{
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
    	        fieldLabel: '区域名称<font color="red">*</font>',//验重
    	        name: 'locationName',
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
									url : Global_Path+'location/checkLocationName',
									params : {
										locationName : vv
									},
									success : function(response) {
										var result = Ext.decode(response.responseText);
										if(!result.success){
												Ext.Msg.alert("提示", "该区域名已经存在");
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
    	        fieldLabel: '注释', 
    	        name: 'memo'
    	    },{
    	    	fieldLabel:'区域编号',
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
		            	var locationValues = form.getValues();
		            		Ext.Ajax.request({
		    	 	   			url:Global_Path+'location/modifyLocation',
		    	 	   			method:'post',
		    	 	   			params:{
		    	 	   					formData:Ext.encode(locationValues)
		    	 	   			},
		    	 	   		success: function(response) {
		                    	var	result =  Ext.decode(response.responseText);
		                    	if(result.success){
		                    		Ext.Msg.alert('提示','修改区域成功');
		    						window.close();
		    	 	   			Ext.getCmp('locationListViewId').store.reload();
		    	 	   			Ext.getCmp('locationListViewId').store.loadRawData();
		                    	}else{
		                    		Ext.Msg.alert('修改','修改区域失败');
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

