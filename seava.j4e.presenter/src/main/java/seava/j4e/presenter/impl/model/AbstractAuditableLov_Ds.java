package seava.j4e.presenter.impl.model;

import seava.j4e.api.annotation.DsField;
import seava.j4e.api.model.IModelWithClientId;
import seava.j4e.api.model.IModelWithId;
import seava.j4e.presenter.model.AbstractDsModel;

public class AbstractAuditableLov_Ds<E> extends AbstractDsModel<E> implements
		IModelWithId, IModelWithClientId {

	public static final String f_id = "id";
	public static final String f_clientId = "clientId";

	@DsField(noUpdate = true)
	protected String id;

	@DsField()
	protected String clientId;

	public AbstractAuditableLov_Ds() {
		super();
	}

	public AbstractAuditableLov_Ds(E e) {
		super(e);
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClientId() {
		return this.clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

}
