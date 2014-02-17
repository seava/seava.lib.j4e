package seava.j4e.web.controller.data;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import seava.j4e.api.Constants;
import seava.j4e.api.service.business.IImportDataPackageService;
import seava.j4e.web.controller.AbstractBaseController;

import org.apache.commons.lang.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping(value = Constants.CTXPATH_DS)
public class ImportDataPackageController extends AbstractBaseController {

	final static Logger logger = LoggerFactory
			.getLogger(ImportDataPackageController.class);

	/**
	 * Import data from a data-file or a data-package located on the server.
	 * 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/"
			+ Constants.DS_ACTION_IMPORT)
	@ResponseBody
	public String importData(
			@RequestParam(value = "dataPackage", required = false) String dataPackage,
			@RequestParam(value = "dataFile", required = false) String dataFile,
			@RequestParam(value = "dsName", required = false) String dsName,
			@RequestParam(value = "ukFieldName", required = false) String ukFieldName,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		if (logger.isInfoEnabled()) {
			logger.info("Processing request: import data from file(s) located on server");
		}

		if (logger.isDebugEnabled()) {
			if (dataPackage != null) {
				logger.debug("  --> dataPackage: {} ",
						new String[] { dataPackage });
			} else {
				logger.debug(
						"  --> dataFile: {}, dsName: {}, ukFieldName: {} ",
						new String[] { dataFile, dsName, ukFieldName });
			}
		}

		this.prepareRequest(request, response);
		try {

			if (dataPackage != null && !"".equals(dataPackage)) {
				this.getApplicationContext()
						.getBean(IImportDataPackageService.class)
						.doImportDataPackage(dataPackage);
			} else if (dataFile != null && !"".equals(dataFile)) {
				this.getApplicationContext()
						.getBean(IImportDataPackageService.class)
						.doImportDataFile(dataFile, dsName, ukFieldName);
			}

			return "{\"success\":true}";
		} catch (Exception e) {
			return this.handleException(e, response);
		} finally {
			this.finishRequest();
		}

	}
}
