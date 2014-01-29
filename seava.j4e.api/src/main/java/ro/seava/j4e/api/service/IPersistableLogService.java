package ro.seava.j4e.api.service;

import ro.seava.j4e.api.exceptions.BusinessException;

public interface IPersistableLogService {

	public static final String PL_TYPE_JOB = "job";

	public String getType();

	public String insert(IPersistableLog log) throws BusinessException;
}
