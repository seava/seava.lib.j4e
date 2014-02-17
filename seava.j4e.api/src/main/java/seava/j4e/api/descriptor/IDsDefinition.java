/** 
 * DNet eBusiness Suite
 * Copyright: 2013 Nan21 Electronics SRL. All rights reserved.
 * Use is subject to license terms.
 */
package seava.j4e.api.descriptor;

import java.util.List;

public interface IDsDefinition {

	public String getName();

	public void setName(String name);

	public Class<?> getModelClass();

	public void setModelClass(Class<?> modelClass);

	public List<IFieldDefinition> getModelFields();

	public Class<?> getFilterClass();

	public void setFilterClass(Class<?> filterClass);

	public List<IFieldDefinition> getFilterFields();

	public Class<?> getParamClass();

	public void setParamClass(Class<?> paramClass);

	public List<IFieldDefinition> getParamFields();

	public boolean isAsgn();

	public boolean isReadOnly();

	public void setAsgn(boolean isAsgn);

	public void addServiceMethod(String serviceMethod);

	public List<String> getServiceMethods();

}
