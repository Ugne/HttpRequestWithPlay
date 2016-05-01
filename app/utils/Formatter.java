package utils;

import java.text.DecimalFormat;

public class Formatter {

	public static String formatChange(Double change) {
		DecimalFormat df = new DecimalFormat("#.####");
		return df.format(change);
	}
}
