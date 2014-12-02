
  
package cn.bjfu.fesdmp.sys.dao;  

import java.util.List;

import cn.bjfu.fesdmp.domain.sys.ResourceTable;


public interface IResourceTableDao extends IGenericDao<ResourceTable> {
	
	public abstract ResourceTable findTableByIndexEnNameAndYear(String indexEnName,String year);

}
 