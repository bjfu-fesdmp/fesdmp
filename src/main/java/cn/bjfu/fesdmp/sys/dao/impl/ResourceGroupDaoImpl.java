package cn.bjfu.fesdmp.sys.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.bjfu.fesdmp.domain.sys.ResourceGroup;
import cn.bjfu.fesdmp.domain.sys.ResourceRelation;
import cn.bjfu.fesdmp.frame.dao.IOrder;
import cn.bjfu.fesdmp.frame.dao.Order;
import cn.bjfu.fesdmp.sys.dao.IResourceGroupDao;
import cn.bjfu.fesdmp.sys.dao.IUserDao;

@Repository
public class ResourceGroupDaoImpl extends AbstractGenericDao<ResourceGroup> implements IResourceGroupDao{

	private static final Logger logger = Logger.getLogger(ResourceGroupDaoImpl.class);
	@Autowired
	private IUserDao userDao;
	public ResourceGroupDaoImpl() {
		super(ResourceGroup.class);
	}

	
	
	
	@Override
	public List<ResourceGroup> findResourceGroupByParentIdAndUserId(int parentId,int userId){
		String jpal =""; 
		if(this.userDao.findByKey(userId).getIsAdmin().equals((byte)1))
			jpal = " SELECT p FROM ResourceGroup p where p.groupParentId="+parentId;
		else
			jpal = " SELECT p FROM ResourceGroup p,UserResourceGroupRelation n where p.id=n.resourceGroup.id and p.groupParentId="+parentId+" and n.user.id="+userId;
		logger.info(jpal);
		Query query = super.getEntityManager().createQuery(jpal);
		List<ResourceGroup> resourceGroupList=query.getResultList();
		return resourceGroupList;

	}
	@Override
	public List<ResourceGroup> findResourceGroupByUserId(String userId){
		String jpal = " SELECT p FROM ResourceGroup p,UserResourceGroupRelation m where p.id=m.resourceGroup.id and m.user.id="+userId;
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
	public List<ResourceGroup> findResourceGroupNotInThisUser(String userId){
		String jpal = " SELECT p FROM ResourceGroup p,UserResourceGroupRelation m where p.id=m.resourceGroup.id and m.user.id="+userId;
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
	@Override
	public boolean checkResourceGroupName(String resourceGroupName){
		String jpal = " SELECT p FROM ResourceGroup p where p.groupName='"+resourceGroupName+"'";
		logger.info(jpal);
		Query query = super.getEntityManager().createQuery(jpal);
		List list=query.getResultList();
		if(list.isEmpty())
			return false;
		else
			return true;
		
	}
	@Override
	public String findResourceGroupNameByIndexResourceId(int indexResourceId){
		String jpal = " SELECT p FROM ResourceGroup p,ResourceRelation m where p.id=m.resourceGroup.id and m.indexResource.id="+indexResourceId;
		logger.info(jpal);
		Query query = super.getEntityManager().createQuery(jpal);
		List<ResourceGroup> list=query.getResultList();
		if(list.isEmpty())
			return null;
		else
			return list.get(0).getGroupName();
		
	}
}