/** 
 * DNet eBusiness Suite
 * Copyright: 2013 Nan21 Electronics SRL. All rights reserved.
 * Use is subject to license terms.
 */
package seava.j4e.business.service;

import java.util.List;

import seava.j4e.api.Constants;
import seava.j4e.api.exceptions.BusinessException;
import seava.j4e.api.exceptions.ErrorCode;
import seava.j4e.api.service.business.IEntityService;
import seava.j4e.api.service.business.IEntityServiceFactory;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Service locator utility methods.
 * 
 * @author amathe
 */
public class ServiceLocatorBusiness implements ApplicationContextAware {

	private ApplicationContext applicationContext;

	private List<IEntityServiceFactory> entityServiceFactories;

	/**
	 * Find an entity service given the entity class.
	 * 
	 * @param <E>
	 * @param entityClass
	 * @return
	 * @throws BusinessException
	 */
	public <E> IEntityService<E> findEntityService(Class<E> entityClass)
			throws BusinessException {
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
	 * @throws BusinessException
	 */
	public <E> IEntityService<E> findEntityService(Class<E> entityClass,
			List<IEntityServiceFactory> factories) throws BusinessException {
		for (IEntityServiceFactory esf : factories) {
			try {
				IEntityService<E> srv = esf.create(entityClass.getSimpleName());
				if (srv != null) {
					return srv;
				}
			} catch (NoSuchBeanDefinitionException e) {
				// service not found in this factory, ignore
			}
		}
		throw new BusinessException(ErrorCode.G_RUNTIME_ERROR,
				entityClass.getSimpleName() + "Service" + " not found ");
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
