package com.github.djunqueirao.main;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

public class RequestManager {
	final String url;

	public RequestManager(String url) {
		this.url = url;
	}

	public void setSSLVerification(final boolean enabled) {
		if(enabled) {
			SSLSocketFactory defaultSSLSocketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
			HttpsURLConnection.setDefaultSSLSocketFactory(defaultSSLSocketFactory);
		} else {
			SSLContext sc;
			try {
				sc = SSLContext.getInstance("SSL");
				sc.init(null, new TrustManager[] { new UnTrustManager() }, new java.security.SecureRandom());
				HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (KeyManagementException e) {
				e.printStackTrace();
			}
		}
	}

	private HttpURLConnection getHttpURLConnection(String endPoint) {
		HttpURLConnection httpURLConnection = null;

		try {
			httpURLConnection = (HttpURLConnection) (new URL(this.url + endPoint)).openConnection();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassCastException e) {
			e.printStackTrace();
		} finally {
			if (httpURLConnection != null) {
				httpURLConnection.disconnect();
			}
		}
		return httpURLConnection;
	}

	public RequestResponse get(String endPoint) {
		return this.get(endPoint, "UTF-8");
	}

	public RequestResponse get(String endPoint, String charsetName) {
		HttpURLConnection connection = null;
		RequestResponse response = new RequestResponse();
		try {
			connection = this.getHttpURLConnection(endPoint);
			connection.setRequestMethod("GET");
			response.setConnection(connection);
			response.setBody(connection, charsetName);
		} catch (ProtocolException e) {
			response.setError(e);
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
		return response;
	}

	public RequestResponse post(String endPoint, String model) {
		return this.post(endPoint, model, "UTF-8");
	}

	public RequestResponse post(String endPoint, String model, String charsetName) {
		HttpURLConnection connection = null;
		OutputStream outputStream = null;
		RequestResponse response = new RequestResponse();
		try {
			connection = this.getHttpURLConnection(endPoint);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setDoOutput(true);
			outputStream = connection.getOutputStream();
			byte[] input = model.getBytes(charsetName);
			outputStream.write(input, 0, input.length);
			outputStream.flush();
			response.setBody(connection, charsetName);
			response.setConnection(connection);
			outputStream.close();
		} catch (NullPointerException var12) {
			response.setError(var12);
		} catch (IOException var13) {
			response.setError(var13);
		} finally {
			if (connection != null) {
				connection.disconnect();
			}

		}
		return response;
	}

	public RequestResponse put(String endPoint, final String model) {
		return this.put(endPoint, model, "UTF-8");
	}
	
	public RequestResponse put(String endPoint, final String model, String charsetName) {
		HttpURLConnection connection = null;
		OutputStream outputStream = null;
		RequestResponse response = new RequestResponse();
		try {
			connection = this.getHttpURLConnection(endPoint);
			connection.setRequestMethod("PUT");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setDoOutput(true);
			outputStream = connection.getOutputStream();
			byte[] input = model.getBytes(charsetName);
			outputStream.write(input, 0, input.length);
			outputStream.flush();
			response.setBody(connection, charsetName);
			response.setConnection(connection);
			outputStream.close();
		} catch (NullPointerException var14) {
			response.setError(var14);
		} catch (ProtocolException var15) {
			response.setError(var15);
		} catch (IOException var16) {
			response.setError(var16);
		} finally {
			if (connection != null) {
				connection.disconnect();
			}

		}
		return response;
	}

	public RequestResponse delete(String endPoint, long id) {
		return this.delete(endPoint, id, "UTF-8");
	}

	public RequestResponse delete(String endPoint, long id, String charsetName) {
		HttpURLConnection connection = null;
		OutputStream outputStream = null;
		RequestResponse response = new RequestResponse();
		try {
			connection = this.getHttpURLConnection(endPoint + "/" + id);
			connection.setRequestMethod("DELETE");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setDoOutput(true);
			outputStream = connection.getOutputStream();
			byte[] input = String.format("{\"id\": %d}", id).getBytes(charsetName);
			outputStream.write(input, 0, input.length);
			outputStream.flush();
			response.setBody(connection, charsetName);
			response.setConnection(connection);
			outputStream.close();
		} catch (NullPointerException var14) {
			response.setError(var14);
		} catch (ProtocolException var15) {
			response.setError(var15);
		} catch (IOException var16) {
			response.setError(var16);
		} finally {
			if (connection != null) {
				connection.disconnect();
			}

		}

		return response;
	}
}
