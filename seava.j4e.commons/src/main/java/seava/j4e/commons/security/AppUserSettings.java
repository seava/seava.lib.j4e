/** 
 * DNet eBusiness Suite
 * Copyright: 2013 Nan21 Electronics SRL. All rights reserved.
 * Use is subject to license terms.
 */
package seava.j4e.commons.security;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import seava.j4e.api.Constants;
import seava.j4e.api.ISettings;
import seava.j4e.api.enums.DateFormatAttribute;
import seava.j4e.api.exceptions.InvalidConfiguration;
import seava.j4e.api.session.IUserSettings;

public class AppUserSettings implements IUserSettings, Serializable {

	private static final long serialVersionUID = -9131543374115237340L;

	private String language = Constants.DEFAULT_LANGUAGE;

	/**
	 * Date format masks to be used in java/extjs. The keys are the names in
	 * {@link DateFormatAttribute}.
	 */
	private Map<String, String> dateFormatMasks = new HashMap<String, String>();

	/**
	 * Date formats to be used in java. The keys are the names in
	 * {@link DateFormatAttribute}.
	 */
	private Map<String, SimpleDateFormat> dateFormats = new HashMap<String, SimpleDateFormat>();

	/**
	 * Pattern to highlight the separators: 0.000,00 or 0,000.00 etc
	 */
	private String numberFormat = Constants.DEFAULT_NUMBER_FORMAT;

	/**
	 * Derived from numberFormatMask
	 */
	private String decimalSeparator;

	/**
	 * Derived from numberFormatMask
	 */
	private String thousandSeparator;

	private AppUserSettings() {

	}

	/**
	 * Create a new instance using values from the provided application settings
	 * or defaults.
	 * 
	 * @param settings
	 * @return
	 * @throws InvalidConfiguration
	 */
	public static AppUserSettings newInstance(ISettings settings)
			throws InvalidConfiguration {

		AppUserSettings usr = new AppUserSettings();

		for (DateFormatAttribute a : DateFormatAttribute.values()) {
			if (settings != null) {
				usr.setDateFormatMask(a.name(),
						settings.get(a.getPropertyFileKey()));
			} else {
				usr.setDateFormatMask(a.name(), a.getDefaultValue());
			}
		}

		if (settings != null) {
			usr.setLanguage(settings.get(Constants.PROP_LANGUAGE));
			usr.setNumberFormat(settings.get(Constants.PROP_NUMBER_FORMAT));
		}

		return usr;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getNumberFormat() {
		return numberFormat;
	}

	public void setNumberFormat(String numberFormat)
			throws InvalidConfiguration {
		this.validateNumberFormat(numberFormat);
		this.numberFormat = numberFormat;
	}

	public String getDecimalSeparator() {
		if (this.decimalSeparator == null) {
			this.decimalSeparator = this.numberFormat.replace("0", "")
					.substring(1, 2);
		}
		return decimalSeparator;
	}

	public String getThousandSeparator() {
		if (this.thousandSeparator == null) {
			this.thousandSeparator = this.numberFormat.replace("0", "")
					.substring(0, 1);
		}
		return thousandSeparator;
	}

	public SimpleDateFormat getDateFormat(DateFormatAttribute dfa) {
		return this.getDateFormat(dfa.name());
	}

	public SimpleDateFormat getDateFormat(String key) {
		if (!this.dateFormats.containsKey(key)) {
			this.dateFormats.put(key,
					new SimpleDateFormat(this.dateFormatMasks.get(key)));
		}
		return this.dateFormats.get(key);
	}

	public String getDateFormatMask(String key) {
		return this.dateFormatMasks.get(key);
	}

	public void setDateFormatMask(String key, String value)
			throws InvalidConfiguration {
		if (this.dateFormatMasks == null) {
			this.dateFormatMasks = new HashMap<String, String>();
		}
		this.validateDateFormatKey(key);
		this.dateFormatMasks.put(key, value);
	}

	private void validateDateFormatKey(String key) throws InvalidConfiguration {
		if (DateFormatAttribute.valueOf(key) == null) {
			throw new InvalidConfiguration(key
					+ " is not an accepted date-format-mask.");
		}
	}

	private void validateNumberFormat(String key) throws InvalidConfiguration {

		if (key != null && key.length() == 8) {
			String k = key.replaceAll("0", "");
			if (k.length() == 2) {
				return;
			}
		}

		throw new InvalidConfiguration(
				key
						+ " is not an accepted number-format-mask. It should be a pattern similar to `0,000.00`  ");
	}
}
