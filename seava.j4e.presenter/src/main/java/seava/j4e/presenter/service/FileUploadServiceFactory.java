/** 
 * DNet eBusiness Suite
 * Copyright: 2013 Nan21 Electronics SRL. All rights reserved.
 * Use is subject to license terms.
 */
package seava.j4e.presenter.service;

import seava.j4e.api.service.IFileUploadService;
import seava.j4e.api.service.IFileUploadServiceFactory;
import seava.j4e.presenter.AbstractApplicationContextAware;

/**
 * 
 * @author amathe
 * 
 */
public class FileUploadServiceFactory extends AbstractApplicationContextAware
		implements IFileUploadServiceFactory {

	@Override
	public IFileUploadService create(String key) {
		IFileUploadService s = (IFileUploadService) this
				.getApplicationContext().getBean(key);
		return s;
	}

}
