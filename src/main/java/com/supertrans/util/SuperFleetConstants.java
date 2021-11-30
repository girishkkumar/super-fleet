package com.supertrans.util;

import java.time.format.DateTimeFormatter;

public class SuperFleetConstants {

	public static final String ADMIN = "ADMIN";

	public static final String USER = "USER";
	
	public static final String AUTH = "Authorization";

	public static final DateTimeFormatter LOCALDATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	public static final DateTimeFormatter LOCALDATETIME_MIN_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

	public static final DateTimeFormatter LOCALDATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	public static final Integer BUS_CAPACITY_24 = 24;
	
	public static final Integer BUS_CAPACITY_36 = 36;
	
	public static final DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
	public static final DateTimeFormatter formatSingleMS = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.S'Z'");
	public static final DateTimeFormatter formatWithoutMS = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

	public static final String THUMBNAIL = "https://icons.iconarchive.com/icons/fasticon/happy-bus/128/bus-blue-icon.png";

	public static final String ACTUAL_IMAGE = "https://image.freepik.com/free-vector/bus-driver-concept-illustration_114360-6330.jpg";

}
