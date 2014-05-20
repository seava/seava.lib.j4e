package seava.j4e.api.service.presenter;

import seava.j4e.api.descriptor.IReportExecutionContext;

public interface IReportService {

	public Object getReport(String reportFqn) throws Exception;

	public void runReport(IReportExecutionContext context) throws Exception;

}
