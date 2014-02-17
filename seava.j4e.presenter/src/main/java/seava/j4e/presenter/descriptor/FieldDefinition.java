/** 
 * DNet eBusiness Suite
 * Copyright: 2013 Nan21 Electronics SRL. All rights reserved.
 * Use is subject to license terms.
 */
package seava.j4e.presenter.descriptor;

import seava.j4e.api.descriptor.IFieldDefinition;

public class FieldDefinition implements IFieldDefinition {

	private String name;
	private String className;

	public FieldDefinition() {
		super();
	}

	public FieldDefinition(String name, String className) {
		super();
		this.name = name;
		this.className = className;
	}

	/* (non-Javadoc)
	 * @see seava.j4e.presenter.descriptor.IFieldDefinition#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/* (non-Javadoc)
	 * @see seava.j4e.presenter.descriptor.IFieldDefinition#getClassName()
	 */
	@Override
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

}
