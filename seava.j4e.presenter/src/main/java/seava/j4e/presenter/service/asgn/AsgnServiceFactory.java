/** 
 * DNet eBusiness Suite
 * Copyright: 2013 Nan21 Electronics SRL. All rights reserved.
 * Use is subject to license terms.
 */
package seava.j4e.presenter.service.asgn;

import java.util.List;

import seava.j4e.api.service.business.IAsgnTxServiceFactory;
import seava.j4e.api.service.presenter.IAsgnService;
import seava.j4e.api.service.presenter.IAsgnServiceFactory;
import seava.j4e.presenter.AbstractApplicationContextAware;

public class AsgnServiceFactory extends AbstractApplicationContextAware
		implements IAsgnServiceFactory {

	private List<IAsgnTxServiceFactory> asgnTxServiceFactories;

	@SuppressWarnings("unchecked")
	@Override
	public <M, F, P> IAsgnService<M, F, P> create(String key) {
		IAsgnService<M, F, P> s = (IAsgnService<M, F, P>) this
				.getApplicationContext().getBean(key, IAsgnService.class);
		s.setAsgnTxServiceFactories(asgnTxServiceFactories);
		return s;
	}

	public List<IAsgnTxServiceFactory> getAsgnTxServiceFactories() {
		return asgnTxServiceFactories;
	}

	public void setAsgnTxServiceFactories(
			List<IAsgnTxServiceFactory> asgnTxServiceFactories) {
		this.asgnTxServiceFactories = asgnTxServiceFactories;
	}

}
