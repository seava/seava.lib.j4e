/** 
 * DNet eBusiness Suite
 * Copyright: 2013 Nan21 Electronics SRL. All rights reserved.
 * Use is subject to license terms.
 */
package seava.j4e.api.model;

/**
 * Interface to be implemented by all models(entities and view-objects) which
 * have an <code>id</code> primary key.
 * 
 * @author amathe
 * 
 */
public interface IModelWithId<T> {

	public T getId();

	public void setId(T id);
}
