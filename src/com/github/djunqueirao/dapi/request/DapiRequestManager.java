package com.github.djunqueirao.dapi.request;

import java.io.IOException;
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
	
	private final String url;
	private DapiOnConnect onConnect = new DapiOnConnect() {
		public void accept(HttpURLConnection connection) {}
	};
	private String defaultCharsetName = "UTF-8";

	public DapiRequestManager(String url) {
		this.url = url;
	}
	
	public String getDefaultCharsetName() {
		return defaultCharsetName;
	}
	
	public void setDefaultCharsetName(String defaultCharsetName) {
		this.defaultCharsetName = defaultCharsetName;
	}
	
	public void setOnConnect(DapiOnConnect onConnect) {
		this.onConnect = onConnect;
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
			httpURLConnection.setRequestProperty(
					DapiRequestProperty.Key.CONTENT_TYPE, DapiRequestProperty.Value.APPLICATION_JSON
			);
			onConnect.accept(httpURLConnection);
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
		return this.get(endPoint, defaultCharsetName);
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
		return this.post(endPoint, model, defaultCharsetName);
	}

	public DapiRequestResponse post(String endPoint, String model, String charsetName) {
		HttpURLConnection connection = null;
		DapiOutputStream outputStream = null;
		DapiRequestResponse response = new DapiRequestResponse();
		try {
			connection = this.getHttpURLConnection(endPoint);
			connection.setRequestMethod("POST");
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
		return this.put(endPoint, model, defaultCharsetName);
	}
	
	public DapiRequestResponse put(String endPoint, final String model, String charsetName) {
		HttpURLConnection connection = null;
		DapiOutputStream outputStream = null;
		DapiRequestResponse response = new DapiRequestResponse();
		try {
			connection = this.getHttpURLConnection(endPoint);
			connection.setRequestMethod("PUT");
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
		return this.delete(endPoint, id, defaultCharsetName);
	}

	public DapiRequestResponse delete(String endPoint, long id, String charsetName) {
		HttpURLConnection connection = null;
		DapiOutputStream outputStream = null;
		DapiRequestResponse response = new DapiRequestResponse();
		try {
			connection = this.getHttpURLConnection(endPoint + "/" + id);
			connection.setRequestMethod("DELETE");
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

	public DapiRequestResponse delete(String endPoint, final String model) {
		return this.put(endPoint, model, defaultCharsetName);
	}
	
	public DapiRequestResponse delete(String endPoint, final String model, String charsetName) {
		HttpURLConnection connection = null;
		DapiOutputStream outputStream = null;
		DapiRequestResponse response = new DapiRequestResponse();
		try {
			connection = this.getHttpURLConnection(endPoint);
			connection.setRequestMethod("DELETE");
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
}
