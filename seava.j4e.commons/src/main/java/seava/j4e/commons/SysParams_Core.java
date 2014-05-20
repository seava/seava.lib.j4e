/**
 * DNet eBusiness Suite
 * Copyright: 2010-2013 Nan21 Electronics SRL. All rights reserved.
 * Use is subject to license terms.
 */
package seava.j4e.commons;

import java.util.Collection;

import seava.j4e.api.descriptor.ISysParamDefinition;
import seava.j4e.api.enums.SysParam;
import seava.j4e.commons.descriptor.AbstractSysParams;
import seava.j4e.commons.descriptor.SysParamDefinition;

public class SysParams_Core extends AbstractSysParams {

	protected void initParams(Collection<ISysParamDefinition> params) {
		SysParam[] list = SysParam.values();
		int l = list.length;
		for (int i = 0; i < l; i++) {
			SysParam p = list[i];
			params.add(new SysParamDefinition(p.name(), p.getTitle(), p
					.getDescription(), p.getDataType(), this.getDefaultValue(p
					.name()), p.getListOfValues()));
		}
	}
}
