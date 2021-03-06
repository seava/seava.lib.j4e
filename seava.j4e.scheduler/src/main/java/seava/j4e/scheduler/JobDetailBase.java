package seava.j4e.scheduler;

import seava.j4e.api.Constants;
import seava.j4e.api.service.job.IJob;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class JobDetailBase implements IDnetQuartzJobDetail {

	private ServiceLocator serviceLocator;

	public void execute(JobExecutionContext context)
			throws JobExecutionException {

		try {
			JobDataMap data = context.getJobDetail().getJobDataMap();
			String jobName = data.getString(Constants.QUARTZ_JOB_NAME);
			IJob job = getServiceLocator().findJob(jobName);
			if (job != null) {
				job.setExecutionContext(context);
				job.execute();
			}
		} catch (Exception e) {
			throw new JobExecutionException(e.getMessage());
		}
	}

	public ServiceLocator getServiceLocator() {
		return serviceLocator;
	}

	public void setServiceLocator(ServiceLocator serviceLocator) {
		this.serviceLocator = serviceLocator;
	}
}
