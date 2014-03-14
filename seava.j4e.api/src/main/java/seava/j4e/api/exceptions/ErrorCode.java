package seava.j4e.api.exceptions;

public enum ErrorCode implements IErrorCode {

	/* ====================== GENERAL 1-999 ============================ */

	G_RUNTIME_ERROR(1),

	// file access
	G_FILE_INVALID_LOCATION(10),
	G_FILE_NOT_FOUND(13),
	G_FILE_NOT_CREATABLE(14),
	G_FILE_NOT_READABLE(15),
	G_FILE_NOT_WRITABLE(16),

	// general
	G_CLIENT_MISMATCH(101),
	G_INSERT_NOT_ALLOWED(110),
	G_UPDATE_NOT_ALLOWED(111),
	G_DELETE_NOT_ALLOWED(112),

	//
	G_NULL_FIELD_CODE(120),
	G_NULL_FIELD_NAME(121),
	G_NULL_FIELD_TYPE(122),

	/* ====================== DATABASE 1000-1999 ============================ */

	DB_CHILD_RECORD_FOUND(1001),
	DB_DUPLICATE_KEY_FOUND(1002);

	private final int number;

	private ErrorCode(int number) {
		this.number = number;
	}

	@Override
	public int getNumber() {
		return number;
	}

}