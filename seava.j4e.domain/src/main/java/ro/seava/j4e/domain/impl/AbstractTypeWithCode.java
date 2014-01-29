/**
 * DNet eBusiness Suite
 * Copyright: 2010-2013 Nan21 Electronics SRL. All rights reserved.
 * Use is subject to license terms.
 */
package ro.seava.j4e.domain.impl;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;

import ro.seava.j4e.api.Constants;
import ro.seava.j4e.api.model.IModelWithCode;
import ro.seava.j4e.domain.impl.AbstractType;

import org.hibernate.validator.constraints.NotBlank;

@MappedSuperclass
public abstract class AbstractTypeWithCode extends AbstractType implements
		IModelWithCode {

	private static final long serialVersionUID = -8865917134914502125L;

	public int _code_allocation_mode() {
		return Constants.ENTITY_CODE_DERIVED;
	}

	@NotBlank
	@Column(name = "CODE", nullable = false, length = 64)
	private String code;

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@PrePersist
	public void prePersist() {
		super.prePersist();
	}
}
