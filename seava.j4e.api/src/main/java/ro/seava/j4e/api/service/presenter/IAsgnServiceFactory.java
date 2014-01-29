/** 
 * DNet eBusiness Suite
 * Copyright: 2013 Nan21 Electronics SRL. All rights reserved.
 * Use is subject to license terms.
 */
package ro.seava.j4e.api.service.presenter;

public interface IAsgnServiceFactory {

	public <M, F, P> IAsgnService<M, F, P> create(String key);
}
