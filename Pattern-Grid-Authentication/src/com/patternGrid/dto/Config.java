package com.patternGrid.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Config")
public class Config {

	@Id
	@GeneratedValue
	@Column(name = "Config_Id")
	private int configId;

	@Column(unique = true, name = "Param_Name")
	private String paramName;

	@Column(name = "Param_Value")
	private String paramValue;

	public Config() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Config(String paramName, String paramValue) {
		super();
		this.paramName = paramName;
		this.paramValue = paramValue;
	}

	public int getConfigId() {
		return configId;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	@Override
	public String toString() {
		return "Config [configId=" + configId + ", paramName=" + paramName + ", paramValue=" + paramValue + "]";
	}

}
