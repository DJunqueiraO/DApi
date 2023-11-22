package com.github.djunqueirao;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public abstract class RequestEncoder {
	
	/**
	 * This method is used to encode a string with parameters for UTF-8.
	 * @param String to format.
	 * @param Values to format.
	 * @return A new string with parameters for a specific charSet.
	 */
	public static String encodeToUtf8(final String format, final Object... args) {
		Object[] encodedArgs = args;
		for (int i = 0; i < encodedArgs.length; i++) {
			try {
				encodedArgs[i] = URLEncoder.encode("" + encodedArgs[i], "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return String.format(format, encodedArgs);
	}
}
