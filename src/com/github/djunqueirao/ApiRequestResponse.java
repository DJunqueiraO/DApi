package com.github.djunqueirao;

import java.io.IOException;
import java.net.HttpURLConnection;

public class ApiRequestResponse<T> {
	
	private HttpURLConnection connection;
	private T data;
	private Exception error;
	
	public ApiRequestResponse() {}
	
	protected void setError(Exception error) {
		error.printStackTrace();
		this.error = error;
	}
	
	protected void setConnection(HttpURLConnection connection) {
		this.connection = connection;
	}
	
	protected void setData(T data) {
		this.data = data;
	}
	
	public Exception getError() {
		return error;
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
