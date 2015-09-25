/** 
 * Project Name:fesdmp 
 * File Name:SystemLogService.java 
 * Package Name:cn.bjfu.fesdmp.sys.service.impl 
 * Date:2014年7月9日 上午10:29:49 
 * Copyright (c) 2014, zhangzhaoyu0524@163.com All Rights Reserved. 
 * 
*/  
  
package cn.bjfu.fesdmp.sys.service.impl;  

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bjfu.fesdmp.frame.dao.IOrder;
import cn.bjfu.fesdmp.frame.dao.JoinMode;
import cn.bjfu.fesdmp.json.DataJson;
import cn.bjfu.fesdmp.json.HierarchicalClusteringJson;
import cn.bjfu.fesdmp.json.TableJson;
import cn.bjfu.fesdmp.sys.dao.IDataDao;
import cn.bjfu.fesdmp.sys.dao.IResourceTableDao;
import cn.bjfu.fesdmp.sys.service.IDataService;
import cn.bjfu.fesdmp.utils.Pagination;
import cn.bjfu.fesdmp.web.jsonbean.DataSearch;

@Service
@Transactional
public class DataService implements IDataService {

	@Autowired
	private IDataDao dataDao;
	@Autowired
	private IResourceTableDao resourceTableDao;
	@Override
	public void addData(String table,List<DataJson> list) {
		this.dataDao.dataInsert(table,list);
	}

	@Override
	public void deleteData(String tableName, String[] id) {
		for(int i=0;i<id.length;i++)
		this.dataDao.deleteDataById(tableName,id[i]);
	}
	@Override
	public void deleteTable(String tableName){
		this.resourceTableDao.delete(this.resourceTableDao.findTableByIndexEnNameAndYear(tableName.substring(5),tableName.substring(0,4)));
		this.dataDao.deleteTable(tableName);
	}
	@Override
	public void modifyData(DataJson data,String tableName) {
		this.dataDao.modifyData(data,tableName);
	}
	
	@Override
	public DataJson findDataById(String id,String tableName) {
		return this.dataDao.findDataById(id,tableName);
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<DataJson> queryAll(IOrder order) {
		return this.dataDao.findAll(order);
	}
	
	@Transactional(readOnly = true)
	@Override
	public void queryByCondition(Object condition, IOrder order,
			Pagination<DataJson> page) {
		this.dataDao.findByCondition(condition, order, page);
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<DataJson> queryByCondition(Object condition, IOrder order,
			Pagination<DataJson> page, JoinMode joinMode) {
		return this.dataDao.findByCondition(condition, order, page, joinMode);
	}

	@Override
	public List<DataJson> queryByCondtinWithOperationTime(String tableName,DataSearch condition,
			IOrder order, Pagination<DataJson> page, JoinMode joinMode) {
		return this.dataDao.findByCondtinWithOperationTime(tableName,condition, order, page, joinMode);
	}
	@Override
	public List<DataJson> queryUnionByCondtinWithOperationTime(String tableName,DataSearch condition,
			IOrder order, Pagination<DataJson> page, JoinMode joinMode) {
		return this.dataDao.findUnionByCondtinWithOperationTime(tableName,condition, order, page, joinMode);
	}
	@Override
	public List<TableJson> findTable(){
		return this.dataDao.findTable();
	};
	@Override
	public TableJson findTableByYearAndIndexResource(String year,int id){
		return this.dataDao.findTableByYearAndIndexResource(year,id);
	};
	@Override
	public boolean checkIfHasTable(String tableName){
		return this.dataDao.checkIfHasTable(tableName);
	};
	@Override
	public DataJson[] timeCoordination(HierarchicalClusteringJson hierarchicalClusteringJson,String table){
		DataJson[] standard=this.dataDao.findData(hierarchicalClusteringJson.getHierarchicalClusteringCenterId(), hierarchicalClusteringJson.getStartTime(), hierarchicalClusteringJson.getEndTime());
		DataJson[] modify=this.dataDao.findData(table, hierarchicalClusteringJson.getStartTime(), hierarchicalClusteringJson.getEndTime());
		DataJson[] newModify=standard;
		
		
		
		int start=0;
		int pointerS=0;
		int pointerM=0;

		//防止存在modify第一个的时间要比standard前几个都晚
		while(standard[pointerS].getTime().getTime()<modify[pointerM].getTime().getTime()){
			start++;
			pointerS++;
		}
		
		
//		if(pointerS>0){
//			for(int i=0;i<pointerS;i++)
//			{
//				newModify[i].setData(modify[0].getData());
//			}
//		}
		
		while(pointerS!=standard.length&&pointerM!=modify.length){
			if(standard[pointerS].getTime().getTime()==modify[pointerM].getTime().getTime()){
				newModify[pointerS].setData(modify[pointerM].getData());
				pointerS++;
				pointerM++;
			}
			else if(standard[pointerS].getTime().getTime()>modify[pointerM].getTime().getTime()){
				for(int i=pointerM;i<modify.length;i++){
					if((standard[pointerS].getTime().getTime()>modify[pointerM].getTime().getTime()))
					{
						pointerM++;
					}
					else
						break;
				}
				if(standard[pointerS].getTime().getTime()==modify[pointerM].getTime().getTime()){
					newModify[pointerS].setData(modify[pointerM].getData());
					pointerS++;
					pointerM++;
				}
				else{
					Double a=Double.parseDouble(modify[pointerM].getData());
					Double b=Double.parseDouble(modify[pointerM-1].getData());
					Long timeA=modify[pointerM].getTime().getTime();
					Long timeB=modify[pointerM-1].getTime().getTime();
					Long timeC=standard[pointerS].getTime().getTime();
					Double res=((a*(timeA-timeC)+b*(timeC-timeB))/(timeA-timeB));
					newModify[pointerS].setData(String.format("%.2f",res));
					pointerS++;
				}
			}
			else{
				for(int i=pointerM;i>0;i--){
					if((standard[pointerS].getTime().getTime()<modify[pointerM].getTime().getTime()))
					{
						pointerM--;
					}
					else
						break;
				}
				if(standard[pointerS].getTime().getTime()==modify[pointerM].getTime().getTime()){
					newModify[pointerS].setData(modify[pointerM].getData());
					pointerS++;
					pointerM++;
				}
				else{

					Double a=Double.parseDouble(modify[pointerM+1].getData());
					Double b=Double.parseDouble(modify[pointerM].getData());
					Long timeA=modify[pointerM+1].getTime().getTime();
					Long timeB=modify[pointerM].getTime().getTime();
					Long timeC=standard[pointerS].getTime().getTime();
					Double res=((a*(timeA-timeC)+b*(timeC-timeB))/(timeA-timeB));
					newModify[pointerS].setData(String.format("%.2f",res));
					pointerS++;
				}
				
			}
		}	
//		//防止存在modify最后一个的时间要比standard最后几个都早
//		if(pointerS!=standard.length){
//			for(int i=pointerS;i<standard.length;i++)
//			{
//				newModify[i].setData(modify[pointerM-1].getData());
//			}
//		}
		DataJson[] result=new DataJson[pointerS-start];
		for(int i=0;i<pointerS-start;i++)
		{
			result[i]=newModify[i+start];
		}
		
		
		
			return result;	
		
	};
	@Override
	public DataJson[] findData(HierarchicalClusteringJson hierarchicalClusteringJson){
		return this.dataDao.findData(hierarchicalClusteringJson.getHierarchicalClusteringCenterId(),hierarchicalClusteringJson.getStartTime(),hierarchicalClusteringJson.getEndTime());
	};
}

 