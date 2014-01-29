/** 
 * DNet eBusiness Suite
 * Copyright: 2013 Nan21 Electronics SRL. All rights reserved.
 * Use is subject to license terms.
 */
package ro.seava.j4e.api.service.presenter;

import ro.seava.j4e.api.ISettings;
import ro.seava.j4e.api.action.result.IDsMarshaller;

/**
 * @author amathe
 * 
 */
public interface IDsBaseService<M, F, P> {

	public Class<M> getModelClass();

	public Class<F> getFilterClass();

	public Class<P> getParamClass();

	public IDsMarshaller<M, F, P> createMarshaller(String dataFormat)
			throws Exception;

	public ISettings getSettings();

	public void setSettings(ISettings settings);

}
