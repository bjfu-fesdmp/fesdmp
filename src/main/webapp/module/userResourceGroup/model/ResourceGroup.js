Ext.define('Bjfu.userResourceGroup.model.ResourceGroup',{
	extend : 'Ext.data.Model',
	fields : [{
    	name : 'id',   					
    	type : 'int' //资源组id
    },{
    	name : 'location', 
    	type : 'string' //资源组所在区域
	},{
    	name : 'groupName',   					
    	type : 'string' //资源组名
    },{
		name : 'memo', 
    	type : 'string' //资源组描述
	}
	]
});
