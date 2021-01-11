package com.patternGrid.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Pattern_Type")
public class PatternType {

	@Id
	@GeneratedValue
	@Column(name = "Pattern_Type_Id")
	private int PatternTypeId;

	@Column(name = "Pattern_Row_Size")
	private int PatternRowSize;

	@Column(name = "Pattern_Col_Size")
	private int PatternColSize;

	public PatternType() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PatternType(int patternRowSize, int patternColSize) {
		super();
		PatternRowSize = patternRowSize;
		PatternColSize = patternColSize;
	}

	public int getPatternTypeId() {
		return PatternTypeId;
	}

	public int getPatternRowSize() {
		return PatternRowSize;
	}

	public void setPatternRowSize(int patternRowSize) {
		PatternRowSize = patternRowSize;
	}

	public int getPatternColSize() {
		return PatternColSize;
	}

	public void setPatternColSize(int patternColSize) {
		PatternColSize = patternColSize;
	}

	@Override
	public String toString() {
		return "PatternType [PatternTypeId=" + PatternTypeId + ", PatternRowSize=" + PatternRowSize
				+ ", PatternColSize=" + PatternColSize + "]";
	}

}
