package seava.j4e.api.enums;

import seava.j4e.api.Constants;

public enum SysParam {

	CORE_LOGO_URL_EXTJS(
			"Application logo URL - Extjs",
			"Link to the company logo to be displayed in the Extjs based application header.",
			Constants.DATA_TYPE_STRING, "resources/images/logo.png", null),

	CORE_LOGO_URL_REPORT("Report logo URL",
			"Link to the company logo to be displayed in the reports.",
			Constants.DATA_TYPE_STRING, "resources/images/logo.png", null),

	CORE_SESSION_CHECK_IP(
			"Check request IP",
			"Check if the remote client IP of the request is the same as the one used at login time. Possible values: true, false.",
			Constants.DATA_TYPE_BOOLEAN, "false", null),

	CORE_SESSION_CHECK_USER_AGENT(
			"Check request user-agent",
			"Check if the remote client user-agent of the request is the same as the one used at login time. Possible values: true, false.",
			Constants.DATA_TYPE_BOOLEAN, "false", null),

	CORE_EXP_HTML_CSS("Css file location for HTML export",
			"Css used to style the HTML exports from grid.",
			Constants.DATA_TYPE_STRING, "resources/css/export-html.css", null),

	CORE_PRINT_HTML_TPL(
			"Template file location FreeMarker",
			"Default template used when printing in html format with FreeMarker.",
			Constants.DATA_TYPE_STRING, null, null),

	CORE_DEFAULT_LANGUAGE(
			"Default language",
			"Default language to be used if no language preference set by user.",
			Constants.DATA_TYPE_STRING, "en", null),

	CORE_DEFAULT_THEME_EXTJS(
			"Default theme Extjs",
			"Default theme to be used in Extjs based user interface if no theme preference set by user.",
			Constants.DATA_TYPE_STRING, "dnet-theme-aqua", ""),

	CORE_JOB_USER("Scheduled job user",
			"Generic user to execute scheduled jobs",
			Constants.DATA_TYPE_STRING, "JOB_PROCESS", "");

	private String title;
	private String description;
	private String dataType;
	private String defaultValue;
	private String listOfValues;

	private SysParam(String title, String description, String dataType,
			String defaultValue, String listOfValues) {

		this.title = title;
		this.description = description;
		this.dataType = dataType;
		this.defaultValue = defaultValue;
		this.listOfValues = listOfValues;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public String getDataType() {
		return dataType;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public String getListOfValues() {
		return listOfValues;
	}

}
