/*
 * @author : karl
 * @creation : 2014-3-30 下午01:40:18
 * @description : 
 *
 */

package com.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.tools.Column;

public class XmlRead {
	
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
							c.setName(map.getNamedItem("name").getNodeName());
						if(map.getNamedItem("name")!=null)
							c.setName(map.getNamedItem("type").getNodeName());
						if(column.getTextContent()!=null)
							c.setName(column.getTextContent().trim());
						columnList.add(c);
					}
				}
			}
			System.out.println("columnList.length = "+columnList.size()); 
			System.out.println("解析完毕"); 
		} catch (FileNotFoundException e) { 
			System.out.println(e.getMessage()); 
		} catch (ParserConfigurationException e) { 
			System.out.println(e.getMessage()); 
		} catch (SAXException e) { 
			System.out.println(e.getMessage()); 
		} catch (IOException e) { 
			e.printStackTrace();
			System.out.println(e.getMessage()); 
		} 
		return columnList;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String fileName = "E:/karl/java/AutoGenerateCode/template/xml/Object.hbm.xml";
		(new XmlRead()).parseHbmXml(fileName); 

		
	}

}
