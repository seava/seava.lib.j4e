package seava.j4e.presenter.service.report;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import seava.j4e.api.Constants;
import seava.j4e.api.descriptor.IReportExecutionContext;
import seava.j4e.api.enums.DateFormatAttribute;
import seava.j4e.api.service.presenter.IReportService;
import seava.j4e.api.session.IUserSettings;
import seava.j4e.api.session.Session;
import seava.j4e.presenter.service.AbstractPresenterBaseService;

public abstract class AbstractJasperReportService extends
		AbstractPresenterBaseService implements IReportService {

	public static final String MAIN_DS = "main";
	private String reportFqn;

	private Map<String, String> subreports;

	public abstract Map<String, Collection<?>> getReportData(
			IReportExecutionContext context) throws Exception;

	/**
	 * 
	 */
	@Override
	public void runReport(IReportExecutionContext context) throws Exception {

		Map<String, Object> params = context.getParameters();

		IUserSettings us = Session.user.get().getSettings();

		params.put("DECIMAL_SEPARATOR", us.getDecimalSeparator().charAt(0));
		params.put("THOUSAND_SEPARATOR", us.getThousandSeparator().charAt(0));

		params.put("DATE_FORMAT",
				us.getDateFormat(DateFormatAttribute.JAVA_DATE_FORMAT));
		params.put("TIME_FORMAT",
				us.getDateFormat(DateFormatAttribute.JAVA_TIME_FORMAT));
		params.put("DATETIME_FORMAT",
				us.getDateFormat(DateFormatAttribute.JAVA_DATETIME_FORMAT));
		params.put("MONTH_FORMAT",
				us.getDateFormat(DateFormatAttribute.JAVA_MONTH_FORMAT));

		String format = context.getFormat();

		if (format.equals(Constants.DATA_FORMAT_PDF)) {
			this.runPdfReport(context);
		} else if (format.equals(Constants.DATA_FORMAT_HTML)) {
			this.runHtmlReport(context);
		} else if (format.equals(Constants.DATA_FORMAT_XLS)) {
			this.runXlsReport(context);
		}
	}

	protected JRDataSource createJrDataSource(Collection<?> data)
			throws Exception {
		return new JRBeanCollectionDataSource(data);
	}

	/**
	 * 
	 * @param context
	 * @throws Exception
	 */
	protected void runHtmlReport(IReportExecutionContext context)
			throws Exception {

		JasperReport jasperReport = this.getReport(this.reportFqn);

		Map<String, Collection<?>> reportData = this.getReportData(context);

		if (this.subreports != null) {
			for (Map.Entry<String, String> entry : this.subreports.entrySet()) {
				context.getParameters().put(entry.getKey(),
						this.getReport(entry.getValue()));
				context.getParameters()
						.put(entry.getKey() + "_ds",
								this.createJrDataSource(reportData.get(entry
										.getKey())));
			}
		}

		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,
				context.getParameters(),
				this.createJrDataSource(reportData.get(MAIN_DS)));

		HtmlExporter exporter = new HtmlExporter();
		PrintWriter out = context.getResultWriter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
		exporter.setParameter(JRExporterParameter.CHARACTER_ENCODING, "UTF-8");

		exporter.exportReport();
	}

	/**
	 * 
	 * @param context
	 * @throws Exception
	 */
	protected void runPdfReport(IReportExecutionContext context)
			throws Exception {

		JasperReport jasperReport = this.getReport(this.reportFqn);

		Map<String, Collection<?>> reportData = this.getReportData(context);

		if (this.subreports != null) {
			for (Map.Entry<String, String> entry : this.subreports.entrySet()) {
				context.getParameters().put(entry.getKey(),
						this.getReport(entry.getValue()));
				context.getParameters()
						.put(entry.getKey() + "_ds",
								this.createJrDataSource(reportData.get(entry
										.getKey())));
			}
		}

		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,
				context.getParameters(),
				this.createJrDataSource(reportData.get(MAIN_DS)));

		File resultFile = new File(reportFqn + ".pdf");
		FileOutputStream stream = new FileOutputStream(resultFile);
		JasperExportManager.exportReportToPdfStream(jasperPrint, stream);
		stream.close();
		context.setResultFile(resultFile);
	}

	/**
	 * 
	 * @param context
	 * @throws Exception
	 */
	protected void runXlsReport(IReportExecutionContext context)
			throws Exception {

		JasperReport jasperReport = this.getReport(this.reportFqn);
		Map<String, Collection<?>> reportData = this.getReportData(context);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,
				context.getParameters(),
				this.createJrDataSource(reportData.get(MAIN_DS)));
		File resultFile = new File(reportFqn + ".xls");
		JRXlsExporter exporter = new JRXlsExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_FILE, resultFile);
		exporter.exportReport();
		context.setResultFile(resultFile);
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public JasperReport getReport(String reportFqn) throws Exception {

		JasperReport jasperReport;

		String rptBinCache = this.getSettings().get(Constants.PROP_WORKSPACE)
				+ "/.cache";

		File rptBin = new File(rptBinCache, reportFqn + ".jasper");

		// get report
		if (Constants.PROP_WORKING_MODE_DEV.equalsIgnoreCase(this.getSettings()
				.get(Constants.PROP_WORKING_MODE))) {

			// DEV mode: Copy source to cache folder and compile it every time
			String reportFqnAsPath = reportFqn.replaceAll("\\.", "/");
			Resource resource = new ClassPathResource(reportFqnAsPath
					+ ".jrxml");
			if (!resource.exists()) {
				throw new Exception("Cannot find resource " + reportFqn);
			}
			File rptSrc = new File(rptBinCache, reportFqn + ".jrxml");
			FileUtils.copyInputStreamToFile(resource.getInputStream(), rptSrc);
			JasperCompileManager.compileReportToFile(rptSrc.toString(),
					rptBinCache + "/" + reportFqn + ".jasper");

		} else {

			// PROD mode: Get compiled if exists or compile it

			if (!rptBin.exists()) {
				File rptSrc = new File(rptBinCache, reportFqn + ".jrxml");
				if (!rptSrc.exists()) {
					String reportFqnAsPath = reportFqn.replaceAll("\\.", "/");
					Resource resource = new ClassPathResource(reportFqnAsPath
							+ ".jrxml");
					if (!resource.exists()) {
						throw new Exception("Cannot find resource " + reportFqn);
					}
					FileUtils.copyInputStreamToFile(resource.getInputStream(),
							rptSrc);
				}
				JasperCompileManager.compileReportToFile(rptSrc.toString());
			}
		}

		jasperReport = (JasperReport) JRLoader.loadObjectFromFile(rptBin
				.getPath());

		return jasperReport;
	}

	public String getReportFqn() {
		return reportFqn;
	}

	public void setReportFqn(String reportFqn) {
		this.reportFqn = reportFqn;
	}

	public Map<String, String> getSubreports() {
		return subreports;
	}

	public void setSubreports(Map<String, String> subreports) {
		this.subreports = subreports;
	}

}
