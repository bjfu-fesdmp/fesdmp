Ext.define('Bjfu.userGroup.model.UserGroup',{
	extend : 'Ext.data.Model',
	fields : [{
    	name : 'id',   					
    	type : 'int' //用户组编号
    },{
    	name : 'userGroupName', 
    	type : 'string' //用户组编号
	},{
    	name : 'createTime',   					
    	type : 'string' //创建时间
    },{
		name : 'createrId', 
    	type : 'int' //创建者id
	},{
		name : 'roleName', 
    	type : 'string' //所属角色
	},{
    	name : 'role',   					
    	type : 'int' //角色id
    }
	]
});
