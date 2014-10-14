Ext.define('Bjfu.dataDisplay.view.FileUpload',{
		extend:'Ext.form.Panel',
        bodyPadding: 10,  
    	border:false,
        items: [{  
            xtype: 'filefield',  
            name: 'file',  
            fieldLabel: 'File',  
            labelWidth: 50,  
            msgTarget: 'side',  
            allowBlank: false,
            anchor: '100%',  
            buttonText: 'Select a File...'  
        }],  
        buttons: [{  
            text: '上传',  
            handler: function() {  
                var form = this.up('form').getForm();  
	            var window = this.up('window');
                if(form.isValid()){  
                    form.submit({  
                        url: Global_Path+'dataDisplay/uploadFile',  
                        waitMsg: 'Uploading your file...',  
                        success: function(fp, o) {  
                            Ext.Msg.alert('提示', '文件上传成功');  
                            window.close();
                            Ext.getCmp('dataDisplayId').store.reload();
                        },  
                    	failure: function(form, action) {
                    		Ext.Msg.alert('提示','文件上传失败');
                    		window.close();
                    	}
                    });  
                }  
            }  
        }]  
    });  
//http://www.open-open.com/home/space-1-do-blog-id-4438.html