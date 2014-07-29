Ext.define('Bjfu.log.model.Index',{
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
    	name : 'indexMemo',   					
    	type : 'string' //指标备忘
    },{
    	name : 'creater_id',
    	type : 'int' //创建人
    },{
    	name : 'createTime', 
    	type : 'string' //创建时间
	},{
		name : 'modifier_id', 
    	type : 'string' //修改人
	},{
		name : 'modifyTime', 
    	type : 'string' //修改时间
	}
	]
});