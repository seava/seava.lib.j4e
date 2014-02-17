package seava.j4e.api.service.business;

import seava.j4e.api.action.impex.IImportDataPackage;

/**
 * Interface for a transactional service to import an {@link IImportDataPackage}
 * . Execution must be started in a transaction, so that all data-files imported
 * from this package can participate in it.
 * 
 * @author amathe
 * 
 */
public interface IImportDataPackageService {

	public void doExecute(IImportDataPackage dataPackage) throws Exception;

	public void doImportDataFile(String xmlString, String dsName,
			String ukFieldName) throws Exception;

	public void doImportDataPackage(String xmlString) throws Exception;
}
