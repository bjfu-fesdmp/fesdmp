Ext.define('Bjfu.indexResource.model.Role',{
	extend : 'Ext.data.Model',
	fields : [{
    	name : 'id',   					
    	type : 'int' //角色编号
    },{
    	name : 'roleName',   					
    	type : 'string' //角色名
    },{
    	name : 'roleDiscription',   					
    	type : 'string' //角色描述
    },{
    	name : 'creater_id',
    	type : 'int' //创建人
    },{
    	name : 'createTime', 
    	type : 'string' //创建时间
	}
	]
});
