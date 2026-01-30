package org.openmrs.module.registration;

import java.io.Serializable;

public class LocationAddress implements Serializable {
	
	private Integer id;
	
	private String code;
	
	private String color;
	
	private String name;
	
	private String nature;
	
	private Integer parent;
	
	public LocationAddress(Integer id, String code, String color, String name, String nature, Integer parent) {
		this.id = id;
		this.code = code;
		this.color = color;
		this.name = name;
		this.nature = nature;
		this.parent = parent;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getColor() {
		return color;
	}
	
	public void setColor(String color) {
		this.color = color;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getNature() {
		return nature;
	}
	
	public void setNature(String nature) {
		this.nature = nature;
	}
	
	public Integer getParent() {
		return parent;
	}
	
	public void setParent(Integer parent) {
		this.parent = parent;
	}
	
}
