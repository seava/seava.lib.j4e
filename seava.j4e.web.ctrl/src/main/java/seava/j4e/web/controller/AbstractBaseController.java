/** 
 * DNet eBusiness Suite
 * Copyright: 2013 Nan21 Electronics SRL. All rights reserved.
 * Use is subject to license terms.
 */
package seava.j4e.web.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import seava.j4e.api.ISettings;
import seava.j4e.api.enums.SysParam;
import seava.j4e.api.exceptions.BusinessException;
import seava.j4e.api.exceptions.ErrorCode;
import seava.j4e.api.exceptions.IErrorCode;
import seava.j4e.api.exceptions.NotAuthorizedRequestException;
import seava.j4e.api.security.IAuthorizationFactory;
import seava.j4e.api.service.IServiceLocator;
import seava.j4e.api.session.ISessionUser;
import seava.j4e.api.session.IUser;
import seava.j4e.api.session.Session;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Root of the abstract controllers hierarchy.
 * 
 * @author amathe
 * 
 */
public abstract class AbstractBaseController implements ApplicationContextAware {

	private ApplicationContext applicationContext;

	/**
	 * System configuration. May be null, use the getter.
	 */
	private ISettings settings;

	/**
	 * Presenter service locator. May be null, use the getter.
	 */
	private IServiceLocator serviceLocator;

	/**
	 * Authorization factory. May be null, use the getter.
	 */
	private IAuthorizationFactory authorizationFactory;

	/**
	 * Default transfer buffer size.
	 */
	protected final static int FILE_TRANSFER_BUFFER_SIZE = 4 * 1024;

	final static Logger logger = LoggerFactory
			.getLogger(AbstractBaseController.class);

	protected void prepareRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ISessionUser sessionUser = null;
		IUser user = null;

		try {
			sessionUser = (ISessionUser) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();

			if (sessionUser.isSessionLocked()) {
				throw new BusinessException(ErrorCode.SEC_SESSION_LOCKED,
						"Session has been locked.");
			}
			user = sessionUser.getUser();
			if (logger.isDebugEnabled()) {
				logger.debug("Working with clientId = " + user.getClientId());
			}
		} catch (ClassCastException e) {
			if (logger.isDebugEnabled()) {
				logger.debug("Not authenticated request denied.");
			}
			throw new BusinessException(ErrorCode.SEC_SESSION_EXPIRED,
					"<b>Session expired.</b>"
							+ "<br> Logout from application and login again.");
		}

		Session.user.set(user);

		boolean checkIp = this.getSettings().getParamAsBoolean(
				SysParam.CORE_SESSION_CHECK_IP.name());

		if (checkIp) {
			if (logger.isDebugEnabled()) {
				logger.debug(SysParam.CORE_SESSION_CHECK_IP.name()
						+ " enabled, checking IP against login IP...");
			}
			String ip = request.getHeader("X-Forwarded-For");
			if (ip != null && !"".equals(ip)) {
				ip = ip.substring(0, ip.indexOf(","));
			} else {
				ip = request.getRemoteAddr();
			}

			if (!sessionUser.getRemoteIp().equals(ip)) {
				logger.debug("Request comes from different IP as expected. Expected: "
						+ sessionUser.getRemoteIp() + ", real " + ip);
				throw new BusinessException(
						ErrorCode.SEC_DIFFERENT_IP,
						"Security settings do not allow to process request. Check log file for details.");
			}
		}

		boolean checkAgent = this.getSettings().getParamAsBoolean(
				SysParam.CORE_SESSION_CHECK_USER_AGENT.name());

