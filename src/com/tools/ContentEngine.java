/*
* @author : karl
* @creation : 2014-3-29 下午06:02:04
* @description : 
*
*/

package com.tools;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */

class AppException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AppException(String string) {
		super(string);
	}

}

public class ContentEngine  extends Task {
	
	private VelocityContext context = null;

	private Template template = null;
	
	private String contextProperties;//${template.properties}
	private String controlTemplate;//{Object*.template}
	private String templatePath;//${template.dir}
	private String outputDirectory;
	private String outputFile;// e.g java file from controlTemplate
	private String hbmXmlFile;
	private String targetDatabase;
	
	private String dateTime ;
	
	public ContentEngine(){}
	
	@Override
	public void execute() throws BuildException {
		System.out.println("ContentEngine ");
		
		try {
			Properties p = new Properties();
			templatePath = templatePath.replaceAll("\\\\", "/");
			p.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, templatePath);
			p.setProperty(Velocity.RUNTIME_LOG, templatePath + "/velocity.log");
			this.init(p);
			
			//这样再调用
			System.out.println("Load  contextProperties = "+contextProperties);
			Properties varp = new Properties();
			FileInputStream fin = new FileInputStream(contextProperties);
			InputStreamReader in= new InputStreamReader(fin,"UTF-8");
//			FileInputStream in = new FileInputStream(contextProperties);
			varp.load(in);

			System.out.println("setTemplate  controlTemplate = "+controlTemplate);
			this.setTemplate(controlTemplate, "utf-8");
			
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if(dateTime==null || dateTime.isEmpty())
				dateTime = df.format(new Date());
			this.put("dateTime", dateTime);

			Iterator<?> it = varp.keySet().iterator();
			String key = "";
			String value = "";
			while (it.hasNext()) {
				key = (String) it.next();
				value = varp.getProperty(key); // new String( varp.getProperty(key).getBytes(), "utf-8");
				this.put(key, value);
			}
			
			System.out.println("xmlFile = "+ templatePath+"/"+hbmXmlFile);
			if(hbmXmlFile!=null){
				this.put("columnList", this.parseHbmXml(templatePath+"/"+hbmXmlFile));
			}

			this.toFile(outputDirectory + '/' + outputFile);//导出的路径，由参数传递。
			
			//else 其他情况处理部分，这里省略。			
		} catch (AppException ae) {
			ae.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * @param properties
	 * @throws Exception
	 */
	public void init(String properties) throws Exception {
		if (properties != null && properties.trim().length() > 0) {
			Velocity.init(properties);
		} else {
			Velocity.init();
		}
		context = new VelocityContext();
	}

	public void init(Properties properties) throws Exception {

		Velocity.init(properties);
		context = new VelocityContext();
	}

	/**
	 * 
	 * @param key
	 * @param value
	 */
	public void put(String key, Object value) {
		context.put(key, value);
	}

	/**
	 * 设置模版
	 * 
	 * @param templateFile
	 *            模版文件
	 * @throws AppException
	 */
	public void setTemplate(String templateFile) throws AppException {
		try {
			template = Velocity.getTemplate(templateFile);
		} catch (ResourceNotFoundException rnfe) {
			rnfe.printStackTrace();
			throw new AppException(" error : cannot find template "+ templateFile);
		} catch (ParseErrorException pee) {
			throw new AppException(" Syntax error in template " + templateFile
					+ ":" + pee);
		} catch (Exception e) {
			throw new AppException(e.toString());
		}

	}

	/**
	 * 设置模版
	 * 
	 * @param templateFile
	 *            模版文件
	 * @throws AppException
	 */
	public void setTemplate(String templateFile, String characterSet)
			throws AppException {
		try {
			System.out.println("templateFile="+templateFile);
			System.out.println("characterSet="+characterSet);
			template = Velocity.getTemplate(templateFile, characterSet);
		} catch (ResourceNotFoundException rnfe) {
			rnfe.printStackTrace();
			throw new AppException(" error : cannot find template "
					+ templateFile);
		} catch (ParseErrorException pee) {
			throw new AppException(" Syntax error in template " + templateFile
					+ ":" + pee);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 转换为文本文件
	 */
	public String toText() throws AppException {
		StringWriter sw = new StringWriter();
		try {
			template.merge(context, sw);
		} catch (Exception e) {
			throw new AppException(e.toString());
		}
		return sw.toString();
	}

	/**
	 * 
	 * @param fileName
	 */
	public void toFile(String fileName) throws AppException {
		try {
			System.out.println(" vm toFile @ "+fileName);
			StringWriter sw = new StringWriter();
			template.merge(context, sw);

			FileOutputStream fos = new FileOutputStream(fileName);
			Writer out = new OutputStreamWriter(fos, "UTF-8");
			out.write(sw.toString());
			out.close();
			fos.close();
			
		} catch (Exception e) {
			throw new AppException(e.toString());
		}

	}
	
	/**
	 * 解析hibernateObject.hbm.xml中的字段信息
	 * @param fileName ：hbm.xml 全路径 
	 * @return List<Column>  
	 */
	public List<Column> parseHbmXml(String fileName){
		List<Column> columnList = new ArrayList<Column>();
		try { 
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); 
			factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
			DocumentBuilder db = factory.newDocumentBuilder(); 
			Document document = db.parse(new File(fileName)); 

			//	NodeList root = document.getChildNodes();
			NodeList property = document.getElementsByTagName("property");
			if(property!=null ){
				System.out.println("property.length=  "+property.getLength()+"\n");
				if(property.getLength()>0){
					for (int j = 0; j < property.getLength(); j++){
						Node column = property.item(j);
						NamedNodeMap map = column.getAttributes();
						Column c = new Column();
						if(map.getNamedItem("name")!=null)
							c.setName(map.getNamedItem("name").getNodeValue());
						if(map.getNamedItem("type")!=null)
							c.setType(map.getNamedItem("type").getNodeValue());
						if(column.getTextContent()!=null)
							c.setText(column.getTextContent().trim());
						columnList.add(c);
					}
				}
			}
		//	System.out.println("columnList.length = "+columnList.size()); 
		//	System.out.println(""+columnList.toString());
		//	System.out.println("解析完毕"); 
		} catch (FileNotFoundException e) { 
			e.printStackTrace();
		} catch (ParserConfigurationException e) { 
			e.printStackTrace();
		} catch (SAXException e) { 
			e.printStackTrace();
		} catch (IOException e) { 
			e.printStackTrace();
		} 
		return columnList;
	}
	
	
/*
	public static void main(String[] args) {
		ContentEngine content = new ContentEngine();
		try {
			Properties p = new Properties();

			Properties varp = new Properties();

			String path = args[1];

			p.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, path);
			p.setProperty(Velocity.RUNTIME_LOG, path + "velocity.log");

			content.init(p);

			FileInputStream in = new FileInputStream(args[2]);
			varp.load(in);

			content.setTemplate(args[3], "gb2312");

			Iterator it = varp.keySet().iterator();
			String key = "";
			String value = "";
			while (it.hasNext()) {
				key = (String) it.next();
				value = varp.getProperty(key);
				content.put(key, value);
			}

			if (args[0].equals("DaoImpl")) {
				content.put("ObjectName", args[8]);
				content.toFile(args[9] + '/' + args[10]);//导出的路径，由参数传递。
			}
			//else 其他情况处理部分，这里省略。			
		} catch (AppException ae) {
			ae.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
*/

	/**
	 * @return the context
	 */
	public VelocityContext getContext() {
		return context;
	}

	/**
	 * @param context the context to set
	 */
	public void setContext(VelocityContext context) {
		this.context = context;
	}

	/**
	 * @return the template
	 */
	public Template getTemplate() {
		return template;
	}

	/**
	 * @param template the template to set
	 */
	public void setTemplate(Template template) {
		this.template = template;
	}

	/**
	 * @return the contextProperties
	 */
	public String getContextProperties() {
		return contextProperties;
	}

	/**
	 * @param contextProperties the contextProperties to set
	 */
	public void setContextProperties(String contextProperties) {
		this.contextProperties = contextProperties;
	}

	/**
	 * @return the controlTemplate
	 */
	public String getControlTemplate() {
		return controlTemplate;
	}

	/**
	 * @param controlTemplate the controlTemplate to set
	 */
	public void setControlTemplate(String controlTemplate) {
		this.controlTemplate = controlTemplate;
	}

	/**
	 * @return the outputDirectory
	 */
	public String getOutputDirectory() {
		return outputDirectory;
	}

	/**
	 * @param outputDirectory the outputDirectory to set
	 */
	public void setOutputDirectory(String outputDirectory) {
		this.outputDirectory = outputDirectory;
	}

	/**
	 * @return the templatePath
	 */
	public String getTemplatePath() {
		return templatePath;
	}

	/**
	 * @param templatePath the templatePath to set
	 */
	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}

	/**
	 * @return the outputFile
	 */
	public String getOutputFile() {
		return outputFile;
	}

	/**
	 * @param outputFile the outputFile to set
	 */
	public void setOutputFile(String outputFile) {
		this.outputFile = outputFile;
	}

	/**
	 * @return the xmlFile
	 */
	public String getHbmXmlFile() {
		return hbmXmlFile;
	}

	/**
	 * @param xmlFile the xmlFile to set
	 */
	public void setHbmXmlFile(String xmlFile) {
		this.hbmXmlFile = xmlFile;
	}

	/**
	 * @return the targetDatabase
	 */
	public String getTargetDatabase() {
		return targetDatabase;
	}

	/**
	 * @param targetDatabase the targetDatabase to set
	 */
	public void setTargetDatabase(String targetDatabase) {
		this.targetDatabase = targetDatabase;
	}

	/**
	 * @return the dateTime
	 */
	public String getDateTime() {
		return dateTime;
	}

	/**
	 * @param dateTime the dateTime to set
	 */
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

}
