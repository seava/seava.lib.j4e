package seava.j4e.presenter.impl.model;

import java.util.Date;

import seava.j4e.api.annotation.DsField;
import seava.j4e.api.model.IModelWithClientId;
import seava.j4e.api.model.IModelWithId;
import seava.j4e.presenter.model.AbstractDsModel;

public class AbstractAuditable_Ds<E> extends AbstractDsModel<E> implements
		IModelWithId<String>, IModelWithClientId {

	public static final String f_id = "id";
	public static final String f_clientId = "clientId";
	public static final String f_createdAt = "createdAt";
	public static final String f_modifiedAt = "modifiedAt";
	public static final String f_createdBy = "createdBy";
	public static final String f_modifiedBy = "modifiedBy";
	public static final String f_version = "version";
	public static final String f_refid = "refid";
	public static final String f_entityFqn = "entityFqn";
	public static final String f_entityAlias = "entityAlias";
	public static final String f_active = "active";
	public static final String f_notes = "notes";

	@DsField
	protected Boolean active;

	@DsField
	protected String notes;

	@DsField(noUpdate = true)
	protected String id;

	@DsField(noUpdate = true)
	protected String clientId;

	@DsField(noUpdate = true)
	protected Date createdAt;

	@DsField(noUpdate = true)
	protected Date modifiedAt;

	@DsField(noUpdate = true)
	protected String createdBy;

	@DsField(noUpdate = true)
	protected String modifiedBy;

	@DsField(noUpdate = true)
	protected String refid;

	@DsField
	protected Long version;

	@DsField(noUpdate = true, fetch = false)
	protected String entityAlias;

	@DsField(noUpdate = true, fetch = false)
	protected String entityFqn;

	public AbstractAuditable_Ds() {
		super();
	}

	public AbstractAuditable_Ds(E e) {
		super(e);
	}

	public Boolean getActive() {
		return this.active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getNotes() {
		return this.notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
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

	public Date getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getModifiedAt() {
		return this.modifiedAt;
	}

	public void setModifiedAt(Date modifiedAt) {
		this.modifiedAt = modifiedAt;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Long getVersion() {
		return this.version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public String getEntityAlias() {
		return entityAlias;
	}

	public void setEntityAlias(String entityAlias) {
		this.entityAlias = entityAlias;
	}

	public String getEntityFqn() {
		return entityFqn;
	}

	public void setEntityFqn(String entityFqn) {
		this.entityFqn = entityFqn;
	}

	public String getRefid() {
		return refid;
	}

	public void setRefid(String refid) {
		this.refid = refid;
	}

}
