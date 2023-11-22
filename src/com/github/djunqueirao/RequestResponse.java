package com.github.djunqueirao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public class RequestResponse<T> {
	
	private HttpURLConnection connection;
	private T data;
	private Exception error;
	private String body;

	public RequestResponse(final RequestResponse<Object> apiRequestResponse) {
		setBody(apiRequestResponse.getBody());
		setError(apiRequestResponse.getError());
		setConnection(apiRequestResponse.getConnection());
	}
	
	public RequestResponse() {}
	
	protected HttpURLConnection getConnection() {
		return connection;
	}
	
	protected void setError(Exception error) {
		if(error != null) error.printStackTrace();
		this.error = error;
	}
	
	protected void setConnection(HttpURLConnection connection) {
		this.connection = connection;
	}
	
	protected void setData(T data) {
		this.data = data;
	}
	
	protected void setBody(final String charsetName) {
		setBody(connection, charsetName);
	}
	
	protected void setBody(final HttpURLConnection connection, final String charsetName) {
		InputStream inputStream;
		String result = "";
		try {
			inputStream = (
					connection.getResponseCode() >= 200 && connection.getResponseCode() < 300? 
					connection.getInputStream() 
					: 
					connection.getErrorStream()
			);
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(inputStream, charsetName)
			);
			String line = "";
			while((line = bufferedReader.readLine()) != null) {
				result += line;
			}
			bufferedReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.body = result;
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
		} catch (NullPointerException e) {
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
	
	public String getBody() {
		return body;
	}

	public T getData() {
		return data;
	}
}
