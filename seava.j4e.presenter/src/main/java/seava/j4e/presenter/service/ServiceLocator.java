/** 
 * DNet eBusiness Suite
 * Copyright: 2013 Nan21 Electronics SRL. All rights reserved.
 * Use is subject to license terms.
 */
package seava.j4e.presenter.service;

import java.util.List;

import seava.j4e.api.Constants;
import seava.j4e.api.service.IServiceLocator;
import seava.j4e.api.service.business.IAsgnTxService;
import seava.j4e.api.service.business.IAsgnTxServiceFactory;
import seava.j4e.api.service.business.IEntityService;
import seava.j4e.api.service.business.IEntityServiceFactory;
import seava.j4e.api.service.presenter.IAsgnService;
import seava.j4e.api.service.presenter.IAsgnServiceFactory;
import seava.j4e.api.service.presenter.IDsService;
import seava.j4e.api.service.presenter.IDsServiceFactory;
import seava.j4e.api.service.presenter.IReportService;
import seava.j4e.api.service.presenter.IReportServiceFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Service locator utility methods.
 * 
 * @author amathe
 */
public class ServiceLocator implements ApplicationContextAware, IServiceLocator {

	final static Logger logger = LoggerFactory.getLogger(ServiceLocator.class);

	private ApplicationContext applicationContext;

	private List<IEntityServiceFactory> entityServiceFactories;

	private List<IDsServiceFactory> dsServiceFactories;

	private List<IAsgnServiceFactory> asgnServiceFactories;

	private List<IAsgnTxServiceFactory> asgnTxServiceFactories;

	private List<IReportServiceFactory> reportServiceFactories;

	@Override
	public IReportService findReportService(String reportServiceAlias)
			throws Exception {
		return this.findReportService(reportServiceAlias,
				this.getReportServiceFactories());
	}

