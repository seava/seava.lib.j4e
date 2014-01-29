package ro.seava.j4e.api.action.impex;

/**
 * Export descriptor. Includes columns and filter information for the export and
 * print operations
 * 
 * @author amathe
 * 
 */
public interface IExportInfo {

	public void prepare(Class<?> modelClass);

}
