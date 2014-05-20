package seava.j4e.commons.descriptor;

import java.io.File;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Map;

import seava.j4e.api.descriptor.IReportExecutionContext;

public class ReportExecutionContext implements IReportExecutionContext {

	private String format;
	private File resultFile;
	private OutputStream resultOutputStream;
	private PrintWriter resultWriter;
	private Map<String, Object> parameters;

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public File getResultFile() {
		return resultFile;
	}

	public void setResultFile(File resultFile) {
		this.resultFile = resultFile;
	}

	public OutputStream getResultOutputStream() {
		return resultOutputStream;
	}

	public void setResultOutputStream(OutputStream resultOutputStream) {
		this.resultOutputStream = resultOutputStream;
	}

	public PrintWriter getResultWriter() {
		return resultWriter;
	}

	public void setResultWriter(PrintWriter resultWriter) {
		this.resultWriter = resultWriter;
	}

	public Map<String, Object> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, Object> parameters) {
		this.parameters = parameters;
	}

}
