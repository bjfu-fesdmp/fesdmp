Ext.define('Bjfu.log.model.Log',{
	extend : 'Ext.data.Model',
	fields : [{
    	name : 'id',   					
    	type : 'int' //用户编号
    },{
    	name : 'createTime',   					
    	type : 'string' //创建时间
    },{
    	name : 'email',   					
    	type : 'string' //邮箱
    },{
    	name : 'isAdmin',   					
    	type : 'int' //是否是超级管理员
    },{
    	name : 'password',   					
    	type : 'string' //密码
    },{
    	name : 'userLoginName',
    	type : 'string' //用户登录名	
    },{
    	name : 'userName', 
    	type : 'string' //用户名
	},{
		name : 'userPhone', 
    	type : 'string' //电话
	},{
		name : 'userStatus', 
    	type : 'int' //用户状态
	},{
		name : 'creater_id', 
    	type : 'int' //创建者id
	}
	]
});
