package seava.j4e.presenter.propertyeditors;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class BigDecimalFormatter {

	public static String format(BigDecimal value, String pattern,
			Character decimalSeparator, Character groupingSeparator) {
		if (value == null) {
			return "";
		}
		
		DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(
				Locale.getDefault());
		otherSymbols.setDecimalSeparator(decimalSeparator);
		otherSymbols.setGroupingSeparator(groupingSeparator);
		DecimalFormat df = new DecimalFormat(pattern, otherSymbols);
		return df.format(value);
	}
}
