package seava.j4e.api.service;

import seava.j4e.api.exceptions.BusinessException;
import seava.j4e.api.service.business.IEntityService;

public interface IServiceLocatorBusiness {

	/**
	 * Find an entity service given the entity class.
	 * 
	 * @param <E>
	 * @param entityClass
	 * @return
	 * @throws Exception
	 */
	public abstract <E> IEntityService<E> findEntityService(Class<E> entityClass)
			throws BusinessException;

}