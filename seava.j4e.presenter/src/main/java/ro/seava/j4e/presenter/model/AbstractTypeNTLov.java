package ro.seava.j4e.presenter.model;

import ro.seava.j4e.api.annotation.DsField;
import ro.seava.j4e.api.model.IModelWithId;
import ro.seava.j4e.presenter.model.AbstractDsModel;

public class AbstractTypeNTLov<E> extends AbstractDsModel<E> implements
		IModelWithId {

	public static final String f_id = "id";
	public static final String f_name = "name";
	public static final String f_active = "active";

	@DsField
	protected String id;

	@DsField
	protected String name;

	@DsField
	protected String description;

	@DsField
	protected Boolean active;

	
	public AbstractTypeNTLov() {
		super();
	}

	public AbstractTypeNTLov(E e) {
		super(e);
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getActive() {
		return this.active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
