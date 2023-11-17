package com.github.djunqueirao;

import java.io.IOException;
import java.net.HttpURLConnection;

public class ApiRequestResponse<T> {
	
	private HttpURLConnection connection;
	private T data;
	
	public ApiRequestResponse() {}
	
	public ApiRequestResponse(
			final HttpURLConnection connection,
			final T data
	) {
		super();
		this.connection = connection;
		this.data = data;
	}
	
	public ApiRequestResponse(
			final HttpURLConnection connection
	) {
		super();
		this.connection = connection;
		this.data = null;
	}
	
	public int getCode() {
		try {
			return connection.getResponseCode();
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public String getMessage() {
		try {
			return connection.getResponseMessage();
		} catch (IOException e) {
			e.printStackTrace();
			return e.getMessage();
		} catch (NullPointerException e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}

	public T getData() {
		return data;
	}
}
