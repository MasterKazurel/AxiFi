package cyber.view;

import java.awt.Font;
import java.text.NumberFormat;

public class Format {
	public static Font defFont = new Font("Arial", 12, 24);
	
	public static String toCurrency(double n) {
		return NumberFormat.getCurrencyInstance().format(n);
	}
	
	public static String toCsvLine(String...strs) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i <= strs.length-1; i++)
			builder.append(strs[i].trim() + ",");
		builder.append(strs[strs.length-1]);
		return builder.toString();
	}
}
