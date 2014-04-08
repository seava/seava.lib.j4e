/** 
 * DNet eBusiness Suite
 * Copyright: 2013 Nan21 Electronics SRL. All rights reserved.
 * Use is subject to license terms.
 */
package seava.j4e.web.controller.data;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import seava.j4e.api.Constants;
import seava.j4e.api.action.impex.IDsExport;
import seava.j4e.api.action.impex.IExportInfo;
import seava.j4e.api.action.query.IFilterRule;
import seava.j4e.api.action.query.IQueryBuilder;
import seava.j4e.api.action.query.ISortToken;
import seava.j4e.api.action.result.IActionResultFind;
import seava.j4e.api.action.result.IDsMarshaller;
import seava.j4e.api.descriptor.IDsDefinition;
import seava.j4e.api.descriptor.IDsDefinitions;
import seava.j4e.api.enums.SysParam;
import seava.j4e.api.exceptions.ErrorCode;
import seava.j4e.api.service.presenter.IDsService;
import seava.j4e.api.session.Session;
import seava.j4e.presenter.action.impex.ExportInfo;
import seava.j4e.presenter.action.marshaller.ModelPrinter;
import seava.j4e.presenter.action.marshaller.XmlMarshaller;
import seava.j4e.web.result.ActionResultFind;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.time.StopWatch;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import freemarker.template.Configuration;
import freemarker.template.ObjectWrapper;
import freemarker.template.Template;

