package com.iod.app.model;

public class Configuration {
	
	private String id;
	private String name;
	private String value;
	
	public Configuration() {
		super();
	}
	
	public Configuration(String id, String name, String value) {
		super();
		this.id = id;
		this.name = name;
		this.value = value;
	}

	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setValue(String value) {
		this.value = value;
	}

}
