

package com.tools;

import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import com.tools.ContentEngine;

public class Test extends Task {
	
	private String contextProperties;
	private String controlTemplate;
	private String outputDirectory;
	private String templatePath;
	private String outputFile;
	private String xmlFile;
	private String targetDatabase;
	
	public Test(){ }

	public static void main(String[] args) {
		ContentEngine e = new ContentEngine();
		String fileName = "E:/karl/java/AutoGenerateCode/template/xml/Object.hbm.xml";
		List<Column> columnList = e.parseHbmXml(fileName);
		System.out.println("columnList.length = "+columnList.size()); 
		for(int k=0; k<columnList.size(); k++)
			System.out.println(k+1+" : "+columnList.get(k).toString());
		System.out.println("test");
	}


	@Override
	public void execute() throws BuildException {
		System.out.println(" 18684937833 Test Task");
		super.execute();
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
	public String getXmlFile() {
		return xmlFile;
	}

	/**
	 * @param xmlFile the xmlFile to set
	 */
	public void setXmlFile(String xmlFile) {
		this.xmlFile = xmlFile;
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

}
