<!-- 
* @author : ${author}
* @creation : ${dateTime}
* @ModuleName : ${ModuleName} 
* @description : 
* 	${description}
*
-->

<%@ page language="java" import="java.util.*"   pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 	<base href="<%=basePath%>/" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>${ModuleName}信息</title>
	<link rel="stylesheet" href="css/common.css" type="text/css" media="all"/>
	<link rel="stylesheet" href="css/main.css" type="text/css" media="all"/>
	
	<link rel="stylesheet" href="scripts/jquery/jquery-ui/css/drop-down-list/jquery-ui.css" type="text/css" media="all"/>
	
	<link rel="stylesheet" href="scripts/MiniTree/css/miniui/miniui.css" type="text/css" media="all"/>
	<script src="scripts/jquery/jquery-1.7.min.js" type="text/javascript"></script>
	
	<script src="scripts/MiniTree/core.js" type="text/javascript"></script>
	<script src="scripts/MiniTree/miniui.js" type="text/javascript" ></script>  
	<script src="scripts/jquery/jquery-ui/js/jquery-ui-1.8.16.custom.min.js" type="text/javascript"></script>
	
	<script src="scripts/validateForm.js" type="text/javascript"></script>
	<script src="scripts/Calendar.js" type="text/javascript"></script>

	<style type="">
		a.operate{
			text-decoration:none;
		}
		a.operate img{
			margin-left:10px;
		}
		td. title-header {
			cursor:pointer;
		}
		.mini-tree-leaf
		{
		    background-image:url(images/device/2.png);
		}
	</style>

	<script language="javascript">

	var msg="${msg}";
	if(msg!="null" && msg!="")
		alert(msg);

	</script>
	
</head>
<body>

		<form action="${ObjectName}Action?" method="post" id="mainForm" >
			<table id="table-data-outter">
				<tr>
					<td>
						<table id="table-data-inner" cellspacing="0">
							<tr id="tr-menu-path">
								<td colspan="2" nowrap="nowrap">
									<fieldset id="f-menu-path">
										当前位置:&nbsp;${ModuleName}管理 &gt; ${ModuleName}信息查询
									</fieldset>
									<fieldset id="f-condition-display">
										<img src="images/main/display_on.gif"
											onclick="displayCondition(this,'tr-menu-path')"
											align="bottom" width="17" height="15" alt="隐藏查询条件" />
									</fieldset>
								</td>
							</tr>
							<tr>
								<td>
								<!-- 查询条件  -->
								
#if ( ${JspQueryType}=="list" )									
								
	#foreach($column in ${columnList})
		#if($velocityCount > 3)
			#break;
		#end
		$column.text :<input type="text" name="${objectName}.$column.name"  value="${${objectName}.$column.name }" />
	#end 

									<input type="submit" value="查询" class="button" />
									
									&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="button" value="重置" class="button" onclick="ResetForm()" />
									<script>
										function ResetForm(){
											$("input[name='${objectName}.userCode']").val("");
											$("#mainForm").submit();
										}
									</script>
									&nbsp;&nbsp;&nbsp;&nbsp;
									<span style="float:right">
										<s:if test="#request.i>=0">
											<a href="${action}?method=beforeAdd" >
												<input type="button" value="添加" class="button" />
											</a>
										</s:if>
									</span>
#elseif ( ${JspQueryType}=="tree" )						
									 <label >&nbsp; ：</label>
			        				  <input id="key" class="mini-textbox" style="width:150px;" onenter="onKeyEnter"/>
			          					<a >
											<input  type="button" value="查询" class="button" onclick="search();" />                    
			          					</a>    	
									 <a href="${action}?method=beforeAdd">
										<input  type="button" value="添加顶级节点" class="button" />
				                    </a>
#end
								
										
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</form>
		
#if ( ${JspQueryType}=="tree" )
	## 树形显示列表
	
		##foreach($column in ${columnList})
		##	$velocityCount  $column.text :	<input type="text" name="${objectName}.$column.name"  value="${${objectName}.$column.name }" />
		##end 
	
		<div id="treegrid1" class="mini-treegrid " style="width:900px;height:350px;"     
		   url="<%=basePath %>/${action}?method=listTree"
		    ondrawcell="onDrawCell" showTreeIcon="true"  
		    treeColumn="name" idField="id" parentField="pid" resultAsTree="false"
		    allowResize="true" expandOnLoad="true" allowCellEdit="false" allowCellSelect="false"
		    allowDrag="true" allowDrop="true" allowLeafDropIn="true" 
		    >
	    
	    <div property="columns">
	        <div  field="id" width="30">ID</div>
	        
