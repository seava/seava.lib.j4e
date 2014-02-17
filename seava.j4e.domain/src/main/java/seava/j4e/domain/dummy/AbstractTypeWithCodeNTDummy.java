/**
 * DNet eBusiness Suite
 * Copyright: 2010-2013 Nan21 Electronics SRL. All rights reserved.
 * Use is subject to license terms.
 */
package seava.j4e.domain.dummy;

import javax.persistence.Entity;
import javax.persistence.Table;

import seava.j4e.domain.impl.AbstractTypeWithCodeNT;

@Entity
@Table(name = AbstractTypeWithCodeNTDummy.TABLE_NAME)
public class AbstractTypeWithCodeNTDummy extends AbstractTypeWithCodeNT {

	public static final String TABLE_NAME = "X_DUMMY1";

	private static final long serialVersionUID = -8865917134914502125L;

}
