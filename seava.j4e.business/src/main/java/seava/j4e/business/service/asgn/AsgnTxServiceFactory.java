package seava.j4e.business.service.asgn;

import seava.j4e.api.service.business.IAsgnTxService;
import seava.j4e.api.service.business.IAsgnTxServiceFactory;
import seava.j4e.business.AbstractApplicationContextAware;

public class AsgnTxServiceFactory extends AbstractApplicationContextAware
		implements IAsgnTxServiceFactory {

	private String name;

	@Override
	public <E> IAsgnTxService<E> create(String key) {
		@SuppressWarnings("unchecked")
		IAsgnTxService<E> s = (IAsgnTxService<E>) this.getApplicationContext()
				.getBean(key, IAsgnTxService.class);
		return s;
	}

	public <E> IAsgnTxService<E> create(Class<E> type) {
		@SuppressWarnings("unchecked")
		IAsgnTxService<E> s = (IAsgnTxService<E>) this.getApplicationContext()
				.getBean(type);
		return s;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
