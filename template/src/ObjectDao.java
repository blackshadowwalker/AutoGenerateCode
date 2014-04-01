/*
* @author : ${author}
* @creation : ${dateTime}
* @ModuleName : ${ModuleName} 
* @description : 
* 	${description}
*
*/

package com.dao;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.base.BaseDao;
import com.base.PageBean;
import com.bean.${ObjectName};
import com.bean.ArticleInfo;
import com.util.Util;

public class ${ObjectName}Dao extends BaseDao {

	public ${ObjectName} get(Integer id){
		this.session = this.getSession();
		if(id==null || id<1)
			return null;
		return (${ObjectName}) this.session.get(${ObjectName}.class, id);
	}
	
	public List<${ObjectName}> query(${ObjectName} ${objectName}, PageBean page){
		this.session = this.getSession();
		String hql = "From ${ObjectName} where 1=1 and status != 0";
		String where = "";
		if(${objectName}!=null){
	//		if(${objectName}.getUserCode()!=null && !${objectName}.getUserCode().trim().isEmpty())
	//		where += " and userCode like '%"+${objectName}.getUserCode().trim()+"%' ";
		}
		String order = " order by id desc ";
		String sql = hql+where+order;
		Long count = (Long) session.createQuery("select count(*) "+ sql).uniqueResult();
		if(page!=null && count!=null )
			page.setTotalRow(count.intValue());
		
		Query query = session.createQuery(sql);
		if(page!=null && page.getPageSize()>0 && page.getCurrentPage()>0){
			query.setFirstResult( (page.getCurrentPage()-1)*page.getPageSize());
			query.setMaxResults( page.getPageSize() );
		}
		return query.list();
	}
	
	public List<${ObjectName}> list(${ObjectName} ${objectName}){
		this.session = this.getSession();
		String hql = "From ${ObjectName} where 1=1 and status!=0";
		if(${objectName}!=null){
		//	if(${objectName}.getUserCode()!=null && !${objectName}.getUserCode().trim().isEmpty())
		//		hql += " and userCode like '%"+${objectName}.getUserCode().trim()+"%' ";
		}
		hql += " order by id desc ";
		return session.createQuery(hql).list();
	}
	
	public String  add(${ObjectName} ${objectName}){
		this.session = this.getSession();
		if(${objectName}!=null){
			if(${objectName}.getStatus()==null)
				${objectName}.setStatus(1);
		//	${objectName}.setCtTime(new Timestamp(System.currentTimeMillis()));
			${objectName}.setLastUpdate(new Timestamp(System.currentTimeMillis()));
			session.save(${objectName});
			return Util.SUCCESS;
		}else
			return Util.FAILE;
	}
	
	public String 	delete(Integer id){
		Session session = super.getSession();
		${ObjectName} ${objectName} = (${ObjectName}) session.get(${ObjectName}.class, id);
		if(${objectName}==null)
			return Util.FAILE;
		${objectName}.setStatus(0);
		${objectName}.setLastUpdate(new Timestamp(System.currentTimeMillis()));
	//	session.delete(${objectName});  //or  session.update(${objectName});
#if ( ${delete}=="true" )
		session.delete(${objectName});
#else
		session.update(${objectName});
#end
		return Util.SUCCESS;
	}
	
	public String update(${ObjectName} ${objectName}){
		Session session = super.getSession();
		${objectName}.setLastUpdate(new Timestamp(System.currentTimeMillis()));
		session.update(${objectName});
		return Util.SUCCESS;
	}
	
}
