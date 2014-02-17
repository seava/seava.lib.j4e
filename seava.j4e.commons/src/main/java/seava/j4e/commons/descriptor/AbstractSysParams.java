package seava.j4e.commons.descriptor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import seava.j4e.api.descriptor.ISysParamDefinition;
import seava.j4e.api.descriptor.ISysParamDefinitions;

public abstract class AbstractSysParams implements ISysParamDefinitions {

	private Collection<ISysParamDefinition> params;
	private Map<String, String> defaultValues;

	abstract protected void initParams(Collection<ISysParamDefinition> params);

	@Override
	public Collection<ISysParamDefinition> getSysParamDefinitions() {
		if (this.params == null) {
			synchronized (this) {
				if (this.params == null) {
					this.params = new ArrayList<ISysParamDefinition>();
					this.initParams(this.params);
				}
			}
		}
		return params;
	}

	public String getDefaultValue(String name) {
		return this.defaultValues.get(name);
	}

	public Map<String, String> getDefaultValues() {
		return defaultValues;
	}

	public void setDefaultValues(Map<String, String> defaultValues) {
		this.defaultValues = defaultValues;
	}

}
