package seava.j4e.security;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import seava.j4e.api.security.IAuthorization;
import seava.j4e.api.security.IAuthorizationFactory;

public class DummyAuthorizationFactory implements IAuthorizationFactory,
		ApplicationContextAware {

	private ApplicationContext applicationContext;

	@Override
	public IAuthorization getAsgnAuthorizationProvider() {
		return (IAuthorization) this.applicationContext
				.getBean(DummyAuthorizationForAsgn.class);
	}

	@Override
	public IAuthorization getDsAuthorizationProvider() {
		return (IAuthorization) this.applicationContext
				.getBean(DummyAuthorizationForDs.class);
	}

	@Override
	public IAuthorization getJobAuthorizationProvider() {
		return (IAuthorization) this.applicationContext
				.getBean(DummyAuthorizationForJob.class);
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

}
