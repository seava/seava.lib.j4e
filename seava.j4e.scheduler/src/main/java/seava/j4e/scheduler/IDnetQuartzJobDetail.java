package seava.j4e.scheduler;

import org.quartz.Job;

public interface IDnetQuartzJobDetail extends Job {

	public ServiceLocator getServiceLocator();

	public void setServiceLocator(ServiceLocator serviceLocator);
}
