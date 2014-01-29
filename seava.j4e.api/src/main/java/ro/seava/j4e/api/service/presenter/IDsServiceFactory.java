/** 
 * DNet eBusiness Suite
 * Copyright: 2013 Nan21 Electronics SRL. All rights reserved.
 * Use is subject to license terms.
 */
package ro.seava.j4e.api.service.presenter;

public interface IDsServiceFactory {
	
	public String getName();
	public <M,F,P> IDsService<M,F,P> create(String key);
}
