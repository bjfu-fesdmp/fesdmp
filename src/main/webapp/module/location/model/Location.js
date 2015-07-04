Ext.define('Bjfu.location.model.Location',{
	extend : 'Ext.data.Model',
	fields : [{
    	name : 'id',   					
    	type : 'int' //日志编号
    },{
    	name : 'locationName',   					
    	type : 'string' //区域名称
    },{
    	name : 'memo',   					
    	type : 'string' //注释
    }
	]
});
