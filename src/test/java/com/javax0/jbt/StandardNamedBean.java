package com.javax0.jbt;

@SuppressWarnings("unused")
public class StandardNamedBean {
	private String fieldWithNoGetter;
	private String fieldWithNoSetter;

	public String getFieldWithNoSetter() {
		return fieldWithNoSetter;
	}

	private String fieldWith;
	private String field4;
	private String goodField;

	public String getGoodField() {
		return goodField;
	}

	public void setGoodField(String goodField) {
		this.goodField = goodField;
	}

	private int intField;

	public int getIntField() {
		return intField;
	}

	public void setIntField(int intField) {
		this.intField = intField;
	}

}
