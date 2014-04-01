/*
* @author : ${author}
* @creation : ${dateTime}
* @ModuleName : ${ModuleName} 
* @description : 
* 	${description}
*
*/

package com.service;

import java.util.List;

import com.base.BaseService;
import com.base.PageBean;
import com.bean.${ObjectName};

public interface ${ObjectName}Service extends BaseService {

	public ${ObjectName} get(Integer id);

	public List<${ObjectName} > query(${ObjectName} ${objectName}, PageBean page);
	public List<${ObjectName} > list(${ObjectName} ${objectName});
	
	public String	add(${ObjectName} ${objectName});

	public String 	delete(Integer id);

	public String	update(${ObjectName} ${objectName});
	
}
