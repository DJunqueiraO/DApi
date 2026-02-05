package com.github.djunqueirao.dapi.request;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

public class DapiRequestResponse {
	private HttpURLConnection connection;
	private Exception error;
	private String body;

	public DapiRequestResponse(DapiRequestResponse apiRequestResponse) {
		this.setBody(apiRequestResponse.getBody());
		this.setError(apiRequestResponse.getError());
		this.setConnection(apiRequestResponse.getConnection());
	}

	public DapiRequestResponse() {
	}

	protected void setError(Exception error) {
		if (error != null) {
			error.printStackTrace();
		}

		this.error = error;
	}

	protected void setConnection(HttpURLConnection connection) {
		this.connection = connection;
	}

	protected void setBody(String body) {
		this.body = body;
	}

	protected void setBody(HttpURLConnection connection, String charsetName) {
		String result = "";
		DapiBufferedReader bufferedReader = null;
		try {
			InputStream inputStream = connection.getResponseCode() >= 200 && connection.getResponseCode() < 300
					? connection.getInputStream()
					: connection.getErrorStream();
			bufferedReader = new DapiBufferedReader(inputStream, charsetName);
			for (String line = ""; (line = bufferedReader.readLine()) != null; result = result + line) {
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bufferedReader != null) {
				bufferedReader.close();
			}
		}
		this.body = result;
	}

	public HttpURLConnection getConnection() {
		return this.connection;
	}

	public Exception getError() {
		return this.error;
	}

	public int getStatus() {
		try {
			return this.connection.getResponseCode();
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
			return this.connection.getResponseMessage();
		} catch (IOException e) {
			e.printStackTrace();
			return e.getMessage();
		} catch (NullPointerException e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}

	public String getBody() {
		return this.body;
	}
}
