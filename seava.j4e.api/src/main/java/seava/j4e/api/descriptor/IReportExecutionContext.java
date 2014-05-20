package seava.j4e.api.descriptor;

import java.io.File;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Map;

public interface IReportExecutionContext {

	public String getFormat();

	public void setFormat(String format);

	public File getResultFile();

	public void setResultFile(File resultFile);

	public OutputStream getResultOutputStream();

	public void setResultOutputStream(OutputStream resultOutputStream);

	public PrintWriter getResultWriter();

	public void setResultWriter(PrintWriter resultWriter);

	public Map<String, Object> getParameters();

	public void setParameters(Map<String, Object> parameters);

}
