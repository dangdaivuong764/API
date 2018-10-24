/**
 * 
 */
package com.qa.classes.advance;

import com.qa.classes.Info;

/**
 * @author nhan.nguyen
 *
 */
public class AdvPosts {
	private String id;
	private String title;
	private String author;
	private AdvInfo[] info;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public AdvInfo[] getInfo(){
		return info;
	}
	public void setInfo(AdvInfo[] info){
		this.info =  info;
	}
}
