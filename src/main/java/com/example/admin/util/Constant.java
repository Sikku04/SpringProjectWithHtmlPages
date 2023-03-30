package com.example.admin.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Constant {

	public static final String IMG_LOCATION = "/home/brsoft/Main-eclipse-workspace/Admin/src/main/resources/static/admin/dist/img/";
	public static final String PRODUCT_IMG_LOCATION = "http://localhost:8082";

	public static String getDateAndTime() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		df.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
		return String.valueOf(df.format(new Date()));
	}
}
