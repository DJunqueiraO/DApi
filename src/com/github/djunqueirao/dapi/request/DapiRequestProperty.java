package com.github.djunqueirao.dapi.request;

public interface DapiRequestProperty {
	public interface Key {
		public final static String CONTENT_TYPE = "Content-Type";
		public final static String AUTHORIZATION = "Authorization";
	}
	public interface Value {
		public final static String APPLICATION_JSON = "application/json";
	}
}
