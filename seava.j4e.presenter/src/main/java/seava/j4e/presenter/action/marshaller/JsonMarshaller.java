/** 
 * DNet eBusiness Suite
 * Copyright: 2013 Nan21 Electronics SRL. All rights reserved.
 * Use is subject to license terms.
 */
package seava.j4e.presenter.action.marshaller;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.List;

import seava.j4e.api.Constants;
import seava.j4e.api.action.impex.IExportInfo;
import seava.j4e.api.action.query.IFilterRule;
import seava.j4e.api.action.query.ISortToken;
import seava.j4e.api.action.result.IActionResultDelete;
import seava.j4e.api.action.result.IActionResultFind;
import seava.j4e.api.action.result.IActionResultRpcData;
import seava.j4e.api.action.result.IActionResultRpcFilter;
import seava.j4e.api.action.result.IActionResultSave;
import seava.j4e.api.action.result.IDsMarshaller;
import seava.j4e.commons.action.query.FilterRule;
import seava.j4e.commons.action.query.SortToken;
import seava.j4e.presenter.action.impex.ExportInfo;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.type.TypeFactory;

public class JsonMarshaller<M, F, P> extends AbstractMarshaller<M, F, P>
		implements IDsMarshaller<M, F, P> {

	private ObjectMapper mapper;

	public JsonMarshaller(Class<M> modelClass, Class<F> filterClass,
			Class<P> paramClass) {

		this.modelClass = modelClass;
		this.filterClass = filterClass;
		this.paramClass = paramClass;

		this.mapper = new ObjectMapper();
		this.mapper.configure(
				SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);
		this.mapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS,
				false);
		this.mapper
				.configure(
						DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES,
						false);

		SimpleDateFormat sdf = new SimpleDateFormat(
				Constants.get_server_datetime_format());

		this.mapper.getDeserializationConfig().setDateFormat(sdf);
		this.mapper.getSerializationConfig().setDateFormat(sdf);
	}

	@Override
	public IExportInfo readExportInfo(String source) throws Exception {
		return this.mapper.readValue(source, ExportInfo.class);
	}

	@Override
	public List<ISortToken> readSortTokens(String source) throws Exception {
		return this.mapper.readValue(source,
				TypeFactory.collectionType(List.class, SortToken.class));
	}

	@Override
	public List<IFilterRule> readFilterRules(String source) throws Exception {
		return this.mapper.readValue(source,
				TypeFactory.collectionType(List.class, FilterRule.class));
	}

	@Override
	public <T> T readDataFromString(String source, Class<T> type)
			throws Exception {
		return this.mapper.readValue(source, type);
	}

	@Override
	public M readDataFromString(String source) throws Exception {
		return this.mapper.readValue(source, getModelClass());
	}

	@Override
	public List<M> readListFromString(String source) throws Exception {
		return this.mapper.readValue(source,
				TypeFactory.collectionType(List.class, getModelClass()));
	}

	@Override
	public F readFilterFromString(String source) throws Exception {
		return this.mapper.readValue(source, getFilterClass());
	}

	@Override
	public P readParamsFromString(String source) throws Exception {
		if (getParamClass() == null) {
			return null;
		} else {
			return this.mapper.readValue(source, getParamClass());
		}
	}

	@Override
	public <T> List<T> readListFromString(String source, Class<T> type)
			throws Exception {
		return this.mapper.readValue(source,
				TypeFactory.collectionType(List.class, type));
	}

	@Override
	public String writeDataToString(M m) throws Exception {
		return this.mapper.writeValueAsString(m);
	}

	@Override
	public String writeListToString(List<M> list) throws Exception {
		return this.mapper.writeValueAsString(list);
	}

	@Override
	public String writeFilterToString(F f) throws Exception {
		return this.mapper.writeValueAsString(f);
	}

	@Override
	public String writeParamsToString(P p) throws Exception {
		return this.mapper.writeValueAsString(p);
	}

	@Override
	public String writeResultToString(IActionResultFind result)
			throws Exception {
		return this.mapper.writeValueAsString(result);
	}

	@Override
	public String writeResultToString(IActionResultSave result)
			throws Exception {
		return this.mapper.writeValueAsString(result);
	}

	@Override
	public String writeResultToString(IActionResultDelete result)
			throws Exception {
		return this.mapper.writeValueAsString(result);
	}

	@Override
	public String writeResultToString(IActionResultRpcData result)
			throws Exception {
		return this.mapper.writeValueAsString(result);
	}

	@Override
	public String writeResultToString(IActionResultRpcFilter result)
			throws Exception {
		return this.mapper.writeValueAsString(result);
	}

	@Override
	public void writeDataToStream(M m, OutputStream out) throws Exception {
		this.mapper.writeValue(out, m);
	}

	@Override
	public void writeListToStream(List<M> list, OutputStream out)
			throws Exception {
		this.mapper.writeValue(out, list);
	}

	@Override
	public void writeFilterToStream(F f, OutputStream out) throws Exception {
		this.mapper.writeValue(out, f);
	}

	@Override
	public void writeParamsToStream(P p, OutputStream out) throws Exception {
		this.mapper.writeValue(out, p);
	}

	@Override
	public void writeResultToStream(IActionResultFind result, OutputStream out)
			throws Exception {
		this.mapper.writeValue(out, result);
	}

	@Override
	public void writeResultToStream(IActionResultSave result, OutputStream out)
			throws Exception {
		this.mapper.writeValue(out, result);
	}

	@Override
	public void writeResultToStream(IActionResultRpcData result,
			OutputStream out) throws Exception {
		this.mapper.writeValue(out, result);
	}

	@Override
	public void writeResultToStream(IActionResultRpcFilter result,
			OutputStream out) throws Exception {
		this.mapper.writeValue(out, result);
	}

	@Override
	public ObjectMapper getDelegate() {
		return this.mapper;
	}

}
