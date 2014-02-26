/** 
 * DNet eBusiness Suite
 * Copyright: 2013 Nan21 Electronics SRL. All rights reserved.
 * Use is subject to license terms.
 */
package seava.j4e.web.controller.data;

import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import seava.j4e.api.Constants;
import seava.j4e.api.action.result.IActionResultRpcData;
import seava.j4e.api.action.result.IActionResultRpcFilter;
import seava.j4e.api.action.result.IActionResultSave;
import seava.j4e.api.action.result.IDsMarshaller;
import seava.j4e.api.service.presenter.IDsService;
import seava.j4e.web.result.ActionResultRpcData;
import seava.j4e.web.result.ActionResultRpcFilter;
import seava.j4e.web.result.ActionResultSave;

import org.apache.commons.lang.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

public class AbstractDsRpcController<M, F, P> extends
		AbstractDsReadController<M, F, P> {

	final static Logger logger = LoggerFactory
			.getLogger(AbstractDsRpcController.class);

	/**
	 * Default handler for remote procedure call on a single value-object.
	 * 
	 * @param resourceName
	 * @param dataformat
	 * @param dataString
	 * @param paramString
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = {
			Constants.REQUEST_PARAM_ACTION + "=" + Constants.DS_ACTION_RPC,
			Constants.DS_ACTION_RPC + "Type=data" })
	@ResponseBody
	public String rpcData(
			@PathVariable String resourceName,
			@PathVariable String dataFormat,
			@RequestParam(value = Constants.REQUEST_PARAM_SERVICE_NAME_PARAM, required = true) String rpcName,
			@RequestParam(value = Constants.REQUEST_PARAM_DATA, required = false, defaultValue = "{}") String dataString,
			@RequestParam(value = Constants.REQUEST_PARAM_PARAMS, required = false, defaultValue = "{}") String paramString,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {
			StopWatch stopWatch = new StopWatch();
			stopWatch.start();

			if (logger.isInfoEnabled()) {
				logger.info(
						"Processing request: {}.{} -> action = {}-data / {}",
						new Object[] { resourceName, dataFormat,
								Constants.DS_ACTION_RPC, rpcName });
			}

			if (logger.isDebugEnabled()) {
				logger.debug("  --> request-data: {} ",
						new Object[] { dataString });
				logger.debug("  --> request-params: {} ",
						new Object[] { paramString });
			}

			this.prepareRequest(request, response);

			this.authorizeDsAction(resourceName, Constants.DS_ACTION_RPC,
					rpcName);

			if (dataFormat.equals("stream")) {
				IDsService<M, F, P> service = this.findDsService(resourceName);
				IDsMarshaller<M, F, P> marshaller = service
						.createMarshaller("json");

				M data = marshaller.readDataFromString(dataString);
				P params = marshaller.readParamsFromString(paramString);

				InputStream s = service.rpcDataStream(rpcName, data, params);
				this.sendFile(s, response.getOutputStream());
				return "";
			} else {
				IDsService<M, F, P> service = this.findDsService(resourceName);
				IDsMarshaller<M, F, P> marshaller = service
						.createMarshaller(dataFormat);

				M data = marshaller.readDataFromString(dataString);
				P params = marshaller.readParamsFromString(paramString);

				service.rpcData(rpcName, data, params);
				IActionResultRpcData result = this.packRpcDataResult(data,
						params);
				stopWatch.stop();
				result.setExecutionTime(stopWatch.getTime());
				return marshaller.writeResultToString(result);
			}

		} catch (Exception e) {
			return this.handleException(e, response);
		} finally {
			this.finishRequest();
		}
	}

	/**
	 * Default handler for remote procedure call on a list of value-objects.
	 * Contributed by Jan Fockaert
	 * 
	 * @param resourceName
	 * @param dataFormat
	 * @param rpcName
	 * @param dataString
	 * @param paramString
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = {
			Constants.REQUEST_PARAM_ACTION + "=" + Constants.DS_ACTION_RPC,
			Constants.DS_ACTION_RPC + "Type=dataList" })
	@ResponseBody
	public String rpcDataList(
			@PathVariable String resourceName,
			@PathVariable String dataFormat,
			@RequestParam(value = Constants.REQUEST_PARAM_SERVICE_NAME_PARAM, required = true) String rpcName,
			@RequestParam(value = Constants.REQUEST_PARAM_DATA, required = false, defaultValue = "{}") String dataString,
			@RequestParam(value = Constants.REQUEST_PARAM_PARAMS, required = false, defaultValue = "{}") String paramString,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {
			StopWatch stopWatch = new StopWatch();
			stopWatch.start();

			if (logger.isInfoEnabled()) {
				logger.info(
						"Processing request: {}.{} -> action = {}-dataList / {}",
						new Object[] { resourceName, dataFormat,
								Constants.DS_ACTION_RPC, rpcName });
			}

			if (logger.isDebugEnabled()) {

				logger.debug("  --> request-dataList: {} ",
						new Object[] { dataString });
				logger.debug("  --> request-params: {} ",
						new Object[] { paramString });
			}
			this.prepareRequest(request, response);

			this.authorizeDsAction(resourceName, Constants.DS_ACTION_RPC,
					rpcName);

			if (dataFormat.equals("stream")) {
				IDsService<M, F, P> service = this.findDsService(resourceName);
				IDsMarshaller<M, F, P> marshaller = service
						.createMarshaller("json");

				List<M> list = marshaller.readListFromString(dataString);
				P params = marshaller.readParamsFromString(paramString);

				InputStream s = service.rpcDataStream(rpcName, list, params);
				this.sendFile(s, response.getOutputStream());
				return "";
			} else {
				IDsService<M, F, P> service = this.findDsService(resourceName);
				IDsMarshaller<M, F, P> marshaller = service
						.createMarshaller(dataFormat);

				List<M> list = marshaller.readListFromString(dataString);
				P params = marshaller.readParamsFromString(paramString);

				service.rpcData(rpcName, list, params);
				IActionResultSave result = this.packRpcDataListResult(list,
						params);
				stopWatch.stop();
				result.setExecutionTime(stopWatch.getTime());
				return marshaller.writeResultToString(result);
			}

		} catch (Exception e) {
			return this.handleException(e, response);
		} finally {
			this.finishRequest();
		}
	}

	/**
	 * Default handler for remote procedure call on a filter.
	 * 
	 * @param resourceName
	 * @param dataformat
	 * @param dataString
	 * @param paramString
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST, params = {
			Constants.REQUEST_PARAM_ACTION + "=" + Constants.DS_ACTION_RPC,
			Constants.DS_ACTION_RPC + "Type=filter" })
	@ResponseBody
	public String rpcFilter(
			@PathVariable String resourceName,
			@PathVariable String dataFormat,
			@RequestParam(value = Constants.REQUEST_PARAM_SERVICE_NAME_PARAM, required = true) String rpcName,
			@RequestParam(value = Constants.REQUEST_PARAM_DATA, required = false, defaultValue = "{}") String dataString,
			@RequestParam(value = Constants.REQUEST_PARAM_PARAMS, required = false, defaultValue = "{}") String paramString,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {
			StopWatch stopWatch = new StopWatch();
			stopWatch.start();

			if (logger.isInfoEnabled()) {
				logger.info(
						"Processing request: {}.{} -> action = {}-filter / {}",
						new Object[] { resourceName, dataFormat,
								Constants.DS_ACTION_RPC, rpcName });
			}

			if (logger.isDebugEnabled()) {
				logger.debug("  --> request-data: {} ",
						new Object[] { dataString });
				logger.debug("  --> request-params: {} ",
						new Object[] { paramString });
			}

			this.prepareRequest(request, response);

			this.authorizeDsAction(resourceName, Constants.DS_ACTION_RPC,
					rpcName);

			IDsService<M, F, P> service = this.findDsService(resourceName);
			IDsMarshaller<M, F, P> marshaller = service
					.createMarshaller(dataFormat);

			F filter = marshaller.readFilterFromString(dataString);
			P params = marshaller.readParamsFromString(paramString);

			service.rpcFilter(rpcName, filter, params);
			IActionResultRpcFilter result = this.packRpcFilterResult(filter,
					params);
			stopWatch.stop();
			result.setExecutionTime(stopWatch.getTime());
			return marshaller.writeResultToString(result);
		} finally {
			this.finishRequest();
		}
	}

	public IActionResultRpcData packRpcDataResult(M data, P params) {
		IActionResultRpcData pack = new ActionResultRpcData();
		pack.setData(data);
		pack.setParams(params);
		return pack;
	}

	public IActionResultRpcFilter packRpcFilterResult(F filter, P params) {
		IActionResultRpcFilter pack = new ActionResultRpcFilter();
		pack.setData(filter);
		pack.setParams(params);
		return pack;
	}

	public IActionResultSave packRpcDataListResult(List<M> data, P params) {
		IActionResultSave pack = new ActionResultSave();
		pack.setData(data);
		pack.setParams(params);
		return pack;
	}

}
