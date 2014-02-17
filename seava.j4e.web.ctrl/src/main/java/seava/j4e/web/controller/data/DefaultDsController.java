/** 
 * DNet eBusiness Suite
 * Copyright: 2013 Nan21 Electronics SRL. All rights reserved.
 * Use is subject to license terms.
 */
package seava.j4e.web.controller.data;

import seava.j4e.api.Constants;

import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = Constants.CTXPATH_DS + "/{resourceName}.{dataFormat}")
public class DefaultDsController<M, F, P> extends
		AbstractDsWriteController<M, F, P> {

}
