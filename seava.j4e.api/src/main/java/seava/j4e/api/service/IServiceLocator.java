package seava.j4e.api.service;

import seava.j4e.api.service.business.IAsgnTxService;
import seava.j4e.api.service.business.IEntityService;
import seava.j4e.api.service.presenter.IAsgnService;
import seava.j4e.api.service.presenter.IDsService;
import seava.j4e.api.service.presenter.IReportService;

public interface IServiceLocator {

	/**
	 * Find a report service given its spring bean alias.
	 * 
	 * @param reportServiceAlias
	 * @return
	 * @throws Exception
	 */
	public IReportService findReportService(String reportServiceAlias)
			throws Exception;

	/**
	 * Find a data-source service given the data-source name.
	 * 
	 * @param <M>
	 * @param <P>
	 * @param dsName
	 * @return
	 * @throws Exception
	 */
	public <M, F, P> IDsService<M, F, P> findDsService(String dsName)
			throws Exception;

	/**
	 * Find a data-source service given the data-source model class.
	 * 
	 * @param <M>
	 * @param <P>
	 * @param modelClass
	 * @return
	 * @throws Exception
	 */
	public <M, F, P> IDsService<M, F, P> findDsService(Class<?> modelClass)
			throws Exception;

	/**
	 * Find an entity service given the entity class.
	 * 
	 * @param <E>
	 * @param entityClass
	 * @return
	 * @throws Exception
	 */
	public <E> IEntityService<E> findEntityService(Class<E> entityClass)
			throws Exception;

	/**
	 * Find an assignment service given the service name.
	 * 
	 * @param <M>
	 * @param <P>
	 * @param asgnName
	 * @return
	 * @throws Exception
	 */
	public <M, F, P> IAsgnService<M, F, P> findAsgnService(String asgnName)
			throws Exception;

	/**
	 * Find an business assignment service given the service name and factory
	 * name.
	 * 
	 * @param <M>
	 * @param <P>
	 * @param asgnName
	 * @return
	 * @throws Exception
	 */
	public <E> IAsgnTxService<E> findAsgnTxService(String asgnName,
			String factoryName) throws Exception;

	/**
	 * Find an business assignment service given the service name.
	 * 
	 * @param <M>
	 * @param <P>
	 * @param asgnName
	 * @return
	 * @throws Exception
	 */
	public <E> IAsgnTxService<E> findAsgnTxService(String asgnName)
			throws Exception;

}