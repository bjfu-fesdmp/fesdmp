package cn.bjfu.fesdmp.sys.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import cn.bjfu.fesdmp.domain.sys.ResourceGroup;
import cn.bjfu.fesdmp.domain.sys.ResourceRelation;
import cn.bjfu.fesdmp.domain.sys.Role;
import cn.bjfu.fesdmp.domain.sys.UserUserGroupRelation;
import cn.bjfu.fesdmp.frame.dao.IOrder;
import cn.bjfu.fesdmp.frame.dao.Order;
import cn.bjfu.fesdmp.sys.dao.IResourceGroupDao;

@Repository
public class ResourceGroupDaoImpl extends AbstractGenericDao<ResourceGroup> implements IResourceGroupDao{

	private static final Logger logger = Logger.getLogger(ResourceGroupDaoImpl.class);
	
	public ResourceGroupDaoImpl() {
		super(ResourceGroup.class);
	}

	
	
	
	@Override
	public List<ResourceGroup> findResourceGroupById(int parentId){
		String jpal = " SELECT p FROM ResourceGroup p where p.groupParentId="+parentId;
		logger.info(jpal);
		Query query = super.getEntityManager().createQuery(jpal);
		List<ResourceGroup> resourceGroupList=query.getResultList();
		return resourceGroupList;

	}
	@Override
	public List<ResourceGroup> findResourceGroupByUserGroupId(String userGroupId){
		String jpal = " SELECT p FROM ResourceGroup p,UserGroupResourceGroupRelation m where p.id=m.resourceGroup.id and m.userGroup.id="+userGroupId;
		logger.info(jpal);
		Query query = super.getEntityManager().createQuery(jpal);
		List<ResourceGroup> resourceGroupList=query.getResultList();
		return resourceGroupList;

	}
	
	
	
	@Override
	public boolean ifHaveChild(int id){
		String jpal = " SELECT p FROM ResourceGroup p where p.groupParentId="+id;
		logger.info(jpal);
		Query query = super.getEntityManager().createQuery(jpal);
		List<ResourceGroup> resourceGroupList=query.getResultList();
		if(resourceGroupList.isEmpty())
			return false;
		else
			return true;
	}
	@Override
	public boolean checkIfHaveIndexResource(int id){
		String jpal = " SELECT p FROM ResourceRelation p where p.resourceGroup.id="+id;
		logger.info(jpal);
		Query query = super.getEntityManager().createQuery(jpal);
		List<ResourceRelation> resourceRelationList=query.getResultList();
		if(resourceRelationList.isEmpty())
			return false;
		else
			return true;
	}
	@Override
	public List<ResourceGroup> findResourceGroupNotInThisUserGroup(String userGroupId){
		String jpal = " SELECT p FROM ResourceGroup p,UserGroupResourceGroupRelation m where p.id=m.resourceGroup.id and m.userGroup.id="+userGroupId;
		logger.info(jpal);
		Query query = super.getEntityManager().createQuery(jpal);
		List<ResourceGroup> list1=query.getResultList();
		IOrder order = new Order();
		order.addOrderBy("id", "DESC");
		List<ResourceGroup> list2=this.findAll(order);
		List<ResourceGroup> list=new ArrayList();
		for(int i=0;i<list2.size();i++){
			boolean check=false;
			for(int j=0;j<list1.size();j++){
				if(list1.get(j).getId()==list2.get(i).getId())
					check=true;
			}
			if(check==false)
				list.add(list2.get(i));
		}
		
		
		return list;	
	}
}

