package seava.j4e.api.action.impex;

import java.util.List;

public interface IImportDataSet {

	public List<IImportDataFile> getDataFiles();

	public void setDataFiles(List<IImportDataFile> dataFiles);
}
