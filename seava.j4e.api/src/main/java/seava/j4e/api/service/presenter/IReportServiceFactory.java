package seava.j4e.api.service.presenter;

public interface IReportServiceFactory {

	public String getName();

	public IReportService create(String key);
}
