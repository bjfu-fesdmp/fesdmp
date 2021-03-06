Ext.define('Bjfu.resourceGroup.model.ResourceGroup',{
	extend : 'Ext.data.Model',
	fields : [{
    	name : 'id',   					
    	type : 'int' //资源组编号
    },{
    	name : 'groupParentId', 
    	type : 'int' //父资源组编号
	},{
    	name : 'groupName',   					
    	type : 'string' //资源组名称
    },{
		name : 'memo', 
    	type : 'string' //备注
	},{
    	name : 'leaf',   					
    	type : 'boolean' //叶子
    }
	]
});
