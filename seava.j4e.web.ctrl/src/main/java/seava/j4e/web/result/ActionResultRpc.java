/** 
 * DNet eBusiness Suite
 * Copyright: 2013 Nan21 Electronics SRL. All rights reserved.
 * Use is subject to license terms.
 */
package seava.j4e.web.result;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import seava.j4e.api.action.result.IActionResultRpc;

@XmlRootElement(name = "result")
@XmlAccessorType(XmlAccessType.FIELD)
public class ActionResultRpc extends AbstractResultData implements
		IActionResultRpc {

	/**
	 * Data value-object.
	 */
	private Object data;

	/**
	 * Parameters.
	 */
	private Object params;

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Object getParams() {
		return params;
	}

	public void setParams(Object params) {
		this.params = params;
	}

}
