/** 
 * DNet eBusiness Suite
 * Copyright: 2013 Nan21 Electronics SRL. All rights reserved.
 * Use is subject to license terms.
 */
package ro.seava.j4e.business.service.entity;

import ro.seava.j4e.api.service.business.IEntityService;

public class DefaultEntityService<E> extends AbstractEntityService<E> implements
		IEntityService<E> {

	private Class<E> entityClass;

	public DefaultEntityService(Class<E> entityClass) {
		this.entityClass = entityClass;
	}

	public static <E> DefaultEntityService<E> createService(Class<E> entityClass) {
		return new DefaultEntityService<E>(entityClass);
	}

	@Override
	public Class<E> getEntityClass() {
		return this.entityClass;
	}

}