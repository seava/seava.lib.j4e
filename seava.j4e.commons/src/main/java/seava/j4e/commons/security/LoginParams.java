/** 
 * DNet eBusiness Suite
 * Copyright: 2013 Nan21 Electronics SRL. All rights reserved.
 * Use is subject to license terms.
 */
package seava.j4e.commons.security;

import seava.j4e.api.security.ILoginParams;

public class LoginParams implements ILoginParams {
	/**
	 * Client to connect to.
	 */
	private String clientCode;

	/**
	 * Language.
	 */
	private String language;

	private String userAgent;

	private String remoteHost;
	private String remoteIp;

	/* (non-Javadoc)
	 * @see seava.j4e.commons.security.ILoginParams#getClientCode()
	 */
	@Override
	public String getClientCode() {
		return clientCode;
	}

	/* (non-Javadoc)
	 * @see seava.j4e.commons.security.ILoginParams#setClientCode(java.lang.String)
	 */
	@Override
	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}

	/* (non-Javadoc)
	 * @see seava.j4e.commons.security.ILoginParams#getLanguage()
	 */
	@Override
	public String getLanguage() {
		return language;
	}

	/* (non-Javadoc)
	 * @see seava.j4e.commons.security.ILoginParams#setLanguage(java.lang.String)
	 */
	@Override
	public void setLanguage(String language) {
		this.language = language;
	}

	/* (non-Javadoc)
	 * @see seava.j4e.commons.security.ILoginParams#getUserAgent()
	 */
	@Override
	public String getUserAgent() {
		return userAgent;
	}

	/* (non-Javadoc)
	 * @see seava.j4e.commons.security.ILoginParams#setUserAgent(java.lang.String)
	 */
	@Override
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	/* (non-Javadoc)
	 * @see seava.j4e.commons.security.ILoginParams#getRemoteHost()
	 */
	@Override
	public String getRemoteHost() {
		return remoteHost;
	}

	/* (non-Javadoc)
	 * @see seava.j4e.commons.security.ILoginParams#setRemoteHost(java.lang.String)
	 */
	@Override
	public void setRemoteHost(String remoteHost) {
		this.remoteHost = remoteHost;
	}

	/* (non-Javadoc)
	 * @see seava.j4e.commons.security.ILoginParams#getRemoteIp()
	 */
	@Override
	public String getRemoteIp() {
		return remoteIp;
	}

	/* (non-Javadoc)
	 * @see seava.j4e.commons.security.ILoginParams#setRemoteIp(java.lang.String)
	 */
	@Override
	public void setRemoteIp(String remoteIp) {
		this.remoteIp = remoteIp;
	}

}
