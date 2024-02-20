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

public class DapiRequestManager {
	final String url;

	public DapiRequestManager(String url) {
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

	public DapiRequestResponse get(String endPoint) {
		return this.get(endPoint, "UTF-8");
	}

	public DapiRequestResponse get(String endPoint, String charsetName) {
		HttpURLConnection connection = null;
		DapiRequestResponse response = new DapiRequestResponse();
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

	public DapiRequestResponse post(String endPoint, String model) {
		return this.post(endPoint, model, "UTF-8");
	}

	public DapiRequestResponse post(String endPoint, String model, String charsetName) {
		HttpURLConnection connection = null;
		DapiOutputStream outputStream = null;
		DapiRequestResponse response = new DapiRequestResponse();
		try {
			connection = this.getHttpURLConnection(endPoint);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setDoOutput(true);
			outputStream = new DapiOutputStream(connection);
			byte[] input = model.getBytes(charsetName);
			outputStream.write(input, 0, input.length);
			outputStream.flush();
			response.setBody(connection, charsetName);
			response.setConnection(connection);
		} catch (NullPointerException e) {
			response.setError(e);
		} catch (IOException e) {
			response.setError(e);
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
			if(outputStream != null) {
				outputStream.close();
			}
		}
		return response;
	}

	public DapiRequestResponse put(String endPoint, final String model) {
		return this.put(endPoint, model, "UTF-8");
	}
	
	public DapiRequestResponse put(String endPoint, final String model, String charsetName) {
		HttpURLConnection connection = null;
		DapiOutputStream outputStream = null;
		DapiRequestResponse response = new DapiRequestResponse();
		try {
			connection = this.getHttpURLConnection(endPoint);
			connection.setRequestMethod("PUT");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setDoOutput(true);
			outputStream = new DapiOutputStream(connection);
			byte[] input = model.getBytes(charsetName);
			outputStream.write(input, 0, input.length);
			outputStream.flush();
			response.setBody(connection, charsetName);
			response.setConnection(connection);
		} catch (NullPointerException e) {
			response.setError(e);
		} catch (ProtocolException e) {
			response.setError(e);
		} catch (IOException e) {
			response.setError(e);
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
			if(outputStream != null) {
				outputStream.close();
			}
		}
		return response;
	}

	public DapiRequestResponse delete(String endPoint, long id) {
		return this.delete(endPoint, id, "UTF-8");
	}

	public DapiRequestResponse delete(String endPoint, long id, String charsetName) {
		HttpURLConnection connection = null;
		DapiOutputStream outputStream = null;
		DapiRequestResponse response = new DapiRequestResponse();
		try {
			connection = this.getHttpURLConnection(endPoint + "/" + id);
			connection.setRequestMethod("DELETE");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setDoOutput(true);
			outputStream = new DapiOutputStream(connection);
			byte[] input = String.format("{\"id\": %d}", id).getBytes(charsetName);
			outputStream.write(input, 0, input.length);
			outputStream.flush();
			response.setBody(connection, charsetName);
			response.setConnection(connection);
		} catch (NullPointerException e) {
			response.setError(e);
		} catch (ProtocolException e) {
			response.setError(e);
		} catch (IOException e) {
			response.setError(e);
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
			if(outputStream != null) {
				outputStream.close();
			}
		}

		return response;
	}
}
