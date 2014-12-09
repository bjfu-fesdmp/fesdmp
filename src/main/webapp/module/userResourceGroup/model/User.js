Ext.define('Bjfu.userResourceGroup.model.User',{
	extend : 'Ext.data.Model',
	fields : [{
    	name : 'id',   					
    	type : 'int' //用户编号
    },{
    	name : 'userName', 
    	type : 'string' //用户名
	},{
    	name : 'email',   					
    	type : 'string' //邮箱
    },{
		name : 'userPhone', 
    	type : 'string' //电话
	}
	]
});
