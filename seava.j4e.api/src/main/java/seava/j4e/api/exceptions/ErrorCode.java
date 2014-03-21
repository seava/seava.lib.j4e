package seava.j4e.api.exceptions;

public enum ErrorCode implements IErrorCode {

	/* ====================== GENERAL 1-899 ============================ */

	G_RUNTIME_ERROR(1),

	// file access

	G_FILE_INVALID_LOCATION(10),
	G_FILE_NOT_FOUND(13),
	G_FILE_NOT_CREATABLE(14),
	G_FILE_NOT_READABLE(15),
	G_FILE_NOT_WRITABLE(16),
	G_FILE_NOT_UPLOADED(17),

	// general

	G_CLIENT_MISMATCH(101),
	G_INSERT_NOT_ALLOWED(110),
	G_UPDATE_NOT_ALLOWED(111),
	G_DELETE_NOT_ALLOWED(112),

	//
	G_NULL_FIELD_CODE(120),
	G_NULL_FIELD_NAME(121),
	G_NULL_FIELD_TYPE(122),

	/* ====================== SECURITY 900-999 ============================ */

	SRV_FILEUPLOAD_SRV_NOT_FOUND(901),
	SRV_PRESENTER_SRV_NOT_FOUND(902),
	SRV_BUSINESS_SRV_NOT_FOUND(903),

	/* ====================== SECURITY 1000-1999 ============================ */

	SEC_NOT_AUTHENTICATED(1001),
	SEC_NOT_AUTHORIZED(1001),

	SEC_SESSION_LOCKED(1011),
	SEC_SESSION_EXPIRED(1012),

	SEC_DIFFERENT_IP(1021),
	SEC_DIFFERENT_USER_AGENT(1022),

	/* ====================== DATABASE 2000-2999 ============================ */
	DB_QUERY_ERROR(2001),
	DB_INSERT_ERROR(2002),
	DB_UPDATE_ERROR(2003),
	DB_DELETE_ERROR(2004),

	DB_CHILD_RECORD_FOUND(2021),
	DB_DUPLICATE_KEY_FOUND(2022);

	private final int errNo;

	private ErrorCode(int errNo) {
		this.errNo = errNo;
	}

	@Override
	public int getErrNo() {
		return errNo;
	}

	@Override
	public String getErrGroup() {
		return "J4E";
	}

	@Override
	public String getErrMsg() {
		return this.name(); // "Translation of error no: " + errNo;
	}

}