public class AbstractDsReadController<M, F, P> extends
		AbstractDsController<M, F, P> {

	protected static final String DEFAULT_RESULT_START = "0";
	protected static final String DEFAULT_RESULT_SIZE = "500";

	final static Logger logger = LoggerFactory
			.getLogger(AbstractDsReadController.class);

	/**
	 * Default handler for find action.
	 * 
	 * @param resourceName
	 * @param dataformat
	 * @param dataString
	 * @param paramString
	 * @param resultStart
	 * @param resultSize
	 * @param orderByCol
	 * @param orderBySense
	 * @param orderBy
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = Constants.REQUEST_PARAM_ACTION + "="
			+ Constants.DS_ACTION_QUERY)
	@ResponseBody
	public String find(
			@PathVariable String resourceName,
			@PathVariable String dataFormat,
			@RequestParam(value = Constants.REQUEST_PARAM_FILTER, required = false, defaultValue = "{}") String filterString,
			@RequestParam(value = Constants.REQUEST_PARAM_ADVANCED_FILTER, required = false, defaultValue = "") String filterRulesString,
			@RequestParam(value = Constants.REQUEST_PARAM_PARAMS, required = false, defaultValue = "{}") String paramString,
			@RequestParam(value = Constants.REQUEST_PARAM_START, required = false, defaultValue = DEFAULT_RESULT_START) int resultStart,
			@RequestParam(value = Constants.REQUEST_PARAM_SIZE, required = false, defaultValue = DEFAULT_RESULT_SIZE) int resultSize,
			@RequestParam(value = Constants.REQUEST_PARAM_SORT, required = false, defaultValue = "") String orderByCol,
			@RequestParam(value = Constants.REQUEST_PARAM_SENSE, required = false, defaultValue = "") String orderBySense,
			@RequestParam(value = Constants.REQUEST_PARAM_ORDERBY, required = false, defaultValue = "") String orderBy,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {

			StopWatch stopWatch = new StopWatch();
			stopWatch.start();

			if (logger.isInfoEnabled()) {
				logger.info("Processing request: {}.{} -> action = {} ",
						new Object[] { resourceName, dataFormat,
								Constants.DS_ACTION_QUERY });
			}

			if (logger.isDebugEnabled()) {
				logger.debug("  --> request-filter: {} ",
						new Object[] { filterString });
				logger.debug("  --> request-params: {} ",
						new Object[] { paramString });
				logger.debug(
						"  --> request-orderBy: sort={}, sense={}, orderBy={}",
						new Object[] { orderByCol, orderBySense, orderBy });
				logger.debug("  --> request-result-range: {} ", new Object[] {
						resultStart + "", (resultStart + resultSize) + "" });
			}
			this.prepareRequest(request, response);

			this.authorizeDsAction(resourceName, Constants.DS_ACTION_QUERY,
					null);

			IDsService<M, F, P> service = this.findDsService(resourceName);
			IDsMarshaller<M, F, P> marshaller = service
					.createMarshaller(IDsMarshaller.JSON);

			F filter = marshaller.readFilterFromString(filterString);
			P params = marshaller.readParamsFromString(paramString);

			IQueryBuilder<M, F, P> builder = service.createQueryBuilder()
					.addFetchLimit(resultStart, resultSize).addFilter(filter)
					.addParams(params);

			if (orderBy != null && !orderBy.equals("")) {
				List<ISortToken> sortTokens = marshaller
						.readSortTokens(orderBy);
				builder.addSortInfo(sortTokens);
			} else {
				builder.addSortInfo(orderByCol, orderBySense);
			}

			if (filterRulesString != null && !filterRulesString.equals("")) {
				List<IFilterRule> filterRules = marshaller
						.readFilterRules(filterRulesString);
				builder.addFilterRules(filterRules);
			}

			List<M> list = service.find(builder);
			long totalCount = service.count(builder);

			IActionResultFind result = this.packfindResult(list, params,
					totalCount);
			stopWatch.stop();
			result.setExecutionTime(stopWatch.getTime());

			String out = null;

			if (dataFormat.equals(IDsMarshaller.XML)) {
				IDsMarshaller<M, F, P> resultMarshaller = service
						.createMarshaller(dataFormat);
				out = resultMarshaller.writeResultToString(result);
				response.setContentType("text/xml; charset=UTF-8");
			} else {
				out = marshaller.writeResultToString(result);
				response.setContentType("text/plain; charset=UTF-8");
			}

			return out;
		} catch (Exception e) {
			return this.handleManagedException(ErrorCode.DB_QUERY_ERROR, e,
					response);
		} finally {
			this.finishRequest();
		}

	}

	/**
	 * Default handler for export action.
	 * 
	 * @param resourceName
	 * @param dataformat
	 * @param dataString
	 * @param paramString
	 * @param resultStart
	 * @param resultSize
	 * @param orderByCol
	 * @param orderBySense
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(params = Constants.REQUEST_PARAM_ACTION + "="
			+ Constants.DS_ACTION_EXPORT, method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public String export(
			@PathVariable String resourceName,
			@PathVariable String dataFormat,
			@RequestParam(value = Constants.REQUEST_PARAM_FILTER, required = false, defaultValue = "{}") String filterString,
			@RequestParam(value = Constants.REQUEST_PARAM_ADVANCED_FILTER, required = false, defaultValue = "") String filterRulesString,
			@RequestParam(value = Constants.REQUEST_PARAM_PARAMS, required = false, defaultValue = "{}") String paramString,
			@RequestParam(value = Constants.REQUEST_PARAM_START, required = false, defaultValue = DEFAULT_RESULT_START) int resultStart,
			@RequestParam(value = Constants.REQUEST_PARAM_SIZE, required = false, defaultValue = DEFAULT_RESULT_SIZE) int resultSize,
			@RequestParam(value = Constants.REQUEST_PARAM_SORT, required = false, defaultValue = "") String orderByCol,
			@RequestParam(value = Constants.REQUEST_PARAM_SENSE, required = false, defaultValue = "") String orderBySense,
			@RequestParam(value = Constants.REQUEST_PARAM_ORDERBY, required = false, defaultValue = "") String orderBy,
			@RequestParam(value = Constants.REQUEST_PARAM_EXPORT_DOWNLOAD, required = false, defaultValue = "true") Boolean exportDownload,
			@RequestParam(value = Constants.REQUEST_PARAM_EXPORT_INFO, required = true, defaultValue = "") String exportInfoString,
			HttpServletRequest request, HttpServletResponse response)

	throws Exception {
		try {

			if (logger.isInfoEnabled()) {
				logger.info("Processing request: {}.{} -> action = {} ",
						new Object[] { resourceName, dataFormat,
								Constants.DS_ACTION_EXPORT });
			}

			if (logger.isDebugEnabled()) {
				logger.debug("  --> request-filter: {} ",
						new Object[] { filterString });
				logger.debug("  --> request-params: {} ",
						new Object[] { paramString });
				logger.debug("  --> request-result-range: {} ", new Object[] {
						resultStart + "", (resultStart + resultSize) + "" });
			}

			this.prepareRequest(request, response);

			this.authorizeDsAction(resourceName, Constants.DS_ACTION_EXPORT,
					null);

			IDsService<M, F, P> service = this.findDsService(resourceName);
			IDsMarshaller<M, F, P> marshaller = service
					.createMarshaller("json");

			F filter = marshaller.readFilterFromString(filterString);
			P params = marshaller.readParamsFromString(paramString);

			IQueryBuilder<M, F, P> builder = service.createQueryBuilder()
					.addFetchLimit(resultStart, resultSize).addFilter(filter)
					.addParams(params);

			if (orderBy != null && !orderBy.equals("")) {
				List<ISortToken> sortTokens = marshaller
						.readSortTokens(orderBy);
				builder.addSortInfo(sortTokens);
			} else {
				builder.addSortInfo(orderByCol, orderBySense);
			}

			if (filterRulesString != null && !filterRulesString.equals("")) {
				List<IFilterRule> filterRules = marshaller
						.readFilterRules(filterRulesString);
				builder.addFilterRules(filterRules);
			}

			IDsExport<M> writer = service.createExporter(dataFormat);

			if (dataFormat.equals(Constants.DATA_FORMAT_CSV)) {
				writer = (IDsExport<M>) this.getApplicationContext().getBean(
						Constants.SPRING_DSEXPORT_WRITER_CSV);
			}
			if (dataFormat.equals(Constants.DATA_FORMAT_JSON)) {
				writer = (IDsExport<M>) this.getApplicationContext().getBean(
						Constants.SPRING_DSEXPORT_WRITER_JSON);
			}
			if (dataFormat.equals(Constants.DATA_FORMAT_XML)) {
				writer = (IDsExport<M>) this.getApplicationContext().getBean(
						Constants.SPRING_DSEXPORT_WRITER_XML);
			}
			if (dataFormat.equals(Constants.DATA_FORMAT_PDF)) {
				writer = (IDsExport<M>) this.getApplicationContext().getBean(
						Constants.SPRING_DSEXPORT_WRITER_XML);
			}
			if (dataFormat.equals(Constants.DATA_FORMAT_HTML)) {
				writer = (IDsExport<M>) this.getApplicationContext().getBean(
						Constants.SPRING_DSEXPORT_WRITER_HTML);
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(
						"cssUrl",
						this.getSettings().getParam(
								SysParam.CORE_EXP_HTML_CSS.name()));
				writer.setProperties(properties);
			}

			if (writer == null) {
				throw new Exception("Invalid data-format " + dataFormat);
			}

			IExportInfo exportInfo = marshaller
					.readExportInfo(exportInfoString);
			exportInfo.prepare(service.getModelClass());

			writer.setExportInfo(exportInfo);

			writer.setOutFilePath(Session.user.get().getWorkspace()
					.getTempPath());
			service.doExport(builder, writer);

			if (exportDownload) {
				// get the file content
				if (dataFormat.equals(Constants.DATA_FORMAT_CSV)) {
					response.setContentType("application/vnd.ms-excel; charset=UTF-8");
				}
				if (dataFormat.equals(Constants.DATA_FORMAT_JSON)) {
					response.setContentType("text/plain; charset=UTF-8");
				}
				if (dataFormat.equals(Constants.DATA_FORMAT_HTML)) {
					response.setContentType("text/html; charset=UTF-8");
				}
				if (dataFormat.equals(Constants.DATA_FORMAT_XML)) {
					response.setContentType("text/xml; charset=UTF-8");
				}
				response.setHeader("Content-Description", "File Transfer");
				response.setHeader("Content-Disposition", "inline; filename=\""
						+ service.getModelClass().getSimpleName() + "."
						+ dataFormat.toLowerCase() + "\";");

				this.sendFile(writer.getOutFile(), response.getOutputStream());
				return null;
			} else {
				// just send the file name
				return "{success:true, file:\"" + writer.getOutFile().getName()
						+ "\"}";
			}
		} catch (Exception e) {
			// return this.handleException(e, response);
			throw e;
		} finally {
			this.finishRequest();
		}
	}

	/**
	 * Returns information about the given resource ( data-source )
	 * 
	 * @param resourceName
	 * @param dataFormat
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = Constants.REQUEST_PARAM_ACTION + "="
			+ Constants.DS_ACTION_INFO)
	public String info(@PathVariable String resourceName,
			@PathVariable String dataFormat, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			StopWatch stopWatch = new StopWatch();
			stopWatch.start();

			if (logger.isInfoEnabled()) {
				logger.info("Processing request: {}.{} -> action = {} ",
						new Object[] { resourceName, dataFormat,
								Constants.DS_ACTION_INFO });

			}

			this.prepareRequest(request, response);

			String out = null;
			IDsDefinition def = null;

			if (this.getSettings().get(Constants.PROP_DEPLOYMENT)
					.equals(Constants.PROP_DEPLOYMENT_JEE)) {
				IDsDefinitions defs = this.getApplicationContext().getBean(
						IDsDefinitions.class);
				if (defs.containsDs(resourceName)) {
					def = defs.getDsDefinition(resourceName);
				}
			} else {
				@SuppressWarnings("unchecked")
				List<IDsDefinitions> defsList = (List<IDsDefinitions>) this
						.getApplicationContext().getBean("osgiDsDefinitions");
				for (IDsDefinitions defs : defsList) {
					if (defs.containsDs(resourceName)) {
						def = defs.getDsDefinition(resourceName);
					}
				}
			}

			def.getModelFields();
			def.getFilterFields();
			def.getParamFields();
			IDsService<M, F, P> service = this.findDsService(resourceName);

			if (dataFormat.equals(IDsMarshaller.JSON)) {

				IDsMarshaller<M, F, P> marshaller = service
						.createMarshaller(dataFormat);
				response.setContentType("text/plain; charset=UTF-8");

				out = ((ObjectMapper) marshaller.getDelegate())
						.writeValueAsString(def);
				PrintWriter w = response.getWriter();
				w.write(out);
				w.flush();
				return null;
			} else if (dataFormat.equals(IDsMarshaller.XML)) {

				// IDsMarshaller<M, F, P> marshaller = service
				// .createMarshaller(dataFormat);
				// StringWriter writer = new StringWriter();
				//
				//
				// ((XmlMarshaller<M, F, P>)
				// marshaller).createMarshaller(
				// def.getClass()).marshal(def, writer);
				//
				//
				// response.setContentType("text/xml; charset=UTF-8");
				// out = writer.toString();
				// PrintWriter w = response.getWriter();
				// w.write(out);
				// w.flush();
				// return null;
			} else if (dataFormat.equals("html")) {

				IDsMarshaller<M, F, P> marshaller = service
						.createMarshaller(IDsMarshaller.XML);

				StringWriter writer = new StringWriter();
				((XmlMarshaller<M, F, P>) marshaller).createMarshaller(
						def.getClass()).marshal(def, writer);
				out = writer.toString();
				String t1 = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>";
				String t2 = "<?xml-stylesheet type=\"text/xsl\" href=\"/seava/resources/xsl/ds-info.xsl\"?>";
				out = out.replace(t1, t1 + '\n' + t2);
				response.setContentType("text/xml; charset=UTF-8");

				PrintWriter w = response.getWriter();
				w.write(out);
				w.flush();
				return null;
			}

			throw new Exception("Data-source " + resourceName
					+ " cannot be found.");

		} catch (Exception e) {
			return this.handleException(e, response);
		} finally {
			this.finishRequest();
		}
	}

	@RequestMapping(params = Constants.REQUEST_PARAM_ACTION + "="
			+ Constants.DS_ACTION_PRINT)
	@ResponseBody
	public String print(
			@PathVariable String resourceName,
			@PathVariable String dataFormat,
			@RequestParam(value = Constants.REQUEST_PARAM_FILTER, required = false, defaultValue = "{}") String filterString,
			@RequestParam(value = Constants.REQUEST_PARAM_ADVANCED_FILTER, required = false, defaultValue = "") String filterRulesString,
			@RequestParam(value = Constants.REQUEST_PARAM_PARAMS, required = false, defaultValue = "{}") String paramString,
			@RequestParam(value = Constants.REQUEST_PARAM_START, required = false, defaultValue = DEFAULT_RESULT_START) int resultStart,
			@RequestParam(value = Constants.REQUEST_PARAM_SIZE, required = false, defaultValue = DEFAULT_RESULT_SIZE) int resultSize,
			@RequestParam(value = Constants.REQUEST_PARAM_SORT, required = false, defaultValue = "") String orderByCol,
			@RequestParam(value = Constants.REQUEST_PARAM_SENSE, required = false, defaultValue = "") String orderBySense,
			@RequestParam(value = Constants.REQUEST_PARAM_ORDERBY, required = false, defaultValue = "") String orderBy,
			@RequestParam(value = Constants.REQUEST_PARAM_EXPORT_DOWNLOAD, required = false, defaultValue = "true") Boolean exportDownload,
			@RequestParam(value = Constants.REQUEST_PARAM_EXPORT_INFO, required = true, defaultValue = "") String exportInfoString,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// return null;
		try {

			if (logger.isInfoEnabled()) {
				logger.info("Processing request: {}.{} -> action = {} ",
						new Object[] { resourceName, dataFormat,
								Constants.DS_ACTION_PRINT });
			}

			if (logger.isDebugEnabled()) {

				logger.debug("  --> request-filter: {} ",
						new Object[] { filterString });
				logger.debug("  --> request-params: {} ",
						new Object[] { paramString });
				logger.debug("  --> request-result-range: {} ", new Object[] {
						resultStart + "", (resultStart + resultSize) + "" });
			}

			this.prepareRequest(request, response);

			this.authorizeDsAction(resourceName, Constants.DS_ACTION_EXPORT,
					null);

			IDsService<M, F, P> service = this.findDsService(resourceName);

			IDsMarshaller<M, F, P> marshaller = service
					.createMarshaller("json");

			F filter = marshaller.readFilterFromString(filterString);
			P params = marshaller.readParamsFromString(paramString);

			ExportInfo exportInfo = marshaller.readDataFromString(
					exportInfoString, ExportInfo.class);
			exportInfo.prepare(service.getModelClass());

			IQueryBuilder<M, F, P> builder = service.createQueryBuilder()
					.addFetchLimit(resultStart, resultSize).addFilter(filter)
					.addParams(params);

			if (orderBy != null && !orderBy.equals("")) {
				List<ISortToken> sortTokens = marshaller
						.readSortTokens(orderBy);
				builder.addSortInfo(sortTokens);
			} else {
				builder.addSortInfo(orderByCol, orderBySense);
			}

			if (filterRulesString != null && !filterRulesString.equals("")) {
				List<IFilterRule> filterRules = marshaller
						.readFilterRules(filterRulesString);
				builder.addFilterRules(filterRules);
			}

			List<M> data = service.find(builder);

			File _tplDir = null;
			String _tplName = null;

			String _tpl = this.getSettings().getParam(
					SysParam.CORE_PRINT_HTML_TPL.name());

			if (_tpl == null || "".equals(_tpl)) {
				_tpl = "print-template/print.ftl";
			}

			_tpl = Session.user.get().getWorkspace().getWorkspacePath() + "/"
					+ _tpl;
			File _tplFile = new File(_tpl);

			_tplDir = _tplFile.getParentFile();
			_tplName = _tplFile.getName();

			if (!_tplFile.exists()) {

				// _tplDir = _tplFile.getParentFile();

				if (!_tplDir.exists()) {
					_tplDir.mkdirs();
				}

				Resource resource = new ClassPathResource(
						"seava/j4e/web/ftl/data/print.ftl");
				FileUtils.copyInputStreamToFile(resource.getInputStream(),
						_tplFile);
			}

			Configuration cfg = new Configuration();
			cfg.setObjectWrapper(ObjectWrapper.DEFAULT_WRAPPER);
			cfg.setDirectoryForTemplateLoading(_tplDir);

			Map<String, Object> root = new HashMap<String, Object>();

			root.put("printer", new ModelPrinter());
			root.put("data", data);
			root.put("filter", filter);
			root.put("params", params);
			root.put("client", Session.user.get().getClient());

			Map<String, Object> reportConfig = new HashMap<String, Object>();
			reportConfig.put(
					"logo",
					this.getSettings().getParam(
							SysParam.CORE_LOGO_URL_REPORT.name()));
			reportConfig.put("runBy", Session.user.get().getName());
			reportConfig.put("runAt", new Date());
			reportConfig.put("title", exportInfo.getTitle());
			reportConfig.put("orientation", exportInfo.getLayout());
			reportConfig.put("columns", exportInfo.getColumns());
			reportConfig.put("filter", exportInfo.getFilter());

			root.put("cfg", reportConfig);

			if (dataFormat.equals(Constants.DATA_FORMAT_HTML)) {
				response.setContentType("text/html; charset=UTF-8");
			}

			Template temp = cfg.getTemplate(_tplName);

			if (exportDownload) {
				Writer out = new OutputStreamWriter(response.getOutputStream(),
						response.getCharacterEncoding());
				temp.process(root, out);
				out.flush();
				return null;
			} else {
				String path = Session.user.get().getWorkspace().getTempPath();
				String fileName = UUID.randomUUID().toString();
				File outFile = new File(path + "/" + fileName + "."
						+ dataFormat);

				Writer out = new OutputStreamWriter(new FileOutputStream(
						outFile), "UTF-8");
				temp.process(root, out);
				out.flush();
				out.close();
				// just send the file name
				return "{success:true, file:\"" + outFile.getName() + "\"}";
			}

		} catch (Exception e) {
			e.printStackTrace();
			this.handleException(e, response);
			return null;
		} finally {
			this.finishRequest();
		}

	}

	public IActionResultFind packfindResult(List<M> data, P params,
			long totalCount) {
		IActionResultFind pack = new ActionResultFind();
		pack.setData(data);
		pack.setParams(params);
		pack.setTotalCount(totalCount);
		return pack;
	}

}
