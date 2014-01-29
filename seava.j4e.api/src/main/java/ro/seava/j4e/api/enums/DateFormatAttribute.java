package ro.seava.j4e.api.enums;

import ro.seava.j4e.api.Constants;

/**
 * Attributes which define patterns used for date-time handling in different
 * layers (Java/Javascript). For each date-format mask proper values must be
 * provided for each of these attributes.
 * 
 * @author amathe
 * 
 */
public enum DateFormatAttribute {

	// Extjs

	EXTJS_DATE_FORMAT("Date format in Extjs", Constants.PROP_EXTJS_DATE_FORMAT,
			"Y-m-d", false, true),

	EXTJS_TIME_FORMAT("Time format in Extjs", Constants.PROP_EXTJS_TIME_FORMAT,
			"H:i", false, true),

	EXTJS_DATETIME_FORMAT("Date-time format in Extjs",
			Constants.PROP_EXTJS_DATETIME_FORMAT, "Y-m-d H:i", false, true),

	EXTJS_DATETIMESEC_FORMAT("Date-time-second format in Extjs",
			Constants.PROP_EXTJS_DATETIMESEC_FORMAT, "Y-m-d H:i:s", false, true),

	EXTJS_MONTH_FORMAT("Month format in Extjs",
			Constants.PROP_EXTJS_MONTH_FORMAT, "Y-m", false, true),

	EXTJS_ALT_FORMATS("Alternative date formats in Extjs",
			Constants.PROP_EXTJS_ALT_FORMATS, "j|j-n|d|d-m", false, true),

	// Java

	JAVA_DATE_FORMAT("Date format in Java", Constants.PROP_JAVA_DATE_FORMAT,
			"yyyy-MM-dd", true, false),

	JAVA_TIME_FORMAT("Time format in Java", Constants.PROP_JAVA_TIME_FORMAT,
			"HH:mm", true, false),

	JAVA_DATETIME_FORMAT("Date-time format in Java",
			Constants.PROP_JAVA_DATETIME_FORMAT, "yyyy-MM-dd HH:mm", true,
			false),

	JAVA_DATETIMESEC_FORMAT("Date-time-second format in Java",
			Constants.PROP_JAVA_DATETIMESEC_FORMAT, "yyyy-MM-dd HH:mm:ss",
			true, false),

	JAVA_MONTH_FORMAT("Month format in Java", Constants.PROP_JAVA_MONTH_FORMAT,
			"yyyy-MM", true, false);

	private String description;
	private String propertyFileKey;
	private String defaultValue;
	private boolean forJava;
	private boolean forJs;

	private DateFormatAttribute(String description, String propertyFileKey,
			String defaultValue, boolean forJava, boolean forJs) {

		this.propertyFileKey = propertyFileKey;
		this.description = description;
		this.defaultValue = defaultValue;

		this.forJava = forJava;
		this.forJs = forJs;

	}

	public String getPropertyFileKey() {
		return propertyFileKey;
	}

	public String getDescription() {
		return description;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public boolean isForJava() {
		return forJava;
	}

	public boolean isForJs() {
		return forJs;
	}

}
