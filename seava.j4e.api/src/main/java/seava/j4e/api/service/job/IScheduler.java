package seava.j4e.api.service.job;

public interface IScheduler {
	public void init() throws Exception;

	public void start() throws Exception;

	public void stop() throws Exception;

	public Object getDelegate() throws Exception;
}
