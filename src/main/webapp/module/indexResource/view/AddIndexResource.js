var resourceGroupStore=new Ext.data.Store({
    fields: ['id', 'groupName'],
	proxy : {
		type : 'ajax',
		actionMethods: {
            create : 'POST',
            read   : 'POST', // by default GET
            update : 'POST',
            destroy: 'POST'
		},
		url : Global_Path+'resourceGroup/getResourceGroupInThisLocation',
		reader : {
			type : 'json',
			root : 'result'
		}
	},
	autoLoad:false
});

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
    	    items: [
    	            
   {
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
    	            	Ext.getCmp('indexResource').setValue("");
    	            	Ext.getCmp('resourceGroup').clearValue();
    	            	var locationId=combo.getValue();
    	            	resourceGroupStore.load({
    	               		params: {
    	               			locationId: locationId,
    	           			}
    	            });
    	            }
    	        }		
    	        },
    	       
    	        {
    	    	id : 'resourceGroup',
    	    	xtype : 'combo',
    	        fieldLabel : '所属资源组<font color="red">*</font>',
    	        name : 'resourceGroupId',
    	        store : resourceGroupStore,
    	        allowBlank : false,
    	        editable : false,
    	        displayField : 'groupName',
    	        valueField : 'id',
    	        emptyText : '请选择...'	,
    	        queryMode:'local',
    	        listeners:{
    	            select : function(combo, record, index) {
    	            	Ext.getCmp('indexResource').setValue("");
    	            }
    	        }	
    	    },{
    	    	id : 'indexResource',
    	        fieldLabel : '中文名称<font color="red">*</font>',//验重
    	        name: 'indexName',
    	        allowBlank : false,
    	        maxLength : 50,
				maxLengthText : '长度不能超过50个字符' ,    	        
    	        listeners:{
	    	        'blur' : function(_this, the, e) {
						var v = _this.getValue();
						var resourceGroupId=Ext.getCmp('resourceGroup').getValue();
						var vv = Ext.String.trim(v);
						_this.setValue(vv);			
							if (vv.length > 0) {
								Ext.Ajax.request({
									url : Global_Path+'indexresource/checkIndexResourceName',
									params : {
										indexResourceName : vv,
										resourceGroupId:resourceGroupId
									},
									success : function(response) {
										var result = Ext.decode(response.responseText);
										if(!result.success){
												Ext.Msg.alert("提示", "该指标资源名已经存在");
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
    	    	fieldLabel : '英文名称<font color="red">*</font>',
    	    	name :'indexEnName',
    	    	allowBlank : false,
    	    	maxLength : 50,
 				maxLengthText : '长度不能超过50个字符',    	        
    	        listeners:{
	    	        'blur' : function(_this, the, e) {
						var v = _this.getValue();
						var resourceGroupId=Ext.getCmp('resourceGroup').getValue();
						var vv = Ext.String.trim(v);
						_this.setValue(vv);			
							if (vv.length > 0) {
								Ext.Ajax.request({
									url : Global_Path+'indexresource/checkIndexResourceEnName',
									params : {
										indexResourceEnName : vv,
										resourceGroupId:resourceGroupId
									},
									success : function(response) {
										var result = Ext.decode(response.responseText);
										if(!result.success){
												Ext.Msg.alert("提示", "该指标资源名已经存在");
												_this.setValue('');
												return;
										}
									},
									failure: function(response) {
										var result = Ext.decode(response.responseText);
										Ext.Msg.alert('错误', result.__msg);
									}
								});
								Ext.Ajax.request({
									url : Global_Path+'indexresource/checkSpacee',
									params : {
										indexResourceEnName : vv
									},
									success : function(response) {
										var result = Ext.decode(response.responseText);
										if(result.success){
												Ext.Msg.alert("提示", "指标英文名不能有空格，请用“_”代替");
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
    	    	fieldLabel : '指标单位<font color="red">*</font>',
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
		    	 	   			url:Global_Path+'indexresource/addIndexResource',
		    	 	   			method:'post',
		    	 	   			params:{
		    	 	   					formData:Ext.encode(indexResourceValues)
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

