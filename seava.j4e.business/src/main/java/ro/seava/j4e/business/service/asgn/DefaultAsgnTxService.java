package ro.seava.j4e.business.service.asgn;

import ro.seava.j4e.api.service.business.IAsgnTxService;

public class DefaultAsgnTxService<E> extends AbstractAsgnTxService<E> implements
		IAsgnTxService<E> {

	public DefaultAsgnTxService() {
	}

	public DefaultAsgnTxService(Class<E> entityClass) {
		this.setEntityClass(entityClass);
	}

}