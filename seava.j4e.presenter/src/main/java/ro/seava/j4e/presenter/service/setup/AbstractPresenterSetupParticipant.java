/** 
 * DNet eBusiness Suite
 * Copyright: 2013 Nan21 Electronics SRL. All rights reserved.
 * Use is subject to license terms.
 */
package ro.seava.j4e.presenter.service.setup;

import java.util.Iterator;
import java.util.List;

import ro.seava.j4e.api.setup.ISetupParticipant;
import ro.seava.j4e.api.setup.ISetupTask;
import ro.seava.j4e.presenter.service.AbstractPresenterBaseService;

/**
 * Abstract base class for services which publish setup related tasks.
 * 
 * @author amathe
 * 
 */
public abstract class AbstractPresenterSetupParticipant extends
		AbstractPresenterBaseService {

	protected String targetName;

	/**
	 * List of contributed tasks.
	 */
	protected List<ISetupTask> tasks;

	/**
	 * Priority in execution.
	 */
	protected int ranking;

	public boolean hasWorkToDo() {
		if (tasks == null) {
			this.init();
		}
		return this.tasks.size() > 0;
	}

	protected abstract void init();

	public List<ISetupTask> getTasks() {
		if (tasks == null) {
			this.init();
		}
		return this.tasks;
	}

	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

	public String getBundleId() {
		return this.getApplicationContext().getId();
	}

	public ISetupTask getTask(String taskId) {
		Iterator<ISetupTask> it = tasks.iterator();
		while (it.hasNext()) {
			ISetupTask t = it.next();
			if (t.getId().equals(taskId)) {
				return t;
			}
		}
		return null;
	}

	protected void beforeExecute() throws Exception {

	}

	public void execute() throws Exception {
		this.beforeExecute();
		this.onExecute();
		this.afterExecute();
	}

	protected void afterExecute() throws Exception {
	}

	protected abstract void onExecute() throws Exception;

	public int getRanking() {
		return ranking;
	}

	public void setRanking(int ranking) {
		this.ranking = ranking;
	}

	public int compareTo(ISetupParticipant sp) {
		return sp.getRanking() - this.ranking;
	}

}
