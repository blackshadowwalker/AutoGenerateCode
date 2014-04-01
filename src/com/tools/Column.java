/*
* @author : karl
* @creation : 2014-3-30 下午03:28:21
* @description : 
*
*/

package com.tools;

public class Column {
	
	private String name;
	private String type;
	private String text;
	
	
	public String toString(){
		return "name="+name+", text="+text+", type="+type;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}
	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

}
