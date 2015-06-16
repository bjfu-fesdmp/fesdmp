  
package cn.bjfu.fesdmp.sys.dao.impl;  

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map; 
import java.sql.DatabaseMetaData;  
import java.sql.SQLException;  
import java.text.SimpleDateFormat;
import java.util.Properties;  















import javax.xml.crypto.Data;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import cn.bjfu.fesdmp.frame.dao.IOrder;
import cn.bjfu.fesdmp.frame.dao.JoinMode;
import cn.bjfu.fesdmp.json.DataJson;
import cn.bjfu.fesdmp.json.TableJson;
import cn.bjfu.fesdmp.sys.dao.IDataDao;
import cn.bjfu.fesdmp.sys.dao.IIndexResourceDao;
import cn.bjfu.fesdmp.sys.dao.IUserDao;
import cn.bjfu.fesdmp.utils.DateFormat;
import cn.bjfu.fesdmp.utils.JdbcByPropertiesUtil;
import cn.bjfu.fesdmp.utils.Pagination;
import cn.bjfu.fesdmp.web.jsonbean.DataSearch;

@Repository
public class DataDaoImpl extends AbstractGenericDao<DataJson> implements IDataDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	private static final Logger logger = Logger.getLogger(DataDaoImpl.class);
	@Autowired
	private IIndexResourceDao indexResourceDao;
	public DataDaoImpl() {
		super(DataJson.class);
	}

	
	
    private JdbcByPropertiesUtil jbpu = JdbcByPropertiesUtil.getInstance();  
    
    public JdbcByPropertiesUtil getJbpu() {  
        return jbpu;  
    }  
      
    public void setJbpu(JdbcByPropertiesUtil jbpu){  
        this.jbpu = jbpu;  
    }  
      
    public Properties getProperties(){  
        Properties pros = JdbcByPropertiesUtil.readPropertiesFile();  
        return pros;  
    }  
  
    /** 
     * 读取配置文件jdbc.properties中的数据库名称 
     * @return 
     * @throws Exception 
     */  
    public String getDataSourceName()throws Exception{  
        Properties pros = this.getProperties();  
        String dbName = pros.get("dbName").toString();  
        return dbName;  
    }  

	
	@Override
	public List<DataJson> findByCondtinWithOperationTime(String tableName,DataSearch condition,
			IOrder order, Pagination<DataJson> page, JoinMode joinMode) {
		DataJson dataJson=new DataJson();
		String sql=null;
		String unit=indexResourceDao.findUnitByIndex(tableName.substring(5));
		List<DataJson> result=new ArrayList();
		if(tableName!=null)
		{
		sql = "select * from "+tableName;	
		if (condition != null&&condition.getStation()=="") {
		
			if (condition.getStartTime() != null && condition.getEndTime() != null) {
				sql += " where time >= '" + DateFormat.getShortDate(condition.getStartTime()) +
						"' and  time <= '" + DateFormat.getShortDate(condition.getEndTime()) + "'";
			}
			if (condition.getStartTime() != null && condition.getEndTime() == null) {
				sql += " where time >=  '" + DateFormat.getShortDate(condition.getStartTime()) + 
						"' and time <= '" + DateFormat.getShortDate(new Date()) + "'";
			}
			
		}
		if (condition != null&&condition.getStation()!="") {
			
			if (condition.getStartTime() != null && condition.getEndTime() != null) {
				sql += " where time >= '" + DateFormat.getShortDate(condition.getStartTime()) +
						"' and  time <= '" + DateFormat.getShortDate(condition.getEndTime()) + "' and station LIKE '"+
						condition.getStation()+
						"'";
			}
			if (condition.getStartTime() != null && condition.getEndTime() == null) {
				sql += " where time >=  '" + DateFormat.getShortDate(condition.getStartTime()) + 
						"' and time <= '" + DateFormat.getShortDate(new Date()) + "' and station LIKE '"+
						condition.getStation()+
						"'";
			}
			else
				sql +=" where station LIKE '"+
				condition.getStation()+
				"'";
			
		}
		sql=sql+" order by time asc";
		
		List<Map<String, Object>> result0 =jdbcTemplate.queryForList(sql);

		for(int i=0;i<result0.size();i++){
			DataJson datajson=new DataJson();
			datajson.setId(Integer.valueOf(result0.get(i).get("id").toString()));
			datajson.setTime((Date)result0.get(i).get("time"));
			datajson.setData(result0.get(i).get("data").toString());
			datajson.setStation(result0.get(i).get("station").toString());
			datajson.setUnit(unit);
			result.add(datajson);
		}	
		logger.info(sql);
		if (page != null) {
			page.setTotalRecord(result.size());
			page.setDatas(result);
			return result;
		}else{
			return result;
		}
		}
		else
			return result;
		
	}
	
	@Override
    public List<TableJson> findTable(){  
        Connection conn = jbpu.getConnection();  
        ResultSet rs = null;  
        List<TableJson> list = new ArrayList();  
        try {  
            Properties pros = this.getProperties();  
            String schema = pros.get("jdbc.username").toString();  
            DatabaseMetaData metaData = conn.getMetaData();  
            rs = metaData.getTables(null, schema, null, new String[]{"TABLE","VIEW"});  
            while(rs.next()){  
                String tableName = rs.getString("TABLE_NAME");  
                if(tableName.substring(0,1).equals("1")||tableName.substring(0,1).equals("2")){
                	TableJson tablejson=new TableJson();
                	tablejson.setName(tableName);
                	list.add(tablejson);  
                }
            }  
        } catch (SQLException e) {  
            e.printStackTrace();  
        } finally{  
            jbpu.close(rs, null, conn);  
        }  
        return list;  
    }  
	
	@Override
	public void modifyTableName(String oldName,String newName){
        Connection conn = jbpu.getConnection();  
        ResultSet rs = null;  
        List<TableJson> list = new ArrayList();  
        try {  
            Properties pros = this.getProperties();  
            String schema = pros.get("jdbc.username").toString();  
            DatabaseMetaData metaData = conn.getMetaData();  
            rs = metaData.getTables(null, schema, null, new String[]{"TABLE","VIEW"});  
            while(rs.next()){  
                String tableName = rs.getString("TABLE_NAME");  
                if(tableName.substring(0,1).equals("1")||tableName.substring(0,1).equals("2")){
                	TableJson tablejson=new TableJson();
                	tablejson.setName(tableName);
                	list.add(tablejson);  
                }
            }  
        } catch (SQLException e) {  
            e.printStackTrace();  
        } finally{  
            jbpu.close(rs, null, conn);  
        }  
		for(int i=0;i<list.size();i++){
			if(list.get(i).getName().substring(5).equals(oldName)){
				String sql="rename table "+list.get(i).getName()+" to "+list.get(i).getName().substring(0,5)+newName;
				jdbcTemplate.update(sql);
			}	
		}
		
	}
	
	
	@Override
	public void dataInsert(String table,List<DataJson> list){
		for(int i=0;i<list.size();i++){
			String sql=null;
			Date date=list.get(i).getTime();
			sql = "insert into "+table+" (time,data,station) values('"+(1900+date.getYear())+
					"-"+(1+date.getMonth())+
					"-"+date.getDate()+
					" "+date.getHours()+
					":"+date.getMinutes()+
					":"+date.getSeconds()+"','"+list.get(i).getData()+"','"+list.get(i).getStation()+"')";
			jdbcTemplate.update(sql);		
		}
	}
	
	public void modifyData(DataJson data,String tableName){
		String sql=null;
		sql="update "+tableName+" set data='"+data.getData()+"' where id="+data.getId();
		jdbcTemplate.update(sql);
	}
	public void deleteDataById(String tableName, String id){
		String sql=null;
		sql="delete from "+tableName+" where id="+id;
		jdbcTemplate.update(sql);
	}
	public DataJson findDataById(String id,String tableName){
		String sql=null;
		sql="select * from "+tableName+" where id="+id+" order by time asc";
		List<Map<String, Object>> result0 =jdbcTemplate.queryForList(sql);

			DataJson datajson=new DataJson();
			datajson.setId(Integer.valueOf(result0.get(0).get("id").toString()));
			datajson.setTime((Date)result0.get(0).get("time"));
			datajson.setData(result0.get(0).get("data").toString());
			return datajson;
	
		
	}
	public void deleteTable(String tableName){
		String sql=null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		Date date=new Date();
		String now=sdf.format(date);
		sql="rename table "+tableName+"  to deleted_"+now+"_"+tableName;
		jdbcTemplate.update(sql);
	}
	
}
 