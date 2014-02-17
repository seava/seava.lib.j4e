/** 
 * DNet eBusiness Suite
 * Copyright: 2013 Nan21 Electronics SRL. All rights reserved.
 * Use is subject to license terms.
 */
package seava.j4e.commons;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import seava.j4e.api.Constants;
import seava.j4e.api.ISettings;
import seava.j4e.api.descriptor.ISysParamDefinition;
import seava.j4e.api.descriptor.ISysParamDefinitions;
import seava.j4e.api.exceptions.InvalidConfiguration;
import seava.j4e.api.exceptions.InvalidDatabase;
import seava.j4e.api.service.ISysParamValueProvider;
import seava.j4e.api.session.Session;

public class Settings implements ISettings, ApplicationContextAware {

	private ApplicationContext applicationContext;

	/**
	 * System properties defined in the application properties file.
	 */
	private final Map<String, String> properties;

	/**
	 * List of system parameter definitions. Each business module can declare
	 * and export system-parameter definitions. All of them are collected in
	 * this list.
	 * 
	 */
	private List<ISysParamDefinitions> paramDefinitions;

	/**
	 * System parameters default values. Provided at startup
	 */
	private Map<String, String> paramDefaultValues;

	/**
	 * Client level values for system parameters.
	 */
	private Map<String, Map<String, String>> paramValues;

	/**
	 * Product-name.
	 */
	private String productName;

	/**
	 * Product-description.
	 */
	private String productDescription;

	/**
	 * Product-vendor
	 */
	private String productVendor;

	/**
	 * Product-url
	 */
	private String productUrl;

	/**
	 * Product-version
	 */
	private String productVersion;

	public Settings(Map<String, String> properties) {

		this.properties = properties;

		Constants.set_server_date_format(this.properties
				.get(Constants.PROP_SERVER_DATE_FORMAT));
		Constants.set_server_time_format(this.properties
				.get(Constants.PROP_SERVER_TIME_FORMAT));
		Constants.set_server_datetime_format(this.properties
				.get(Constants.PROP_SERVER_DATETIME_FORMAT));
		Constants.set_server_alt_formats(this.properties
				.get(Constants.PROP_SERVER_ALT_FORMATS));
	}

	@Override
	public String get(String key) {
		return this.properties.get(key);
	}

	@Override
	public boolean getAsBoolean(String key) {
		return Boolean.valueOf(this.get(key));
	}

	@Override
	public String getParam(String paramName) throws InvalidConfiguration {
		checkParam(paramName);
		checkParamValue(paramName);
		if (Session.user.get() != null) {
			String clientId = Session.user.get().getClientId();

			if (clientId != null && !"".equals(clientId)
					&& this.paramValues.get(clientId).containsKey(paramName)) {
				return this.paramValues.get(clientId).get(paramName);
			} else {
				return this.paramDefaultValues.get(paramName);
			}
		}
		return this.paramDefaultValues.get(paramName);
	}

	@Override
	public boolean getParamAsBoolean(String paramName)
			throws InvalidConfiguration {
		return Boolean.valueOf(getParam(paramName));
	}

	@Override
	public void reloadParams() throws Exception {
		this.populateParams();
	}

	@Override
	public void reloadParamValues() throws Exception {
		try {
			this.populateParamValues();
		} catch (InvalidConfiguration e) {
			throw new Exception(e);
		}
	}

	private void checkParam(String paramName) throws InvalidConfiguration {
		if (this.paramDefaultValues == null) {
			this.populateParams();
		}
		if (!this.paramDefaultValues.containsKey(paramName)) {
			// maybe it's a bundle plugged in after first populate ...
			// populate it once again
			this.populateParams();
			if (!this.paramDefaultValues.containsKey(paramName)) {
				throw new InvalidConfiguration("Parameter `" + paramName
						+ "` has no value defined");
			}
		}
	}

	private void checkParamValue(String paramName) throws InvalidConfiguration {
		if (Session.user.get() != null) {
			String clientId = Session.user.get().getClientId();
			if (this.paramValues == null
					|| (clientId != null && !"".equals(clientId) && this.paramValues
							.get(clientId) == null)) {
				this.populateParamValues();
			}
		}
	}

	private void populateParams() {
		this.paramDefaultValues = new HashMap<String, String>();
		for (ISysParamDefinitions pd : this.getParamDefinitions()) {
			for (ISysParamDefinition p : pd.getSysParamDefinitions()) {
				String name = p.getName();
				this.paramDefaultValues.put(name, p.getDefaultValue());
			}
		}
	}

	private void populateParamValues() throws InvalidConfiguration {
		if (this.paramValues == null) {
			this.paramValues = new HashMap<String, Map<String, String>>();
		}

		if (Session.user.get() != null
				&& Session.user.get().getClientId() != null
				&& !"".equals(Session.user.get().getClientId())) {
			this.paramValues.put(Session.user.get().getClientId(),
					new HashMap<String, String>());
			try {
				Map<String, String> values = this.applicationContext.getBean(
						ISysParamValueProvider.class)
						.getParamValues(new Date());

				this.paramValues.put(Session.user.get().getClientId(), values);
			} catch (InvalidDatabase e) {
				// TODO: set a flag to force a setup
			} catch (Exception e) {
				throw new InvalidConfiguration(e.getMessage(), e);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public List<ISysParamDefinitions> getParamDefinitions() {
		if (this.paramDefinitions == null) {
			this.paramDefinitions = (List<ISysParamDefinitions>) this.applicationContext
					.getBean(Constants.SPRING_OSGI_SYSPARAM_DEFINITIONS);
			if (this.get(Constants.PROP_DEPLOYMENT).equals(
					Constants.PROP_DEPLOYMENT_JEE)) {
				// TODO: use a flag to check if already populated
				Map<String, ISysParamDefinitions> beans = this.applicationContext
						.getBeansOfType(ISysParamDefinitions.class);
				for (ISysParamDefinitions b : beans.values()) {
					this.paramDefinitions.add(b);
				}
			}
		}
		return this.paramDefinitions;
	}

	public void setParamDefinitions(List<ISysParamDefinitions> paramDefinitions) {
		this.paramDefinitions = paramDefinitions;
	}

	public Map<String, String> getParams() {
		return paramDefaultValues;
	}

	public void setParams(Map<String, String> params) {
		this.paramDefaultValues = params;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public String getProductVendor() {
		return productVendor;
	}

	public void setProductVendor(String productVendor) {
		this.productVendor = productVendor;
	}

	public String getProductUrl() {
		return productUrl;
	}

	public void setProductUrl(String productUrl) {
		this.productUrl = productUrl;
	}

	public String getProductVersion() {
		return productVersion;
	}

	public void setProductVersion(String productVersion) {
		this.productVersion = productVersion;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

}
