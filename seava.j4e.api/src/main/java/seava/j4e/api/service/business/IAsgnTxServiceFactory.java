/** 
 * DNet eBusiness Suite
 * Copyright: 2013 Nan21 Electronics SRL. All rights reserved.
 * Use is subject to license terms.
 */
package seava.j4e.api.service.business;

public interface IAsgnTxServiceFactory {
	public <E> IAsgnTxService<E> create(String key);

	public <E> IAsgnTxService<E> create(Class<E> type);

	public String getName();

	public void setName(String name);
}
