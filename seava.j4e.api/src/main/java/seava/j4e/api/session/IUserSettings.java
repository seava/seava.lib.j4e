/** 
 * DNet eBusiness Suite
 * Copyright: 2013 Nan21 Electronics SRL. All rights reserved.
 * Use is subject to license terms.
 */
package seava.j4e.api.session;

public interface IUserSettings {

	public String getLanguage();

	public void setLanguage(String language);

	public String getNumberFormat();

	// public void setNumberFormat(String numberFormat)
	// throws InvalidConfiguration;

	public String getDecimalSeparator();

	public String getThousandSeparator();

	public String getDateFormat(String key);
}