		if (checkAgent) {
			if (logger.isDebugEnabled()) {
				logger.debug(SysParam.CORE_SESSION_CHECK_USER_AGENT.name()
						+ " enabled, checking user-agent agianst login user-agent...");
			}
			String agent = request.getHeader("User-Agent");
			if (!sessionUser.getUserAgent().equals(agent)) {
				logger.debug("Request comes from different user-agent as expected. Expected: "
						+ sessionUser.getUserAgent() + ", real " + agent);
				throw new BusinessException(
						ErrorCode.SEC_DIFFERENT_USER_AGENT,
						"Security settings do not allow to process request. Check log file for details.");
			}
		}
	}

	protected void finishRequest() {
		Session.user.set(null);
	}

	/**
	 * Collect parameters from request.
	 * 
	 * ATTENTION!: Only the first value is considered.
	 * 
	 * @param request
	 * @param prefix
	 *            Collect only parameters which start with this prefix
	 * @param suffix
	 *            Collect only parameters which ends with this suffix
	 * @return
	 */
	protected Map<String, String> collectParams(HttpServletRequest request,
			String prefix, String suffix) {
		@SuppressWarnings("unchecked")
		Map<String, String[]> paramMap = (Map<String, String[]>) request
				.getParameterMap();
		Map<String, String> result = new HashMap<String, String>();
		for (Map.Entry<String, String[]> e : paramMap.entrySet()) {
			String k = e.getKey();
			if (prefix != null && k.startsWith(prefix)) {
				if (suffix != null && k.endsWith(suffix)) {
					result.put(k, e.getValue()[0]);
				}
			}
		}
		return result;
	}

	protected ObjectMapper getJsonMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS,
				false);
		mapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
		mapper.configure(
				DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return mapper;
	}

	protected void sendFile(File file, ServletOutputStream outputStream)
			throws IOException {
		this.sendFile(file, outputStream, FILE_TRANSFER_BUFFER_SIZE);
	}

	protected void sendFile(File file, ServletOutputStream outputStream,
			int bufferSize) throws IOException {
		InputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream(file));
			byte[] buf = new byte[bufferSize];
			int bytesRead;
			while ((bytesRead = in.read(buf)) != -1) {
				outputStream.write(buf, 0, bytesRead);
			}
		} finally {
			if (in != null)
				in.close();
		}
		outputStream.flush();
	}

	protected void sendFile(InputStream inputStream,
			ServletOutputStream outputStream) throws IOException {
		this.sendFile(inputStream, outputStream, FILE_TRANSFER_BUFFER_SIZE);
	}

	protected void sendFile(InputStream inputStream,
			ServletOutputStream outputStream, int bufferSize)
			throws IOException {
		try {
			byte[] buf = new byte[bufferSize];
			int bytesRead;
			while ((bytesRead = inputStream.read(buf)) != -1) {
				outputStream.write(buf, 0, bytesRead);
			}
		} finally {
			if (inputStream != null)
				inputStream.close();
		}
		outputStream.flush();
	}

	/* ================ EXCEPTIONS ======================== */

	/**
	 * Generic exception handler
	 * 
	 * @param e
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	protected String handleException(Exception e, HttpServletResponse response)
			throws IOException {

		e.printStackTrace();

		StringBuffer sb = new StringBuffer(" NOT MANAGED EXCEPTION HANDLER ");
		if (e.getLocalizedMessage() != null) {
			sb.append(e.getLocalizedMessage());
		} else if (e.getCause() != null) {
			if (sb.length() > 0) {
				sb.append(" Reason: ");
			}
			sb.append(e.getCause().getLocalizedMessage());
		}
		if (sb.length() == 0) {
			if (e.getStackTrace() != null) {
				response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
				e.printStackTrace(response.getWriter());
				return null;
			}
		}
		response.setStatus(HttpStatus.EXPECTATION_FAILED.value());
		response.getOutputStream().print(sb.toString());
		response.getOutputStream().flush();
		return null;

	}

	protected String handleManagedExceptionAsText(IErrorCode errorCode,
			Exception e, HttpServletResponse response) throws IOException {
		return this.handleManagedException(errorCode, e, response, "txt");
	}

	protected String handleManagedExceptionAsHtml(IErrorCode errorCode,
			Exception e, HttpServletResponse response) throws IOException {
		return this.handleManagedException(errorCode, e, response, "html");
	}

	protected String handleManagedExceptionAsJson(IErrorCode errorCode,
			Exception e, HttpServletResponse response) throws IOException {
		return this.handleManagedException(errorCode, e, response, "json");
	}

	protected String handleManagedException(IErrorCode errorCode, Exception e,
			HttpServletResponse response) throws IOException {
		return this.handleManagedException(errorCode, e, response, "txt");
	}

	/**
	 * Exception
	 * 
	 * @param e
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	protected String handleManagedException(IErrorCode errorCode, Exception e,
			HttpServletResponse response, String outputType) throws IOException {

		IErrorCode err = errorCode;
		Map<String, String> result = new HashMap<String, String>();

		if (!(e instanceof BusinessException)) {
			e.printStackTrace();
		}
		if (err == null) {
			err = ErrorCode.G_RUNTIME_ERROR;
		}

		// if (e instanceof BusinessException) {
		// err = ((BusinessException) e).getErrorCode();
		// } else {
		// if (e.getCause() != null) {
		// Throwable t = e;
		// while (t.getCause() != null) {
		// t = t.getCause();
		// if (t instanceof BusinessException) {
		// err = ((BusinessException) t).getErrorCode();
		// break;
		// }
		// }
		// }
		// }

		result.put("err_group", err.getErrGroup());
		result.put("err_no", err.getErrNo() + "");
		result.put("err_msg", err.getErrMsg());

		// --------------------

		if (e.getCause() != null) {
			Throwable t = e;
			while (t.getCause() != null) {
				t = t.getCause();
			}

			if (t instanceof SQLException) {
				SQLException sqlException = (SQLException) t;
				result.put(
						"msg",
						sqlException.getErrorCode() + " - "
								+ sqlException.getSQLState() + " - "
								+ sqlException.getLocalizedMessage());

			} else {
				result.put("msg", t.getMessage());
			}

		}

		// ---------------------

		if (!result.containsKey("msg")) {
			result.put("msg", e.getMessage());
		} else {
			result.put("details", e.getMessage());
		}

		StringBuffer sb = new StringBuffer();

		if (outputType.matches("txt")) {
			sb.append(result.get("err_group") + "-" + result.get("err_no")
					+ "\n||\n");
			sb.append(result.get("err_msg") + "\n||\n");
			sb.append(result.get("msg") + "\n||\n");
			sb.append(result.get("details") + "\n||\n");
		} else if (outputType.matches("html")) {
			sb.append("<html><body><div>" + result.get("msg")
					+ "</div></body></html>");
		}

		response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		response.getOutputStream().print(sb.toString());
		return null;
	}

	/**
	 * Not authenticated
	 * 
	 * @param e
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	protected String handleNotAuthorizedRequestException(
			NotAuthorizedRequestException e, HttpServletResponse response)
			throws IOException {
		response.setStatus(403);
		if (e.getCause() != null) {
			response.getOutputStream().print(e.getCause().getMessage());
		} else {
			response.getOutputStream().print(e.getMessage());
		}
		return null;
	}

	/* ================= GETTERS - SETTERS ================== */

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	/**
	 * Get system configuration object. If it is null attempts to retrieve it
	 * from Spring context.
	 * 
	 * @return
	 */
	public ISettings getSettings() {
		if (this.settings == null) {
			this.settings = this.getApplicationContext().getBean(
					ISettings.class);
		}
		return settings;
	}

	/**
	 * Set system configuration object
	 * 
	 * @param systemConfig
	 */
	public void setSettings(ISettings settings) {
		this.settings = settings;
	}

	/**
	 * Get presenter service locator. If it is null attempts to retrieve it from
	 * Spring context.
	 * 
	 * @return
	 */
	public IServiceLocator getServiceLocator() {
		if (this.serviceLocator == null) {
			this.serviceLocator = this.getApplicationContext().getBean(
					IServiceLocator.class);
		}
		return serviceLocator;
	}

	/**
	 * Set presenter service locator.
	 * 
	 * @param serviceLocator
	 */
	public void setServiceLocator(IServiceLocator serviceLocator) {
		this.serviceLocator = serviceLocator;
	}

	/**
	 * Get authorization factory. If it is null attempts to retrieve it from
	 * Spring context.
	 * 
	 * @return
	 * @throws Exception
	 */
	public IAuthorizationFactory getAuthorizationFactory() {
		if (this.authorizationFactory == null) {
			this.authorizationFactory = this.getApplicationContext().getBean(
					IAuthorizationFactory.class);
		}
		return authorizationFactory;
	}

	/**
	 * Set authorization factory.
	 * 
	 * @param authorizationFactory
	 */
	public void setAuthorizationFactory(
			IAuthorizationFactory authorizationFactory) {
		this.authorizationFactory = authorizationFactory;
	}

	/**
	 * Authorize an assignment action.
	 * 
	 * @param asgnName
	 * @param action
	 * @throws Exception
	 */
	protected void authorizeAsgnAction(String asgnName, String action)
			throws Exception {
		this.getAuthorizationFactory().getAsgnAuthorizationProvider()
				.authorize(asgnName, action, null);
	}

	/**
	 * Authorize a DS action.
	 * 
	 * @param asgnName
	 * @param action
	 * @throws Exception
	 */
	protected void authorizeDsAction(String dsName, String action,
			String rpcName) throws Exception {
		this.getAuthorizationFactory().getDsAuthorizationProvider()
				.authorize(dsName, action, rpcName);
	}

}
