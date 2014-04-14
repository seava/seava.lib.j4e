package seava.j4e.web.controller.download;

import java.io.File;
import java.nio.file.Files;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import seava.j4e.api.Constants;
import seava.j4e.api.exceptions.BusinessException;
import seava.j4e.api.exceptions.ErrorCode;
import seava.j4e.api.session.Session;
import seava.j4e.web.controller.AbstractBaseController;

@Controller
public class FileDownloadController extends AbstractBaseController {

	final static Logger logger = LoggerFactory
			.getLogger(FileDownloadController.class);

	@RequestMapping(value = "/{fileName}.{dataFormat}", method = { RequestMethod.GET })
	@ResponseBody
	public String download(@PathVariable String fileName,
			@PathVariable String dataFormat, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			if (logger.isInfoEnabled()) {
				logger.info("Processing request: download export file ",
						new Object[] { fileName });
			}

			this.prepareRequest(request, response);

			String path = Session.user.get().getWorkspace().getTempPath();

			File file = new File(path + "/" + fileName + "." + dataFormat);
			// send file

			if (!file.exists()) {
				throw new BusinessException(ErrorCode.G_FILE_NOT_FOUND,
						"File not found");
			}

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
					+ fileName + "\";");

			this.sendFile(file, response.getOutputStream());
			// return null;

			// delete it
			Files.delete(file.toPath());

			return null;
		} catch (Exception e) {
			throw e;
		} finally {
			this.finishRequest();
		}
	}
}
