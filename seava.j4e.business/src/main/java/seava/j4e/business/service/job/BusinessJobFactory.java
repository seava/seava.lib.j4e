/** 
 * DNet eBusiness Suite
 * Copyright: 2013 Nan21 Electronics SRL. All rights reserved.
 * Use is subject to license terms.
 */
package seava.j4e.business.service.job;

import seava.j4e.api.service.job.IJob;
import seava.j4e.api.service.job.IJobFactory;
import seava.j4e.business.AbstractApplicationContextAware;

public class BusinessJobFactory extends AbstractApplicationContextAware
		implements IJobFactory {

	@Override
	public IJob create(String key) {
		IJob s = this.getApplicationContext().getBean(key, IJob.class);
		return s;
	}

}
