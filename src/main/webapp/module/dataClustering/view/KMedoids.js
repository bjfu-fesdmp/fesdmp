Ext.define('Bjfu.dataClustering.view.KMedoids',{
		extend:'Ext.form.Panel',
        bodyPadding: 10,  
    	border:false,
    	tableName:null,
    	ids:null,
    	initComponent: function() {
        	var me = this;
    		var dataStore = Ext.create('Ext.data.Store', {
    			fields : [ 'name', 'data' ],
    			proxy : {
    				type : 'ajax',
    				actionMethods : {
    					create : 'POST',
    					read : 'POST', // by default GET
    					update : 'POST',
    					destroy : 'POST'
    				},
    				url : Global_Path + 'dataClustering/kmedoids',
    				reader : {
    					type : 'json',
    					root : 'result',
    					idProperty : 'name',
    					totalProperty : 'pageCount'
    				}
    			},
    			autoLoad:false
    		});

    		var donut = false;
    		var chart = Ext.create('Ext.chart.Chart', {
    			xtype : 'chart',
    			animate : true,
    			store : dataStore,
    			shadow : true,
    			legend : {
    				position : 'right'
    			},
    			insetPadding : 60,
    			theme : 'Base:gradients',
    			series : [ {
    				type : 'pie',
    				field : 'data',
    				showInLegend : true,
    				donut : donut,
    				tips : {
    					trackMouse : true,
    					width : 300,
    					height : 100,
    					renderer : function(storeItem, item) {
    						//calculate percentage.
    						var total = parseInt(0);
    						
    						dataStore.each(function(record) {
    							total=total+parseInt(record.get('data'));
    							});  
    						this.setTitle(storeItem.get('name')
    								+ ': '
    								 + " 包含"
    								+ storeItem.get('data') + "条数据"+'<br>'+"占所有"+total+"条数据的"+ Math.round(storeItem.get('data')*100/total) + '%');
    					}
    				},
    				highlight : {
    					segment : {
    						margin : 20
    					}
    				},
    				label : {
    					field : 'name',
    					display : 'rotate',
    					contrast : true,
    					font : '18px Arial'
    				}
    			} ]
    		});
 
        	Ext.apply(me,{
        		layout: 'column',
        	    defaults: {  
        	    	labelAlign:'right',
        	        layout: 'anchor',
        	        columnWidth:.90,
        	        margin: '3 25 3 0',
        	        defaults: {
        	            anchor: '100%'
        	        }
        	    },
    		    defaultType: 'textfield',
        	    items: [{
        	        fieldLabel : '聚类数<font color="red">*</font>',
        	        name: 'number',
        	        allowBlank : false,
        	        maxLength : 2,
        	        listeners:{
    	    	        'blur' : function(_this, the, e) {
    						var v = _this.getValue();
    						var vv = Ext.String.trim(v);
    						_this.setValue(vv);			
    						
    							if (vv.length > 0) {
    								Ext.Ajax.request({
    									url : Global_Path+'dataClustering/checkIfIsNumber',
    									params : {
    										num:vv
    									},
    									success : function(response) {
    										var result = Ext.decode(response.responseText);
    										if(!result.success){
    												Ext.Msg.alert("提示", "请输入正确的数字");
    												_this.setValue('');
    												return;
    										}
    									},
    									failure: function(response) {
    										var result = Ext.decode(response.responseText);
    										Ext.Msg.alert('错误', result.__msg);
    									}
    								});
    								
    							}			    
        	        	}
        } 	       
        	    
        	    }],
            	buttonAlign:'center', 
                buttons: [{
            		        text: '开始运算',
            		        formBind: true,
            		        disabled: true,
            		        handler: function() {
            		            var form = this.up('form').getForm();
            		            var window = this.up('window');
            		            if (form.isValid()) { 
            		            	var tableValues = form.getValues();
            		            	dataStore.load({
            	    	               		params: {
                   		    	 	   			tableName:me.tableName,
                		    	 	   			ids:me.ids,
                		    	 	   			num:Ext.encode(tableValues)
            	    	           			}
            	    	            });
            		            	this.up('window').close();
            		            	
            		            	 var win = Ext.create('Ext.Window', {
            		                     width: 800,
            		                     height: 600,
            		                     minHeight: 400,
            		                     minWidth: 550,
            		                     hidden: false,
            		                     maximizable: true,
            		                     title: '聚类结果图',
            		                     autoShow: true,
            		                     layout: 'fit',
            		                     tbar: [{
            		                         text: '下载图表',
            		                         handler: function() {
            		                             Ext.MessageBox.confirm('下载提示', '是否下载当前图表?', function(choice){
            		                                 if(choice == 'yes'){
            		                                     chart.save({
            		                                         type: 'image/png'
            		                                     });
            		                                 }
            		                             });
            		                         }
            		                     }, {
            		         				enableToggle : true,
            		        				pressed : false,
            		        				text : 'Donut',
            		        				toggleHandler : function(btn, pressed) {
            		        					chart.series.first().donut = pressed ? 35 : false;
            		        					chart.refresh();
            		        				}
            		        			} ],
            		                     items: chart
            		                 });
            		            	 dataStore.removeAll();
                                }
            		        }
            		    }]
        	});
        	me.callParent(arguments);
    	},

    });
