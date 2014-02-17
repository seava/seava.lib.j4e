/** 
 * DNet eBusiness Suite
 * Copyright: 2013 Nan21 Electronics SRL. All rights reserved.
 * Use is subject to license terms.
 */
package seava.j4e.presenter.descriptor;

import seava.j4e.presenter.service.AbstractPresenterBaseService;

public class RpcDefinition {

	private final Class<? extends AbstractPresenterBaseService> delegateClass;
	private final String methodName;
	private boolean reloadFromEntity;

	public RpcDefinition(
			Class<? extends AbstractPresenterBaseService> delegateClass,
			String methodName) {
		super();
		this.delegateClass = delegateClass;
		this.methodName = methodName;
	}

	public RpcDefinition(
			Class<? extends AbstractPresenterBaseService> delegateClass,
			String methodName, boolean reloadFromEntity) {
		super();
		this.delegateClass = delegateClass;
		this.methodName = methodName;
		this.reloadFromEntity = reloadFromEntity;
	}

	public Class<? extends AbstractPresenterBaseService> getDelegateClass() {
		return delegateClass;
	}

	public String getMethodName() {
		return methodName;
	}

	public boolean getReloadFromEntity() {
		return this.reloadFromEntity;
	}

}