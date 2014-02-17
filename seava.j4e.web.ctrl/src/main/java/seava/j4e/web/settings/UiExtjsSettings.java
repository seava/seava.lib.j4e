/** 
 * DNet eBusiness Suite
 * Copyright: 2013 Nan21 Electronics SRL. All rights reserved.
 * Use is subject to license terms.
 */
package seava.j4e.web.settings;

/**
 * Extjs based user-interface settings. These properties are populated from the
 * system properties specified in the application configuration file.
 * 
 * @author amathe
 * 
 */
public class UiExtjsSettings {

	public static final String CACHE_FOLDER_DEFVAL = "~/dnet-ebs/cache/extjs";

	/**
	 * URL of Extjs library
	 */
	private String urlLib;

	/**
	 * Root URL of the core framework
	 */
	private String urlCore;

	/**
	 * Root URL of the core translations
	 */
	private String urlCoreI18n;

	/**
	 * Root URL of the modules components
	 */
	private String urlModules;

	/**
	 * Token for the module components within the bundle.
	 */
	private String moduleSubpath;

	/**
	 * Include component bundle in url?
	 */
	private boolean moduleUseBundle;

	/**
	 * Root URL of the themes for Extjs
	 */
	private String urlThemes;

	/**
	 * older to store the packed dependencies
	 */
	private String cacheFolder;

	public UiExtjsSettings() {
		this.setCacheFolder(CACHE_FOLDER_DEFVAL);
	}

	public String getUrlLib() {
		return urlLib;
	}

	public void setUrlLib(String urlLib) {
		this.urlLib = urlLib;
	}

	public String getUrlCore() {
		return urlCore;
	}

	public void setUrlCore(String urlCore) {
		this.urlCore = urlCore;
	}

	public String getUrlCoreI18n() {
		return urlCoreI18n;
	}

	public void setUrlCoreI18n(String urlCoreI18n) {
		this.urlCoreI18n = urlCoreI18n;
	}

	public String getUrlModules() {
		return urlModules;
	}

	public void setUrlModules(String urlModules) {
		this.urlModules = urlModules;
	}

	public String getModuleSubpath() {
		return moduleSubpath;
	}

	public void setModuleSubpath(String moduleSubpath) {
		this.moduleSubpath = moduleSubpath;
	}

	public String getUrlThemes() {
		return urlThemes;
	}

	public void setUrlThemes(String urlThemes) {
		this.urlThemes = urlThemes;
	}

	public String getCacheFolder() {
		return cacheFolder;
	}

	public void setCacheFolder(String cacheFolder) {
		if (cacheFolder.startsWith("~")) {
			this.cacheFolder = System.getProperty("user.home")
					+ cacheFolder.replaceFirst("~", "");
		} else {
			this.cacheFolder = cacheFolder;
		}

	}

	public boolean isModuleUseBundle() {
		return moduleUseBundle;
	}

	public void setModuleUseBundle(boolean moduleUseBundle) {
		this.moduleUseBundle = moduleUseBundle;
	}

}