	/**
	 * Find a data-source service given the data-source name and a list of
	 * factories.
	 * 
	 * @param <M>
	 * @param <P>
	 * @param dsName
	 * @param factories
	 * @return
	 * @throws Exception
	 */
	public IReportService findReportService(String reportServiceAlias,
			List<IReportServiceFactory> factories) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("Looking for ds-service `" + reportServiceAlias + "`");
		}
		IReportService srv = null;
		for (IReportServiceFactory f : factories) {
			try {
				srv = f.create(reportServiceAlias);
				if (srv != null) {
					if (logger.isDebugEnabled()) {
						logger.debug("Ds-service `" + reportServiceAlias
								+ "` found in factory " + f.toString());
					}
					return srv;
				}
			} catch (NoSuchBeanDefinitionException e) {
				// service not found in this factory, ignore
			}
		}
		throw new Exception("Ds-service `" + reportServiceAlias
				+ "` not found !");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * seava.j4e.presenter.service.IServiceLocator#findDsService(java.lang.String
	 * )
	 */
	@Override
	public <M, F, P> IDsService<M, F, P> findDsService(String dsName)
			throws Exception {
		return this.findDsService(dsName, this.getDsServiceFactories());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * seava.j4e.presenter.service.IServiceLocator#findDsService(java.lang.Class
	 * )
	 */
	@Override
	public <M, F, P> IDsService<M, F, P> findDsService(Class<?> modelClass)
			throws Exception {
		return this.findDsService(modelClass.getSimpleName(),
				this.getDsServiceFactories());
	}

	/**
	 * Find a data-source service given the data-source name and a list of
	 * factories.
	 * 
	 * @param <M>
	 * @param <P>
	 * @param dsName
	 * @param factories
	 * @return
	 * @throws Exception
	 */
	public <M, F, P> IDsService<M, F, P> findDsService(String dsName,
			List<IDsServiceFactory> factories) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("Looking for ds-service `" + dsName + "`");
		}
		IDsService<M, F, P> srv = null;
		for (IDsServiceFactory f : factories) {
			try {
				srv = f.create(dsName);
				if (srv != null) {
					if (logger.isDebugEnabled()) {
						logger.debug("Ds-service `" + dsName
								+ "` found in factory " + f.toString());
					}
					return srv;
				}
			} catch (NoSuchBeanDefinitionException e) {
				// service not found in this factory, ignore
			}
		}
		throw new Exception("Ds-service `" + dsName + "` not found !");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * seava.j4e.presenter.service.IServiceLocator#findEntityService(java.lang
	 * .Class)
	 */
	@Override
	public <E> IEntityService<E> findEntityService(Class<E> entityClass)
			throws Exception {
		return this.findEntityService(entityClass,
				this.getEntityServiceFactories());
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
	public <E> IEntityService<E> findEntityService(Class<E> entityClass,
			List<IEntityServiceFactory> factories) throws Exception {
		String serviceAlias = entityClass.getSimpleName();
		if (logger.isDebugEnabled()) {
			logger.debug("Looking for entity-service `" + serviceAlias + "`");
		}
		for (IEntityServiceFactory esf : factories) {
			try {
				IEntityService<E> srv = esf.create(serviceAlias);
				if (srv != null) {
					if (logger.isDebugEnabled()) {
						logger.debug("Entity-service `" + serviceAlias
								+ "` found in factory " + esf.toString());
					}
					return srv;
				}
			} catch (NoSuchBeanDefinitionException e) {
				// service not found in this factory, ignore
			}
		}
		throw new Exception("Entity service `" + serviceAlias + "` not found");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * seava.j4e.presenter.service.IServiceLocator#findAsgnService(java.lang
	 * .String)
	 */
	@Override
	public <M, F, P> IAsgnService<M, F, P> findAsgnService(String asgnName)
			throws Exception {
		return this.findAsgnService(asgnName, this.getAsgnServiceFactories());
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
	public <M, F, P> IAsgnService<M, F, P> findAsgnService(String asgnName,
			List<IAsgnServiceFactory> factories) throws Exception {

		if (logger.isDebugEnabled()) {
			logger.debug("Looking for assignment service `" + asgnName + "`");
		}

		IAsgnService<M, F, P> srv = null;
		for (IAsgnServiceFactory f : factories) {
			try {
				srv = f.create(asgnName);
				if (srv != null) {
					if (logger.isDebugEnabled()) {
						logger.debug("Assignment-service `" + asgnName
								+ "` found in factory " + f.toString());
					}
					return srv;
				}
			} catch (NoSuchBeanDefinitionException e) {
				// service not found in this factory, ignore
			}
		}
		throw new Exception("Assignment service `" + asgnName + "` not found !");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * seava.j4e.presenter.service.IServiceLocator#findAsgnTxService(java.lang
	 * .String, java.lang.String)
	 */
	@Override
	public <E> IAsgnTxService<E> findAsgnTxService(String asgnName,
			String factoryName) throws Exception {
		return this.findAsgnTxService(asgnName, factoryName,
				this.getAsgnTxServiceFactories());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * seava.j4e.presenter.service.IServiceLocator#findAsgnTxService(java.lang
	 * .String)
	 */
	@Override
	public <E> IAsgnTxService<E> findAsgnTxService(String asgnName)
			throws Exception {
		return this.findAsgnTxService(asgnName, null,
				this.getAsgnTxServiceFactories());
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
	public <E> IAsgnTxService<E> findAsgnTxService(String asgnName,
			String factoryName, List<IAsgnTxServiceFactory> factories)
			throws Exception {

		if (logger.isDebugEnabled()) {
			logger.debug("Looking for business assignment service `" + asgnName
					+ "`");
		}

		IAsgnTxService<E> srv = null;

		if (factoryName == null || "".equals(factoryName)) {
			for (IAsgnTxServiceFactory f : factories) {
				try {
					srv = f.create(asgnName);
					if (srv != null) {
						if (logger.isDebugEnabled()) {
							logger.debug("Business assignment-service `"
									+ asgnName + "` found in factory "
									+ f.getName() + " / " + f.toString());
						}
						return srv;
					}
				} catch (NoSuchBeanDefinitionException e) {
					// service not found in this factory, ignore
				}
			}
		} else {
			for (IAsgnTxServiceFactory f : factories) {
				if (factoryName.equals(f.getName())) {
					srv = f.create(asgnName);
					if (srv != null) {
						if (logger.isDebugEnabled()) {
							logger.debug("Business assignment-service `"
									+ asgnName + "` found in factory "
									+ f.getName() + " / " + f.toString());
						}
						return srv;
					}
				}
			}
		}

		throw new Exception("Business assignment service `" + asgnName
				+ "` not found !");
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

	/**
	 * Get entity service factories. If it is null attempts to retrieve it from
	 * Spring context by <code>osgiEntityServiceFactories</code> alias.
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<IEntityServiceFactory> getEntityServiceFactories() {
		if (this.entityServiceFactories == null) {
			this.entityServiceFactories = (List<IEntityServiceFactory>) this
					.getApplicationContext().getBean(
							Constants.SPRING_OSGI_ENTITY_SERVICE_FACTORIES);
		}
		return this.entityServiceFactories;
	}

	/**
	 * Set entity service factories
	 * 
	 * @param entityServiceFactories
	 */
	public void setEntityServiceFactories(
			List<IEntityServiceFactory> entityServiceFactories) {
		this.entityServiceFactories = entityServiceFactories;
	}

	/**
	 * Get data-source service factories. If it is null attempts to retrieve it
	 * from Spring context by <code>osgiDsServiceFactories</code> alias.
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<IDsServiceFactory> getDsServiceFactories() {
		if (this.dsServiceFactories == null) {
			this.dsServiceFactories = (List<IDsServiceFactory>) this
					.getApplicationContext().getBean(
							Constants.SPRING_OSGI_DS_SERVICE_FACTORIES);
		}
		return this.dsServiceFactories;
	}

	/**
	 * Set data-source service factories.
	 * 
	 * @param dsServiceFactories
	 */
	public void setDsServiceFactories(List<IDsServiceFactory> dsServiceFactories) {
		this.dsServiceFactories = dsServiceFactories;
	}

	/**
	 * Get assignment factories. If it is null attempts to retrieve it from
	 * Spring context by <code>osgiAsgnServiceFactories</code> alias.
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<IAsgnServiceFactory> getAsgnServiceFactories() {
		if (this.asgnServiceFactories == null) {
			this.asgnServiceFactories = (List<IAsgnServiceFactory>) this
					.getApplicationContext().getBean(
							Constants.SPRING_OSGI_ASGN_SERVICE_FACTORIES);
		}
		return asgnServiceFactories;
	}

	/**
	 * Set assignment factories.
	 * 
	 * @param asgnServiceFactories
	 */
	public void setAsgnServiceFactories(
			List<IAsgnServiceFactory> asgnServiceFactories) {
		this.asgnServiceFactories = asgnServiceFactories;
	}

	/**
	 * Get assignment factories. If it is null attempts to retrieve it from
	 * Spring context by <code>osgiAsgnServiceFactories</code> alias.
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<IAsgnTxServiceFactory> getAsgnTxServiceFactories() {
		if (this.asgnTxServiceFactories == null) {
			this.asgnTxServiceFactories = (List<IAsgnTxServiceFactory>) this
					.getApplicationContext().getBean(
							Constants.SPRING_OSGI_ASGN_TX_SERVICE_FACTORIES);
		}
		return asgnTxServiceFactories;
	}

	/**
	 * Set assignment factories.
	 * 
	 * @param asgnServiceFactories
	 */
	public void setAsgnTxServiceFactories(
			List<IAsgnTxServiceFactory> asgnTxServiceFactories) {
		this.asgnTxServiceFactories = asgnTxServiceFactories;
	}

	/**
	 * Get report service factories. If it is null attempts to retrieve it from
	 * Spring context by <code>osgiReportServiceFactories</code> alias.
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<IReportServiceFactory> getReportServiceFactories() {
		if (this.reportServiceFactories == null) {
			this.reportServiceFactories = (List<IReportServiceFactory>) this
					.getApplicationContext().getBean(
							Constants.SPRING_OSGI_REPORT_SERVICE_FACTORIES);
		}
		return this.reportServiceFactories;
	}

	/**
	 * Set report service factories.
	 * 
	 * @param dsServiceFactories
	 */
	public void setReportServiceFactories(
			List<IReportServiceFactory> reportServiceFactories) {
		this.reportServiceFactories = reportServiceFactories;
	}

}
