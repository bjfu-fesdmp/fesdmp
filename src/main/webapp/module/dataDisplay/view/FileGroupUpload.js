Ext.define('upLoadForm', {
    extend: 'Ext.form.field.File',

    width: 100,
    height: 30,
    buttonOnly: true ,
    buttonText: '选择文件',
    renderTo: document.body,
    supportMultFn: function($this){
        //2.1 为input添加支持多文件选择属性
        var typeArray = ["txt/xml"];
        var fileDom = $this.getEl().down('input[type=file]');
        fileDom.dom.setAttribute("multiple","multiple");
        fileDom.dom.setAttribute("accept",typeArray.join(","));
    },
    listeners: {
    	afterrender: function(){
        //2.2 渲染后重写
        this.supportMultFn(this);
    },
    	change: function(){
       //2.3 获取文件列表
        var fileDom = this.getEl().down('input[type=file]');
        var files = fileDom.dom.files;
        var fileArr = [];
        for(var i = 0; i<files.length; i++){
              fileArr.push((i+1)+"、文件名："+files[i].name+",类型:"+files[i].type+",大小:"+files[i].size/1024+"KB");
        }
        me.fileArr=fileArr;
         //files[0].name / files[0].type / files[0].size 
        this.supportMultFn(this);
    	}
    },

});

Ext.define('Bjfu.dataDisplay.view.FileGroupUpload',{
	extend:'Ext.form.Panel',
    bodyPadding: 10,  
	border:false,
	files:null,
	tableName:null,
	initComponent: function() {
    	var me = this;
    	Ext.apply(me, {
    		items: [{
                xtype: 'filefield',  
                fieldLabel: 'File',  
                labelWidth: 50,  
                msgTarget: 'side',  
                allowBlank: false,
                name: 'file',  
                anchor: '100%',  
                width: 100,
                height: 30,
                buttonText: '选择文件',
                renderTo: document.body,
                supportMultFn: function($this){
                    //2.1 为input添加支持多文件选择属性
                    var typeArray = ["txt/xml"];
                    var fileDom = $this.getEl().down('input[type=file]');
                    fileDom.dom.setAttribute("multiple","multiple");
                    fileDom.dom.setAttribute("accept",typeArray.join(","));
                },
                listeners: {
                	afterrender: function(){
                    //2.2 渲染后重写
                    this.supportMultFn(this);
                },
                	change: function(){
                   //2.3 获取文件列表
                    var fileDom = this.getEl().down('input[type=file]');
                    var files = fileDom.dom.files;
                    var fileArr = [];
                    for(var i = 0; i<files.length; i++){
                          fileArr.push((i+1)+"、文件名："+files[i].name+",类型:"+files[i].type+",大小:"+files[i].size/1024+"KB");
                    }
                    me.files=files;
                     //files[0].name / files[0].type / files[0].size 
                    this.supportMultFn(this);
                	}
                },
            }], 
            buttons: [{  
                text: '上传',  
                handler: function() {
                    var form = this.up('form').getForm();  
    	            var window = this.up('window');
                    if(form.isValid()){  
                        form.submit({  
                            url: Global_Path+'dataDisplay/uploadGroupFile', 
                            params:{tableName:me.tableName},
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
    	})
		
		
    	me.callParent(arguments);
	},
    
});
