package com.github.djunqueirao;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public abstract class ApiRequestEncoder {
	public static String encode(final String format, final Object... args) {
		Object[] encodedArgs = args;
		for (int i = 0; i < encodedArgs.length; i++) {
			encodedArgs[i] = URLEncoder.encode("" + encodedArgs[i], StandardCharsets.UTF_8);
		}
		return String.format(format, encodedArgs);
	}
}
