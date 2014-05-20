/** 
 * DNet eBusiness Suite
 * Copyright: 2013 Nan21 Electronics SRL. All rights reserved.
 * Use is subject to license terms.
 */
package seava.j4e.api.session;

import java.text.SimpleDateFormat;

import seava.j4e.api.enums.DateFormatAttribute;

public interface IUserSettings {

	public String getLanguage();

	public void setLanguage(String language);

	public String getNumberFormat();

	// public void setNumberFormat(String numberFormat)
	// throws InvalidConfiguration;

	public String getDecimalSeparator();

	public String getThousandSeparator();

	public String getDateFormatMask(String key);

	public SimpleDateFormat getDateFormat(DateFormatAttribute dfa);

	public SimpleDateFormat getDateFormat(String key);
}