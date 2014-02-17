/** 
 * DNet eBusiness Suite
 * Copyright: 2013 Nan21 Electronics SRL. All rights reserved.
 * Use is subject to license terms.
 */
package seava.j4e.api.security;

/**
 * ThreadLocal variable holder used to send extra login parameters to the
 * authentication service.
 */
public class LoginParamsHolder {
	public static ThreadLocal<ILoginParams> params = new ThreadLocal<ILoginParams>();
}
