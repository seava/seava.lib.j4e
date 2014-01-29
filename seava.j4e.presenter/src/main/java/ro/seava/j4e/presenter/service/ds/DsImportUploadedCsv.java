package ro.seava.j4e.presenter.service.ds;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ro.seava.j4e.api.descriptor.IUploadedFileDescriptor;
import ro.seava.j4e.api.service.IFileUploadService;
import ro.seava.j4e.api.service.presenter.IDsService;
import ro.seava.j4e.presenter.AbstractPresenterBase;
import ro.seava.j4e.presenter.action.impex.ConfigCsvImport;

public class DsImportUploadedCsv extends AbstractPresenterBase implements
		IFileUploadService {

	public static final String KEY_SEPARATOR = "separator";
	public static final String KEY_QUOTE_CHAR = "quoteChar";
	public static final String KEY_ENCODING = "encoding";
	public static final String KEY_DS_NAME = "dsName";

	private List<String> paramNames;

	public DsImportUploadedCsv() {
		super();
		paramNames = new ArrayList<String>();
		paramNames.add(KEY_DS_NAME);
		paramNames.add(KEY_ENCODING);
		paramNames.add(KEY_QUOTE_CHAR);
		paramNames.add(KEY_SEPARATOR);
	}

	@Override
	public Map<String, Object> execute(IUploadedFileDescriptor fileDescriptor,
			InputStream inputStream, Map<String, String> params)
			throws Exception {

		String dsName = params.get(KEY_DS_NAME);
		String separator = params.get(KEY_SEPARATOR);
		String quoteChar = params.get(KEY_QUOTE_CHAR);
		String encoding = params.get(KEY_ENCODING);

		Map<String, Object> result = new HashMap<String, Object>();
		ConfigCsvImport cfg = new ConfigCsvImport();

		if (separator != null) {
			cfg.setSeparator(separator.charAt(0));
		}

		if (quoteChar != null) {
			cfg.setQuoteChar(quoteChar.charAt(0));
		}

		if (encoding != null) {
			cfg.setEncoding(encoding);
		}

		IDsService<?, ?, ?> dsService = this.getServiceLocator().findDsService(
				dsName);
		dsService.doImport(inputStream, fileDescriptor.getOriginalName(), cfg);

		return result;
	}

	@Override
	public List<String> getParamNames() {
		return paramNames;
	}

}
