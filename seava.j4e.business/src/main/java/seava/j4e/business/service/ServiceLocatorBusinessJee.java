/** 
 * DNet eBusiness Suite
 * Copyright: 2013 Nan21 Electronics SRL. All rights reserved.
 * Use is subject to license terms.
 */
package seava.j4e.business.service;

import seava.j4e.api.exceptions.BusinessException;
import seava.j4e.api.exceptions.ErrorCode;
import seava.j4e.api.service.IServiceLocatorBusiness;
import seava.j4e.api.service.business.IEntityService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Service locator utility methods.
 * 
 * @author amathe
 */
public class ServiceLocatorBusinessJee implements ApplicationContextAware,
		IServiceLocatorBusiness {

	final static Logger logger = LoggerFactory
			.getLogger(ServiceLocatorBusinessJee.class);

	private ApplicationContext applicationContext;

	/**
	 * Find an entity service given the entity class.
	 * 
	 * @param <E>
	 * @param entityClass
	 * @return
	 * @throws BusinessException
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <E> IEntityService<E> findEntityService(Class<E> entityClass)
			throws BusinessException {
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
			throw new BusinessException(ErrorCode.G_RUNTIME_ERROR,
					"Entity service `" + serviceAlias + "` not found");
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
