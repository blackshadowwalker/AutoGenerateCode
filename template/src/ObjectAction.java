/*
* @author : ${author}
* @creation : ${dateTime}
* @ModuleName : ${ModuleName} 
* @description : 
* 	${description}
*
*/

package com.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.base.BaseAction;
import com.bean.${ObjectName};
import com.service.${ObjectName}Service;
import com.util.Util;

public class ${ObjectName}Action extends BaseAction {

	${ObjectName}Service ${objectName}Service;// ObjectService
	${ObjectName} ${objectName};// object
	List<${ObjectName}> list;// Object list

	@Override
	public String handle() throws Exception {
		if(method==null){
			if(id==null)
				return query();
			else
				return view();
		}
		return null;
	}

	/**
	 *  save: add or update 
	 * @return ;
	 * @throws Exception
	 */
	public String save() throws Exception{
		if(${objectName}==null)
			return Util.ERROR;
		
		if( !this.validate(null) ){
			this.setMsg("${ModuleName}表单不正确");
			return Util.EDIT;
		}
		
		//set object.id from request.getParameter("id");
		String idstr = request.getParameter("id");
		if(idstr!=null && !idstr.trim().isEmpty())
			${objectName}.setId( Integer.parseInt(idstr) );
		
		//call saveOrUpdate
		String result = Util.NONE;
		if(${objectName}!=null && ${objectName}.getId()!=null && ${objectName}.getId()>0){
			this.setLogmsg("修改${ModuleName}信息");
			result = ${objectName}Service.update(${objectName});
		}else{
			this.setLogmsg("添加${ModuleName}信息");
			result = ${objectName}Service.add(${objectName});
		}
		//result 
		if(result.equals(Util.SUCCESS)){
			this.setMsg("${ModuleName}.操作成功");
			this.addLogmsg("@id=" + ${objectName}.getId());
			${objectName} = null;
#if ( ${JspQueryType}=="list" )		
			return this.query();
#else			
			return Util.LIST;
#end
		}else{
			this.setMsg("${ModuleName}.操作失败");
			this.addLogmsg("@id=" + ${objectName}.getId());
			return Util.EDIT;
		}
	}
	
	public String saveJson() throws Exception{
		
		JSONObject json = new JSONObject();
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();	
		
		if(${objectName}==null){
			json.put("error", -1);
			json.put("msg", "空数据");
			out.println(json.toString());
			out.close();
			return Util.NONE;
		}
		
		if( !this.validate(null) ){
			json.put("error", -1);
			json.put("msg", "${ModuleName}表单不正确");
			out.println(json.toString());
			out.close();
			return Util.NONE;
		}

		//set object.id from request.getParameter("id");
		String idstr = request.getParameter("id");
		if(idstr!=null && !idstr.trim().isEmpty())
			${objectName}.setId( Integer.parseInt(idstr) );
		
		String result = Util.NONE;
		if(${objectName}!=null && ${objectName}.getId()!=null && ${objectName}.getId()>0){
			this.setLogmsg("修改${ModuleName}信息");
			result = ${objectName}Service.update(${objectName});
		}else{
			this.setLogmsg("添加${ModuleName}信息");
			result = ${objectName}Service.add(${objectName});
		}
		//result 
		if(result.equals(Util.SUCCESS)){
			this.setMsg("${ModuleName}.操作成功");
			this.addLogmsg("@id=" + ${objectName}.getId());
			json.put("error", 0);
			json.put("msg", this.getMsg());
			json.put("id", ${objectName}.getId());
			${objectName} = null;
		}else{
			this.setMsg("${ModuleName}.操作失败");
			this.addLogmsg("@id=" + ${objectName}.getId());
			json.put("error", 0);
			json.put("msg", this.getMsg());
		}
		out.println(json.toString());
		out.close();
		return Util.NONE;
	}
	
	
	/**
	 * 
	 * @return json 
	 */
	public String listTree(){
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8"); 
		response.setHeader("pragma", "no-cache");  
		response.setHeader("cache-control", "no-cache");
		
		this.setLogmsg("查询${ModuleName}信息");
		list = ${objectName}Service.list(${objectName});
		JSONArray json = JSONArray.fromObject(list);
		try {
			PrintWriter out = response.getWriter();	
			if(json!=null)
				out.write(json.toString());
			else
				out.write("[{}]");
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Util.NONE;
	}

	@Override
	public String add() throws Exception {
		return this.save();
	}

	@Override
	public String beforeAdd() throws Exception {
	//	List ObjectList = ${objectName}Service.beforeAdd();
	//	request.setAttribute("ObjectList", ObjectList);
		return Util.EDIT;
	}

	@Override
	public String beforeUpdate() throws Exception {
		if(id==null || id <0)
			msg = "${ModuleName}.id错误";
		else
			${objectName} = ${objectName}Service.get(id);
		return Util.EDIT;
	}

	@Override
	public String delete() throws Exception {
		if(id!=null && id>0){
			${objectName} = ${objectName}Service.get(id);
			String result = ${objectName}Service.delete(id);
			if(result!=null && result.endsWith(Util.SUCCESS))
				this.setMsg( "删除${ModuleName}成功" );
			else
				this.setMsg( "删除${ModuleName}失败" );
			return this.query();
		}else
			return Util.ERROR; 
	}


	@Override
	public String query() throws Exception {
		this.setLogmsg("${ModuleName}查询");
		list = ${objectName}Service.query(${objectName}, page);
		return Util.LIST;
	}

	@Override
	public String update() throws Exception {
		return this.save();
	}

	@Override
	public boolean validate(Object obj) throws Exception {
		boolean ret = true;
		/*
		if(${objectName}.getTitle()==null || ${objectName}.getTitle().isEmpty()){
			this.addFieldError("${objectName}.title", "xx不能空");
			ret = false;
		}
		*/
		return ret;
	}

	@Override
	public String view() throws Exception {
		if(id==null || id<1){
			msg = "ID错误";
		}
		${objectName} = ${objectName}Service.get(id);
		if(${objectName}==null)
			msg = "不存在";
		return Util.VIEW;
	}

	public ${ObjectName}Service get${ObjectName}Service() {
		return ${objectName}Service;
	}

	public void set${ObjectName}Service(${ObjectName}Service objectService) {
		this.${objectName}Service = objectService;
	}

	public ${ObjectName} get${ObjectName}() {
		return ${objectName};
	}

	public List<${ObjectName}> getList() {
		return list;
	}

	public void set${ObjectName}(${ObjectName} ${objectName}) {
		this.${objectName} = ${objectName};
	}

	public void setList(List<${ObjectName}> list) {
		this.list = list;
	}

}
