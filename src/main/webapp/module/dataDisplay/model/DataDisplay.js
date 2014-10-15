Ext.define('Bjfu.dataDisplay.model.DataDisplay',{
	extend : 'Ext.data.Model',
	fields : [{
    	name : 'id',   					
    	type : 'int' //数据编号
    },{
    	name : 'time',   					
    	type : 'string' //事件
    },{
    	name : 'data',   					
    	type : 'string' //值
    },{
    	name : 'unit',   					
    	type : 'string' //单位
    }]
});
