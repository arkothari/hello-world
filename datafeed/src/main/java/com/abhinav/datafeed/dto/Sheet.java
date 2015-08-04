package com.abhinav.datafeed.dto;

import org.springframework.data.annotation.Id;

public class Sheet {
	
	@Id
	private String id;
	private String name;
	private String publicId;
	private String crtBy;
	private String crtTS;
	private String version;
	private String comments;
	private String[][] data;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPublicId() {
		return publicId;
	}
	public void setPublicId(String publicId) {
		this.publicId = publicId;
	}
	public String getCrtBy() {
		return crtBy;
	}
	public void setCrtBy(String crtBy) {
		this.crtBy = crtBy;
	}
	public String getCrtTS() {
		return crtTS;
	}
	public void setCrtTS(String crtTS) {
		this.crtTS = crtTS;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String[][] getData() {
		return data;
	}
	public void setData(String[][] data) {
		this.data = data;
	}
	
	@Override
	public String toString(){
		return name+"-"+publicId+"-"+version+"-"+data.toString();
	}

}
