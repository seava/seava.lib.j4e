package seava.j4e.presenter.test.entityds.qb;

import seava.j4e.api.annotation.Ds;
import seava.j4e.api.annotation.DsField;
import seava.j4e.api.annotation.SortField;
import seava.j4e.presenter.impl.model.AbstractTypeWithCode_Ds;

@Ds(entity = Object.class, jpqlWhere = " e.name not null ", sort = {
		@SortField(field = Model.f_code),
		@SortField(field = Model.f_name, desc = true) })
public class Model extends AbstractTypeWithCode_Ds<Object> {

	@DsField()
	private Integer sequenceNo;

	@DsField(fetch = false, jpqlFilter = "e.sequenceNo > :sequenceNoAlias")
	private Integer sequenceNoAlias;

	@DsField(path = "parent1.name")
	private String parent1Name;

	@DsField(path = "parent2.name", join = "left")
	private String parent2Name;

	@DsField()
	private String title;

	public Model() {
		super();
	}

	public Model(Object e) {
		super(e);
	}

	public Integer getSequenceNo() {
		return this.sequenceNo;
	}

	public void setSequenceNo(Integer sequenceNo) {
		this.sequenceNo = sequenceNo;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getParent1Name() {
		return parent1Name;
	}

	public void setParent1Name(String parent1Name) {
		this.parent1Name = parent1Name;
	}

	public String getParent2Name() {
		return parent2Name;
	}

	public void setParent2Name(String parent2Name) {
		this.parent2Name = parent2Name;
	}

	public Integer getSequenceNoAlias() {
		return sequenceNoAlias;
	}

	public void setSequenceNoAlias(Integer sequenceNoAlias) {
		this.sequenceNoAlias = sequenceNoAlias;
	}

}
