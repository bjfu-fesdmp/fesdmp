Ext.define('Bjfu.unionDataClustering.model.TableDisplay',{
	extend : 'Ext.data.Model',
	fields : [{
    	name : 'id',   					
    	type : 'int' //编号
    },{
    	name : 'text',   					
    	type : 'string' //表名
    },{
    	name : 'parentId',   					
    	type : 'int' //父节点编号名
    },{
    	name : 'leaf',   					
    	type : 'boolean' //叶子
    }]
});
