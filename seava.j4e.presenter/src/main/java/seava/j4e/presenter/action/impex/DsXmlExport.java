/** 
 * DNet eBusiness Suite
 * Copyright: 2013 Nan21 Electronics SRL. All rights reserved.
 * Use is subject to license terms.
 */
package seava.j4e.presenter.action.impex;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import seava.j4e.api.action.impex.IDsExport;

public class DsXmlExport<M> extends AbstractDsExport<M> implements IDsExport<M> {

	private String rootTag = "records";

	public DsXmlExport() {
		super();
		this.outFileExtension = "xml";
	}

	@Override
	public void write(M data, boolean isFirst) throws Exception {

		List<ExportField> columns = ((ExportInfo) this.getExportInfo())
				.getColumns();
		Iterator<ExportField> it = columns.iterator();

		StringBuffer sb = new StringBuffer();
		sb.append("<record>");
		while (it.hasNext()) {
			ExportField k = it.next();

			Object x = k._getFieldGetter().invoke(data);
			String tag = k.getName();

			if (x != null) {
				if (x instanceof Date) {
					sb.append("<" + tag + ">"
							+ this.getServerDateFormat().format(x) + "</" + tag
							+ ">");
				} else {
					sb.append("<" + tag + ">" + x.toString() + "</" + tag + ">");
				}
			} else {
				sb.append("<" + tag + "></" + tag + ">");
			}

		}
		sb.append("</record>");
		this.writer.write(sb.toString());

	}

	@Override
	protected void beginContent() throws Exception {
		this.writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		this.writer.write("<" + rootTag + ">");
	}

	@Override
	protected void endContent() throws Exception {
		this.writer.write("</" + rootTag + ">");
	}

	public String getRootTag() {
		return rootTag;
	}

	public void setRootTag(String rootTag) {
		this.rootTag = rootTag;
	}

}