#foreach($column in ${columnList})
			 <div name="${column.name}" field="${column.name}" width="200"> ${column.text}
		        <input property="editor" class="mini-textbox" style="width:100%;"  /> 
		    </div> 
#end 
	         <div field="operator" width="100" >操作
	         </div>        
	    </div>
	    
	</div>
	
	<script type="text/javascript">
        mini.parse();
       	var tree = mini.get("treegrid1");
        function search() {
            var key = mini.get("key").getValue();
            if (key == "") {
                tree.clearFilter();
            } else {
                key = key.toLowerCase();                
                tree.filter(function (node) {
                	console.log(node.name);
                    var text = node.name ? node.name.toLowerCase() : "";
                    if (text.indexOf(key) != -1) {
                        return true;
                    }
                });
            }
        }
        function onKeyEnter(e) {
            search();
        }

        function onDrawCell(e){
            var node = e.node,
                column = e.column,
                field = e.field,
                value = e.value;

            //超链接任务
            if (field == "operator") {
                e.cellHtml = "<s:if test="#request.i>=0"><a class=opeate href=\"<%= basePath%>/${action}?method=beforeAdd&pid="+node.id+"&pname="+ node.name+"\" >"+
                				"<img src=\"images/button/addnew.gif\" title=\"添加\" style=\"cursor:pointer \"></img></a></s:if> "+
                			"<s:if test="#request.u>=0"><a class=opeate href=\"${action}?method=beforeUpdate&id="+node.id+ "\">"+
                				"<img src=\"images/button/pen.png\" title=\"修改\" border=0 style=\"cursor:pointer\"></img></a></s:if>"+
                			" <s:if test="#request.d>=0"><a class=opeate href=\"javascript:del("+ node.id +")\">"+
                				"<img src=\"images/button/delete.png\" title=\"删除\" style=\"cursor:pointer \"></img></a></s:if>";
            }
            //格式化日期
            if (field == "lastUpdateString") {
                if (mini.isDate(value)) 
                	e.cellHtml = mini.formatDate(value, "yyyy年MM月dd日");
             }
             
             if(field == "status"){
             	if(value==1)
	             	e.cellHtml = "启用";
	             else
	             	e.cellHtml = "禁用";
           	}
        };
        
	
    </script>

#else 
	## 分页列表显示
		
		<div id="list_div">
			<table id="table-view" cellpadding="0" cellspacing="1">
				<tr class="row-header">
					<td width="20">ID</td>
					
#foreach($column in ${columnList})
					<td ondblclick="return sortQuery('${objectName}.$column.name')" class="title-header" style="width:120px;" > $column.text </td>  
#end 

                    <td colspan="2" align="center" style="width:150px;" >操作</td> 
                </tr>
				<s:iterator id="l" value="list" status="index">
					<s:if test='#index.odd == true'>
						<s:set name="var_class" value="'row-odd'" />
					</s:if>
					<s:else>
						<s:set name="var_class" value="'row-even'" />
					</s:else>

					<tr class="<s:property value='var_class' />"
						onmouseover="this.className='row-over'"
						onmouseout="this.className='<s:property value='var_class' />'">
						<td align="center">
							<s:property value="#l.id" />
						</td>
#foreach($column in ${columnList})
	#if (${column.type} == "java.sql.Timestamp")
						<td align="center">
							<s:date name="#l.${column.name}" format="yyyy-MM-dd" />
						</td>
	#else
						<td align="center">
							<s:property value="#l.${column.name}" />
						</td>
	#end
