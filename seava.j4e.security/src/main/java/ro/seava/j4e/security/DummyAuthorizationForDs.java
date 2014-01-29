/** 
 * DNet eBusiness Suite
 * Copyright: 2013 Nan21 Electronics SRL. All rights reserved.
 * Use is subject to license terms.
 */
package ro.seava.j4e.security;

import ro.seava.j4e.api.security.IAuthorization;

public class DummyAuthorizationForDs implements IAuthorization {

	public void authorize(String dsName, String action, String rpcMethod)
			throws Exception {
		// If it doesn't throw exception is authorized
	}

}
