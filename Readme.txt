
SSH2 Auto Generate Code
SSH2 业务处理代码自动生成器

Tools : ant + velocity(called by ContentEngine.java)

Description:

1. 主要文件说明
   1) build.xml 主文件，载入config/build.properties配置，加载lib，运行task，生成java&jsp
   2) build.properties 配置模版template目录和路径, 配置代码输出目录
   3) control/template.properties 配置业务信息，以及vm模版文件路径 
   4) template/ 模版路径，
			|__control/ 放置模版属性参数template.properties，此处的属性可以直接在vm中使用
			|__src/ 放置java模版文件
			|__jsp/ 放置jsp模版文件
			|__xml/ 放置配置参数的xml文件，目前是 hibernate.hbm.xml文件，
					通过此文件的property在jsp页面自动生成列表
   5) src/java, 放置velocity处理代码，如 ContentEngine
   
   
2. 使用方法
   1) 用eclipse在DB Browser选择数据库和表后，自动生成Bean源码 Object.java 和 Object.hbm.xml
   2) 将 Object.hbm.xml 拷贝到templete/xml/下面，并在 template/xml/template.properties中配置此路径
   3) template.propertiee可以根据业务复制多份，并在 config/build.properties中配置模版控制文件
   4) 如果jsp和src有变动，请在template.properties中配置
   5) 如果jsp.vm或java.vm中需要更多的变量，可以在template.properties中配置，然后就可以在vm文件中直接使用了
   6) 如果需要同时加载其他xml文件，请在ContentEngine.java中增加参数，生成setter和getter方法，
	  并将com.tools包导出到 lib/VContentEngine.jar; 并在 build.xml中生命 com.tools.ContentEngine为一个task
   7) 用ant运行build.xml
   8) 生成成功后，到${output.dir}目录，将生成的代码复制到工程目录即可
   # 忽略9-12 部
	   9) ${output.dir}/src/struts/struts-object.xml中的action声明复制到项目下的struts/struts-sys.xml中
	   10) ${output.dir}/src/spring/action-object-spring.xml中的action声明复制到项目下spring/action-spring.xml
	   11) ${output.dir}/src/spring/dao-object-spring.xml中的dao声明复制到项目下spring/dao-spring.xml
	   12) ${output.dir}/src/spring/service-object-spring.xml中的dao声明复制到项目下spring/service-spring.xml
   13) Finish, 在浏览其输入(或者点击对应的菜单)触发Action即可进行增删改查功能，其他拓展功能/管理功能需要再次技术上手动额外添加与修改


   
   