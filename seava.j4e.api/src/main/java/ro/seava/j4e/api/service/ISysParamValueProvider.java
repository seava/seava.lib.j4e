package ro.seava.j4e.api.service;

import java.util.Date;
import java.util.Map;

import ro.seava.j4e.api.exceptions.BusinessException;
import ro.seava.j4e.api.exceptions.InvalidDatabase;

public interface ISysParamValueProvider {

	public Map<String, String> getParamValues(Date validAt)
			throws BusinessException, InvalidDatabase;
}
