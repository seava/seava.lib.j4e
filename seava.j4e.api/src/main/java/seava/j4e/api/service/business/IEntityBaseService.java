/** 
 * DNet eBusiness Suite
 * Copyright: 2013 Nan21 Electronics SRL. All rights reserved.
 * Use is subject to license terms.
 */
package seava.j4e.api.service.business;

import java.util.Map;

import javax.persistence.EntityManager;

import seava.j4e.api.ISettings;
import seava.j4e.api.exceptions.BusinessException;

public interface IEntityBaseService<E> {

	/**
	 * Get EntityManager
	 * 
	 * @return
	 */
	public EntityManager getEntityManager();

	/**
	 * Set EntityManager
	 * 
	 * @param em
	 */
	public void setEntityManager(EntityManager em);

	public E create() throws BusinessException;

	public ISettings getSettings();

	public void setSettings(ISettings settings);

	public void doStartWfProcessInstanceByKey(String processDefinitionKey,
			String businessKey, Map<String, Object> variables)
			throws BusinessException;

	public void doStartWfProcessInstanceById(String processDefinitionId,
			String businessKey, Map<String, Object> variables)
			throws BusinessException;

	public void doStartWfProcessInstanceByMessage(String messageName,
			String businessKey, Map<String, Object> processVariables)
			throws BusinessException;
}
