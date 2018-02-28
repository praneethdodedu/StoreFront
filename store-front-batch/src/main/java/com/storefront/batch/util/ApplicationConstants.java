package com.storefront.batch.util;

/**
 * @author Praneeth.dodedu
 *
 */
public final class ApplicationConstants {

	public static final String EMPTY_STRING = "";

	public static final String CHAR_ENCODING = "UTF-8";

	public static final String HTTPS = "https";
	public static final String HTTP = "http";

	public static final String DATE_FORMATE_YYYY_MM_DDHH_MM_SS_SSS = "yyyy-MM-ddhh:mm:ss.SSS";
	public static final String PARAM_KEY_AUTHORIZATION = "Authorization";
	public static final String PARAM_VALUE_OAUTH = "oauth ";
	public static final String PARAM_KEY_CATALOG_ID = "catalogId";

	public static final String PROPERTY_KEY_ACP_CATALOG_ALL = "${acp.catalog.all}";
	public static final String PROPERTY_KEY_ACP_COURSE_ALL = "${acp.course.all}";
	public static final String PROPERTY_KEY_ACP_USERS_ALL = "${acp.users.all}";
	public static final String PROPERTY_KEY_ACP_ACCESS_TOKEN = "${acp.access.token}";
	public static final String PROPERTY_KEY_ACP_LEARNING_PROGRAM_ALL = "${acp.learning.program.all}";

	public static final String PATH_VARIABLE_MODULES = "modules";
	public static final String PATH_VARIABLE_COURSE_ENROLLMENT = "courseInstanceEnrollments";

	public static final String CATALOG_ID_DEFAULT = "_default";
	public static final String CHARACTER_KEY_FORWARD_SLASH = "/";

	private ApplicationConstants() {

	}

}
