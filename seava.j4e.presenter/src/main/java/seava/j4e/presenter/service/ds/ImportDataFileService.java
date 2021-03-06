package seava.j4e.presenter.service.ds;

import seava.j4e.api.action.impex.IImportDataFile;
import seava.j4e.api.service.presenter.IImportDataFileService;
import seava.j4e.presenter.service.AbstractPresenterBaseService;

public class ImportDataFileService extends AbstractPresenterBaseService
		implements IImportDataFileService {

	/**
	 * Import one data-file from a data-package. Locate the proper ds-service to
	 * delegate the work to
	 */
	public void execute(IImportDataFile dataFile) throws Exception {

		String dsName = dataFile.getDs();
		String fileName = dataFile.getFile();

		if (dataFile.getUkFieldName() != null
				&& !dataFile.getUkFieldName().equals("")) {
			this.findDsService(dsName).doImport(fileName,
					dataFile.getUkFieldName(), 0, null);
		} else {
			this.findDsService(dsName).doImport(fileName, null);
		}
	}
}