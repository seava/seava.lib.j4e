/** 
 * DNet eBusiness Suite
 * Copyright: 2013 Nan21 Electronics SRL. All rights reserved.
 * Use is subject to license terms.
 */
package seava.j4e.api.exceptions;

public class BusinessException extends Exception {

	private static final long serialVersionUID = 1L;

	private IErrorCode errorCode;
	private String errorDetails;

	public BusinessException(IErrorCode errorCode, String errorDetails) {
		super(errorDetails);
		this.errorCode = errorCode;
		this.errorDetails = errorDetails;
	}

	public BusinessException(IErrorCode errorCode, Throwable exception) {
		this.errorCode = errorCode;
		this.initCause(exception);
	}

	public BusinessException(IErrorCode errorCode, String errorDetails,
			Throwable exception) {
		this.initCause(exception);
		this.errorCode = errorCode;
		this.errorDetails = errorDetails;
	}

	public IErrorCode getErrorCode() {
		return errorCode;
	}

}
