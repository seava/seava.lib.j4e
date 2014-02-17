/** 
 * DNet eBusiness Suite
 * Copyright: 2013 Nan21 Electronics SRL. All rights reserved.
 * Use is subject to license terms.
 */
package seava.j4e.security;

import seava.j4e.api.security.IAuthorization;

public class DummyAuthorizationForJob implements IAuthorization {

	@Override
	public void authorize(String resourceName, String action, String rpcMethod)
			throws Exception {
		// If it doesn't throw exception is authorized
	}

}