#end 
						
						<td align="center" style="width:100px;">
							
							<span style="display:block;" >
								<s:if test="#request.u>=0">
									<a class=operate href="${action}?method=beforeUpdate&id=<s:property value='#l.id' />">
									<img src="images/button/pen.png" title="修改" border="0" style="cursor: pointer"></img>
									</a>
								</s:if>
								<s:if test="#request.d>=0">
									<a class=operate onclick="javaScript:del(<s:property value='#l.id' />);">
									<img src="images/button/delete.png" title="删除" style="cursor: pointer"></img>
									</a>
								</s:if>
								<s:if test="#request.v>=0">
									<a class=operate href="${action}?method=view&id=<s:property value='#l.id' />">
										<img src="images/button/doc.gif" title="详情" style="cursor:pointer "></img>
									</a>
								</s:if>
							</span>
						</td>
					</tr>
				</s:iterator>

			</table>
		</div> <!-- end of #list_div -->

		
		<div name="page-div" style="font-size: 13px font-family : '宋体'" align="center">
			
				共&nbsp; ${page.totalRow} &nbsp;条记录,
				共&nbsp; ${page.totalPage} &nbsp;页,
				
				&nbsp;&nbsp;
				<a class="submit"  href="${action}?page.currentPage=1&page.pageSize=${page.pageSize}" style="text-decoration:none;">
					<img src="images/page/PaginStart.gif"	border="0" />
				</a>
				&nbsp;&nbsp;
				<s:if test="page.hasPrePage==true">
					<a class="submit" href="${action}?page.currentPage=${page.prePage}&page.pageSize=${page.pageSize}" style="text-decoration:none;" 	>
						<img src="images/page/PaginLeft.gif" border="0" />
					</a>
				</s:if>
				<s:else>
					<img src="images/page/PaginLeftInactive.gif" border="0" title="已是第一页"/>
				</s:else>
				&nbsp;&nbsp;
				第:
				<select name="page.currentPage" onchange="changePage()" >
					 <script>
						for(var i=1; i<=${page.totalPage}; i++)
						{
							if(i==${page.currentPage})
								document.write("<option value='"+i+"' selected >"+i+"</option>");
							else
								document.write("<option value='"+i+"' >"+i+"</option>");
						}
					 </script>
				</select>
				页
				&nbsp;&nbsp;
				<s:if test="page.hasNextPage==true">
					<a class="submit" href="${action}?page.currentPage=${page.nextPage}&page.pageSize=${page.pageSize}"  style="text-decoration:none;">
						<img src="images/page/PaginRight.gif" border="0" />
					</a>
				</s:if>
				<s:else>
					<img src="images/page/PaginRightInactive.gif" border="0" title="已到达最后一页" />
				</s:else>
				&nbsp;&nbsp;
				<a class="submit" href="${action}?page.currentPage=${page.totalPage}&page.pageSize=${page.pageSize}"  style="text-decoration:none;">
					<img src="images/page/PaginEnd.gif" border="0" />
				</a> 
				
				每页显示
				<select name="page.pageSize" onchange="ResetSelect();changePage();" >
					<option value="5" >5</option>				
					<option value="10" >10</option>				
					<option value="15" >15</option>				
					<option value="20" >20</option>				
					<option value="50" >50</option>				
					<option value="100" >100</option>				
				</select>
				<script>
					$("select[name='page.pageSize']").val("${page.pageSize}");
				</script>
				条记录&nbsp;

		</div>
	
		<script>
		
		
			// down for page actions
			function ResetSelect(){
				$("select[name='page.currentPage']").val('1')
			}
			function changePage(){
				var curPage = $("select[name='page.currentPage']").val();
				var pageSize = $("select[name='page.pageSize']").val();
				var url = "${action}?page.currentPage="+curPage+"&page.pageSize="+pageSize;
				$("#mainForm").attr("action", url);
				$("#mainForm").submit();
			}
		
			$(document).ready(function(){
				$("a[class='submit']").click(function (event)
				{
				    event.preventDefault();
				});
				
				$("a[class='submit']").bind("click", function(){
					var url = $(this).attr("href");
					$("#mainForm").attr("action", url);
					$("#mainForm").submit();
				});
			});

		</script>
#end


<!-- booter script -->
	<script>
	
		 function del(id){
        	console.log(id);
			if(confirm("确定删除吗？")){
				location.href="<%= basePath%>/${action}?method=delete&id="+id;
			}
		}
		
		$(document).ready(function(){
			
			setTimeout(function(){
				var WinAlerts = window.alert;
				window.alert = function (e) 
				{
				//	WinAlerts("嘿嘿,和谐内容:"+e);
					if (e != null && (e.indexOf("试用到期")>-1 || e.indexOf("www.miniui.com")) )
					{
						//和谐了
					//	WinAlerts("和谐了miniui,嘿嘿!");
					}
					else
					{
						WinAlerts (e);
					}
					
				};   
				//alert("seted alert");
			}, 200);
			
			
		});
		
	
	</script>
	
	</body>

</html>