package seava.j4e.business.service.other;

import java.io.File;

import seava.j4e.api.Constants;
import seava.j4e.api.action.impex.IImportDataFile;
import seava.j4e.api.action.impex.IImportDataPackage;
import seava.j4e.api.action.impex.IImportDataSet;
import seava.j4e.api.service.business.IImportDataPackageService;
import seava.j4e.business.service.AbstractBusinessBaseService;
import seava.j4e.commons.action.impex.DataFile;
import seava.j4e.commons.action.impex.DataPackage;

public class ImportDataPackageService extends AbstractBusinessBaseService
		implements IImportDataPackageService {

	/**
	 * Import a data-package.
	 */
	@Override
	public void doExecute(IImportDataPackage dataPackage) throws Exception {

		for (IImportDataSet set : dataPackage.getDataSets()) {

			for (IImportDataFile df : set.getDataFiles()) {

				String fileName = df.getFile();

				if (fileName == null || fileName.equals("")) {
					fileName = df.getDs() + ".csv";
				}
				fileName = dataPackage.getLocation() + File.separator
						+ fileName;
				df.setFile(fileName);

				this.sendMessage(Constants.SPRING_MSG_IMPORT_DATA_FILE, df);
			}
		}
	}

	@Override
	public void doImportDataFile(String xmlString, String dsName,
			String ukFieldName) throws Exception {
		if (xmlString != null && !"".equals(xmlString)) {
			IImportDataPackage dp = null;
			DataFile df = new DataFile();
			df.setDs(dsName);
			df.setFile(xmlString);
			df.setUkFieldName(ukFieldName);
			dp = DataPackage.forDataFile(df);
			if (dp != null) {
				this.doExecute(dp);
			}
		}
	}

	@Override
	public void doImportDataPackage(String xmlString) throws Exception {
		if (xmlString != null && !"".equals(xmlString)) {
			IImportDataPackage dp = null;
			dp = DataPackage.forIndexFile(xmlString);
			if (dp != null) {
				this.doExecute(dp);
			}
		}
	}

}
