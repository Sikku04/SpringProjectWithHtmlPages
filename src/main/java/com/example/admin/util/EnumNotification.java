package com.example.admin.util;

public enum EnumNotification {

	Student_SIGNUP("STUDENT_SIGNUP"), Teacher_SIGNUP("Teacher_SIGNUP"), Staff_SIGNUP("STAFF_SIGNUP");

	private String type;

	private EnumNotification() {

	}

	private EnumNotification(String type) {
	this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
