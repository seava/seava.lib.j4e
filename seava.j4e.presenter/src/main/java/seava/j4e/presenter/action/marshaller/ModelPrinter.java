package seava.j4e.presenter.action.marshaller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import seava.j4e.api.session.Session;

public class ModelPrinter {

	private Map<String, SimpleDateFormat> dateFormats;

	public ModelPrinter() {
		this.dateFormats = new HashMap<String, SimpleDateFormat>();
	}

	public String print(String v) {
		return v;
	}

	public String print(Boolean v) {
		return (v) ? "yes" : "no";
	}

	public String print(Integer v) {
		return v.toString();
	}

	public String print(Long v) {
		return v.toString();
	}

	public String print(Float v) {
		return v.toString();
	}

	public String print(BigDecimal v) {
		return v.toString();
	}

	public String print(Date v) {
		return this.getDateFormat("DATE_FORMAT").format(v);
	}

	public String print(Date v, String mask) {
		return this.getDateFormat(mask).format(v);
	}

	private SimpleDateFormat getDateFormat(String mask) {
		if (!this.dateFormats.containsKey(mask)) {
			SimpleDateFormat fmt = Session.user.get().getSettings()
					.getDateFormat("JAVA_" + mask);
			this.dateFormats.put(mask, fmt);
		}
		return this.dateFormats.get(mask);
	}
}
