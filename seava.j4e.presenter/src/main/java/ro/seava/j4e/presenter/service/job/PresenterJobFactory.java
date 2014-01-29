/** 
 * DNet eBusiness Suite
 * Copyright: 2013 Nan21 Electronics SRL. All rights reserved.
 * Use is subject to license terms.
 */
package ro.seava.j4e.presenter.service.job;

import ro.seava.j4e.api.service.job.IJob;
import ro.seava.j4e.api.service.job.IJobFactory;
import ro.seava.j4e.presenter.AbstractApplicationContextAware;

public class PresenterJobFactory extends AbstractApplicationContextAware
		implements IJobFactory {

	@Override
	public IJob create(String key) {
		IJob s = this.getApplicationContext().getBean(key, IJob.class);
		return s;
	}

}
