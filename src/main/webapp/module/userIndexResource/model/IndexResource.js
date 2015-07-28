Ext.define('Bjfu.userIndexResource.model.IndexResource',{
	extend : 'Ext.data.Model',
	fields : [{
    	name : 'id',   					
    	type : 'int' //指标编号
    },{
    	name : 'indexName',   					
    	type : 'string' //指标中文名
    },{
    	name : 'indexEnName',   					
    	type : 'string' //指标英文名
    },{
    	name : 'indexUnit',   					
    	type : 'string' //指标单位
    },{
		name : 'resourceGroupName', 
    	type : 'string' //所属资源组
	},{
		name : 'locationName', 
    	type : 'string' //所属区域
	}
	]
});
