package com.learnvest.checklistapi;

/**
 * @author Jerome Simmonds
 *
 */
public class ChecklistApiConstants {

	public static final String APPLICATION_PATH = "/v1";
	
	public static final String ROLE_USER = "ROLE_USER";
	public static final String ROLE_ADMIN = "ROLE_ADMIN";
	
	public static final String SPACE = " ";
	public static final String EMPTY = "";
	public static final String DOT = ".";
	public static final String DASH = "-";
	public static final String SLASH = "/";
	public static final String COLON = ":" ;
	public static final String COMMA = ",";
	public static final String BRACE_OPEN = "{";
	public static final String BRACE_CLOSE = "}";
	public static final String LINE_SEPARATOR = "line.separator";
	public static final String NEW_LINE = System.getProperty(LINE_SEPARATOR);

	public static final String STATUS = "status";
	public static final String ERROR = "error";
	public static final String ERRORS = "errors";
	public static final String CODE = "code";
	public static final String MESSAGE = "message";
	public static final String WARNING = "warning";
	public static final String DATA = "data";
	public static final String METADATA = "metadata";
	public static final String PAGE = "page";
	public static final String PAGE_SIZE = "pageSize";
	public static final String PATH = "path";
	public static final String ID = "id";
	public static final String USER_ID = "userId";
	public static final String CHECKLIST_ID = "checklistId";
	public static final String USERNAME = "username";
	public static final String NAME = "name";
	public static final String DESCRIPTION = "description";
	public static final String COMPLETE = "complete";
	public static final String DUE_DATE = "dueDate";
	
	public static final String PATH_PARAM_ID = "/{id:\\d+}";

	public static final String DATE_PATTERN_ISO8601 = "yyyy-MM-dd'T'HH:mm:ss"; // DateTimeFormatter.ISO_LOCAL_DATE_TIME
}
