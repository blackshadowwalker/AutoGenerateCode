/*
* @author : ${author}
* @creation : ${dateTime}
* @ModuleName : ${ModuleName} 
* @description : 
* 	${description}
*
*/

package com.service.impl;

import java.util.List;

import com.base.PageBean;
import com.bean.${ObjectName};
import com.dao.${ObjectName}Dao;
import com.service.${ObjectName}Service;

public class ${ObjectName}ServiceImpl implements ${ObjectName}Service {

	${ObjectName}Dao ${objectName}Dao;
	
	@Override
	public ${ObjectName} get(Integer id) {
		return ${objectName}Dao.get(id);
	}

	@Override
	public List<${ObjectName}> query(${ObjectName} ${objectName}, PageBean page) {
		return ${objectName}Dao.query(${objectName}, page);
	}
	@Override
	public List<${ObjectName}> list(${ObjectName} ${objectName}) {
		return ${objectName}Dao.list(${objectName});
	}

	public String	add(${ObjectName} ${objectName}){
		return ${objectName}Dao.add(${objectName});
	}

	public String 	delete(Integer id){
		return ${objectName}Dao.delete(id);
	}

	public String	update(${ObjectName} ${objectName}){
		return ${objectName}Dao.update(${objectName});
	}
	
	
	// ========= getter and setter  =========
	public ${ObjectName}Dao get${ObjectName}Dao() {
		return ${objectName}Dao;
	}

	public void set${ObjectName}Dao(${ObjectName}Dao ${objectName}Dao) {
		this.${objectName}Dao = ${objectName}Dao;
	}


}
