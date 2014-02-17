/** 
 * DNet eBusiness Suite
 * Copyright: 2013 Nan21 Electronics SRL. All rights reserved.
 * Use is subject to license terms.
 */
package seava.j4e.web.controller.data;

import seava.j4e.api.Constants;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = Constants.CTXPATH_ASGN + "/{resourceName}.{dataFormat}")
public class DefaultAsgnController<M, F, P> extends
		AbstractAsgnController<M, F, P> {

}
