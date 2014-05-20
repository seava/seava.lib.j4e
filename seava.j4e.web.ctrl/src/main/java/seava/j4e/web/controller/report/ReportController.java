package seava.j4e.web.controller.report;

import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import seava.j4e.api.Constants;
import seava.j4e.api.descriptor.IReportExecutionContext;
import seava.j4e.api.service.presenter.IReportService;
import seava.j4e.commons.descriptor.ReportExecutionContext;
import seava.j4e.web.controller.AbstractBaseController;

@Controller
public class ReportController extends AbstractBaseController {

	final static Logger logger = LoggerFactory
			.getLogger(ReportController.class);

	@RequestMapping(value = "/{reportFqn}.{dataFormat}", method = { RequestMethod.GET })
	@ResponseBody
	public String runReport(@PathVariable String reportFqn,
			@PathVariable String dataFormat, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		try {
			if (logger.isInfoEnabled()) {
				logger.info("Processing request: run report {} ",
						new Object[] { reportFqn });
			}

			this.prepareRequest(request, response);
			response.setCharacterEncoding("UTF-8");
			IReportService reportService = (IReportService) this
					.getServiceLocator().findReportService(reportFqn);

			IReportExecutionContext context = new ReportExecutionContext();

			Map<String, Object> parameters = new HashMap<String, Object>();
			Map<String, String> httpRequestParams = this.collectParams(request,
					"", "");

			for (Map.Entry<String, String> e : httpRequestParams.entrySet()) {
				parameters.put(e.getKey(), e.getValue());
			}

			context.setParameters(parameters);
			context.setFormat(dataFormat);

			if (dataFormat.equals(Constants.DATA_FORMAT_HTML)) {

				PrintWriter writer = response.getWriter();
				context.setResultWriter(writer);
				reportService.runReport(context);

			} else if (dataFormat.equals(Constants.DATA_FORMAT_PDF)) {

				reportService.runReport(context);
				File result = context.getResultFile();

				response.setContentType("application/pdf");
				response.addHeader("Content-Disposition",
						"attachment; filename=" + result.getName());
				response.setContentLength((int) result.length());
				this.sendFile(result, response.getOutputStream());

			} else if (dataFormat.equals("xls")) {

				reportService.runReport(context);
				File result = context.getResultFile();

				response.setContentType("application/vnd.ms-excel");
				response.addHeader("Content-Disposition",
						"attachment; filename=" + result.getName());
				response.setContentLength((int) result.length());
				this.sendFile(result, response.getOutputStream());

			} else {
				throw new Exception("Unsupported data format " + dataFormat);
			}

			return null;

		} catch (Exception e) {
			throw e;
		} finally {
			this.finishRequest();
		}
	}

	/**
	 * Lookup a data-source service.
	 * 
	 * @param dsName
	 * @return
	 * @throws Exception
	 */
	public IReportService findReportService(String reportServiceAlias)
			throws Exception {
		return this.getServiceLocator().findReportService(reportServiceAlias);
	}

}
