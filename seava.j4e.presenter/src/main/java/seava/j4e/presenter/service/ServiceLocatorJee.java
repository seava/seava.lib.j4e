/** 
 * DNet eBusiness Suite
 * Copyright: 2013 Nan21 Electronics SRL. All rights reserved.
 * Use is subject to license terms.
 */
package seava.j4e.presenter.service;

import seava.j4e.api.service.IServiceLocator;
import seava.j4e.api.service.business.IAsgnTxService;
import seava.j4e.api.service.business.IEntityService;
import seava.j4e.api.service.presenter.IAsgnService;
import seava.j4e.api.service.presenter.IDsService;
import seava.j4e.api.service.presenter.IReportService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Service locator utility methods.
 * 
 * @author amathe
 */
public class ServiceLocatorJee implements ApplicationContextAware,
		IServiceLocator {

	final static Logger logger = LoggerFactory
			.getLogger(ServiceLocatorJee.class);

	private ApplicationContext applicationContext;

	@Override
	public <M, F, P> IDsService<M, F, P> findDsService(Class<?> modelClass)
			throws Exception {
		return this.findDsService(modelClass.getSimpleName());
	}

	/**
	 * Find a data-source service given the data-source name
	 * 
	 * @param <M>
	 * @param <P>
	 * @param dsName
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <M, F, P> IDsService<M, F, P> findDsService(String dsName)
			throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("Looking for ds-service `" + dsName + "`");
		}
		IDsService<M, F, P> srv = (IDsService<M, F, P>) this.applicationContext
				.getBean(dsName);
		if (srv == null && this.applicationContext.getParent() != null) {
			srv = (IDsService<M, F, P>) this.applicationContext.getParent()
					.getBean(dsName);
		}

		if (srv == null) {
			throw new Exception("Ds-service `" + dsName + "` not found !");
		}
		return srv;
	}

	/**
	 * Find a report service given its spring bean alias factories.
	 * 
	 * @param reportServiceAlias
	 * @return
	 * @throws Exception
	 */
	@Override
	public IReportService findReportService(String reportServiceAlias)
			throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("Looking for ds-service `" + reportServiceAlias + "`");
		}
		IReportService srv = (IReportService) this.applicationContext
				.getBean(reportServiceAlias);
		if (srv == null && this.applicationContext.getParent() != null) {
			srv = (IReportService) this.applicationContext.getParent().getBean(
					reportServiceAlias);
		}

		if (srv == null) {
			throw new Exception("Report-service `" + reportServiceAlias
					+ "` not found !");
		}
		return srv;
	}

	/**
	 * Find an entity service given the entity class and a list of factories.
	 * 
	 * @param <E>
	 * @param entityClass
	 * @param factories
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <E> IEntityService<E> findEntityService(Class<E> entityClass)
			throws Exception {
		String serviceAlias = entityClass.getSimpleName();
		if (logger.isDebugEnabled()) {
			logger.debug("Looking for entity-service `" + serviceAlias + "`");
		}

		IEntityService<E> srv = (IEntityService<E>) this.applicationContext
				.getBean(serviceAlias);
		if (srv == null && this.applicationContext.getParent() != null) {
			srv = (IEntityService<E>) this.applicationContext.getParent()
					.getBean(serviceAlias);
		}

		if (srv == null) {
			throw new Exception("Entity service `" + serviceAlias
					+ "` not found");
		}
		return srv;
	}

	/**
	 * Find an assignment service given the service name and the list of
	 * factories.
	 * 
	 * @param <M>
	 * @param <P>
	 * @param asgnName
	 * @param factories
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <M, F, P> IAsgnService<M, F, P> findAsgnService(String asgnName)
			throws Exception {

		if (logger.isDebugEnabled()) {
			logger.debug("Looking for assignment service `" + asgnName + "`");
		}

		IAsgnService<M, F, P> srv = (IAsgnService<M, F, P>) this.applicationContext
				.getBean(asgnName);
		if (srv == null && this.applicationContext.getParent() != null) {
			srv = (IAsgnService<M, F, P>) this.applicationContext.getParent()
					.getBean(asgnName);
		}

		if (srv == null) {
			throw new Exception("Assignment service `" + asgnName
					+ "` not found !");
		}
		return srv;
	}

	@Override
	public <E> IAsgnTxService<E> findAsgnTxService(String asgnName)
			throws Exception {

		return this.findAsgnTxService(asgnName, null);
	}

	/**
	 * Find an business assignment service given the service name and the list
	 * of factories.
	 * 
	 * @param <M>
	 * @param <P>
	 * @param asgnName
	 * @param factories
	 * @return
	 * @throws Exception
	 */

	@SuppressWarnings("unchecked")
	@Override
	public <E> IAsgnTxService<E> findAsgnTxService(String asgnName,
			String factoryName) throws Exception {

		if (logger.isDebugEnabled()) {
			logger.debug("Looking for business assignment service `" + asgnName
					+ "`");
		}

		IAsgnTxService<E> srv = (IAsgnTxService<E>) this.applicationContext
				.getBean(asgnName, IAsgnTxService.class);
		if (srv == null && this.applicationContext.getParent() != null) {
			srv = (IAsgnTxService<E>) this.applicationContext.getParent()
					.getBean(asgnName, IAsgnTxService.class);
		}

		if (srv == null) {
			throw new Exception("Business assignment service `" + asgnName
					+ "` not found !");
		}
		return srv;
	}

	/**
	 * Getter for the spring application context.
	 * 
	 * @return
	 */
	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * Setter for the spring application context.
	 */
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

}
