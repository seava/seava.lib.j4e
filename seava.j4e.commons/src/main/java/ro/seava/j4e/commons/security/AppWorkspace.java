package ro.seava.j4e.commons.security;

import java.io.Serializable;

import ro.seava.j4e.api.session.IWorkspace;

public class AppWorkspace implements IWorkspace, Serializable {

	private static final long serialVersionUID = 690259452784508198L;

	private final String workspacePath;
	private final String importPath;
	private final String exportPath;
	private final String tempPath;

	public AppWorkspace(String workspacePath, String importPath,
			String exportPath, String tempPath) {
		super();
		this.workspacePath = workspacePath;
		this.importPath = importPath;
		this.exportPath = exportPath;
		this.tempPath = tempPath;
	}

	public AppWorkspace(String workspacePath) {
		super();
		this.workspacePath = workspacePath;
		this.importPath = workspacePath + "/import";
		this.exportPath = workspacePath + "/export";
		this.tempPath = workspacePath + "/temp";
	}

	public String getWorkspacePath() {
		return workspacePath;
	}

	public String getImportPath() {
		return importPath;
	}

	public String getExportPath() {
		return exportPath;
	}

	public String getTempPath() {
		return tempPath;
	}

}
