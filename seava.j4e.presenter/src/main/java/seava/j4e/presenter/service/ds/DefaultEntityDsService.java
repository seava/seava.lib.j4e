/** 
 * DNet eBusiness Suite
 * Copyright: 2013 Nan21 Electronics SRL. All rights reserved.
 * Use is subject to license terms.
 */
package seava.j4e.presenter.service.ds;

import seava.j4e.api.action.impex.IDsExport;
import seava.j4e.api.service.presenter.IDsService;
import seava.j4e.presenter.model.AbstractDsModel;

/**
 * Default base class for an entity-ds service. It can be exposed as an
 * entity-ds presenter service in case the standard functionality is
 * appropriate.
 * 
 * Consider implementing your own custom service which extends
 * {@link AbstractEntityDsService} to customize standard behavior through the
 * provided template methods or necessary overrides.
 * 
 * @author amathe
 * 
 * @param <M>
 * @param <F>
 * @param <P>
 * @param <E>
 */
public class DefaultEntityDsService<M extends AbstractDsModel<E>, F, P, E>
		extends AbstractEntityDsService<M, F, P, E> implements
		IDsService<M, F, P> {

	@Override
	public IDsExport<M> createExporter(String dataFormat) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
