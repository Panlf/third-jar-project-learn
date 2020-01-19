package com.plf.learn.elasticjob;

public class FileCustom {

	private String id;
	
	private String name;
	
	private String type;
	
	private String content;
	
	private Boolean backUp=false;

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Boolean getBackUp() {
		return backUp;
	}

	public void setBackUp(Boolean backUp) {
		this.backUp = backUp;
	}

	public FileCustom(String id, String name, String type, String content) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.content = content;
	}

	public FileCustom() {
		super();
	}
}
