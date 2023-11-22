package com.github.djunqueirao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

import com.google.gson.Gson;

abstract class RequestManager {
	
	final String url;
	
	public RequestManager(final String url) {
			this.url = url;
	}

//	/**
//	 * This method get the request body.
//	 * @return HttpURLConnection
//	 * @param charsetName to encode stream.
//	 */
//	private String getRequestBody(final HttpURLConnection connection, final String charsetName) {
//		InputStream inputStream;
//		String result = "";
//		try {
//			inputStream = (
//					connection.getResponseCode() >= 200 && connection.getResponseCode() < 300? 
//					connection.getInputStream() 
//					: 
//					connection.getErrorStream()
//			);
//			BufferedReader bufferedReader = new BufferedReader(
//					new InputStreamReader(inputStream, charsetName)
//			);
//			String line = "";
//			while((line = bufferedReader.readLine()) != null) {
//				result += line;
//			}
//			bufferedReader.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return result;
//	}
	
	/**
	 * This method opens a connection from an endPoint.
	 * @param endPoint
	 * @return HttpURLConnection
	 */
	private HttpURLConnection getHttpURLConnection(final String endPoint) {
		HttpURLConnection httpURLConnection = null;
		try {
			httpURLConnection = (HttpURLConnection) new URL(url + endPoint).openConnection();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassCastException e) {
			e.printStackTrace();
		} finally {
			if(httpURLConnection != null) httpURLConnection.disconnect();
		}
		return httpURLConnection;
	}
	
	/**
	 * This method performs a GET request.
	 * @param endPoint
	 * @return An object of type ApiRequestResponse that represents the response.
	 */
	protected <T> RequestResponse<Object> get(final String endPoint) {
		return get(endPoint, "UTF-8");
	}
	
	/**
	 * This method performs a GET request.
	 * @param endPoint
	 * @param model of the appropriate type to perform a GET request.
	 * @return An object of type ApiRequestResponse that represents the response.
	 */
	protected <T> RequestResponse<T> get(final String endPoint, Class<T> model) {
		return get(endPoint, model, "UTF-8");
	}
	
	/**
	 * This method performs a GET request.
	 * @param endPoint
	 * @param model of the appropriate type to perform this request.
	 * @param charsetName to perform this request.
	 * @return An object of type ApiRequestResponse that represents the response.
	 */
	protected <T> RequestResponse<T> get(final String endPoint, Class<T> model, final String charsetName) {
		RequestResponse<T> response = new RequestResponse<T>();
		final RequestResponse<Object> responseBody = get(endPoint, charsetName);
		response.setConnection(responseBody.getConnection());
		response.setError(responseBody.getError());
		response.setData(new Gson().fromJson("" + responseBody.getData(), model));
		return response;
	}
	
	/**
	 * This method performs a GET request.
	 * @param endPoint
	 * @param charsetName to perform this request.
	 * @return An object of type ApiRequestResponse that represents the response.
	 */
	protected RequestResponse<Object> get(final String endPoint, final String charsetName) {
		HttpURLConnection connection = null;
		RequestResponse<Object> response = new RequestResponse<Object>();
		try {
			connection = getHttpURLConnection(endPoint);
			connection.setRequestMethod("GET");
			response.setConnection(connection);
			response.setBody(charsetName);
//			final String body = getRequestBody(connection, charsetName);
//			response.setData(body);
		} catch (ProtocolException e) {
			response.setError(e);
		} finally {
			if(connection != null) connection.disconnect();
		}
		
		return response;
	}
	
	/**
	 * This method performs a POST request.
	 * @param endPoint
	 * @param model of the appropriate type to perform this request.
	 * @param charsetName to perform this request.
	 * @return An object of type ApiRequestResponse that represents the response.
	 */
	protected RequestResponse<Object> post(final String endPoint, String model) {
        return post(endPoint, model, "UTF-8");
	}
	
	/**
	 * This method performs a POST request.
	 * @param endPoint
	 * @param model of the appropriate type to perform this request.
	 * @param charsetName to perform this request.
	 * @return An object of type ApiRequestResponse that represents the response.
	 */
	protected RequestResponse<Object> post(final String endPoint, String model, final String charsetName) {
		HttpURLConnection connection = null;
        OutputStream outputStream = null;
        RequestResponse<Object> response = new RequestResponse<Object>();
        try {
    		connection = getHttpURLConnection(endPoint);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            outputStream = connection.getOutputStream();
            byte[] input = model.getBytes(charsetName);
            outputStream.write(input, 0, input.length);
			outputStream.flush();
//			final String body = getRequestBody(connection, charsetName);
			response.setBody(charsetName);
//			response.setData(body);
			response.setConnection(connection);
			outputStream.close();
        } catch (NullPointerException e) {
			response.setError(e);
        } catch (IOException e) {
			response.setError(e);
		} finally {
			if(connection != null) connection.disconnect();
		}
        return response;
	}
	
	/**
	 * This method performs a POST request.
	 * @param endPoint
	 * @param model of the appropriate type to perform this request.
	 * @return An object of type ApiRequestResponse that represents the response.
	 */
	protected <T> RequestResponse<T> post(final String endPoint, T model) {
		final RequestResponse<Object> responseBody = post(endPoint, new Gson().toJson(model));
		RequestResponse<T> response = new RequestResponse<T>(responseBody);
        return response;
	}
	
	/**
	 * This method performs a PUT request.
	 * @param endPoint
	 * @param model of the appropriate type to perform this request.
	 * @return An object of type ApiRequestResponse that represents the response.
	 */
	protected <T> RequestResponse<T> put(final String endPoint, T model) {
        return put(endPoint, model, "UTF-8");
	}
	
	/**
	 * This method performs a PUT request.
	 * @param endPoint
	 * @param model of the appropriate type to perform this request.
	 * @param charsetName to perform this request.
	 * @return An object of type ApiRequestResponse that represents the response.
	 */
	protected <T> RequestResponse<T> put(final String endPoint, T model, final String charsetName) {
		HttpURLConnection connection = null;
        OutputStream outputStream = null;
        RequestResponse<T> response = new RequestResponse<T>();
        try {
    		final String jsonInputString = new Gson().toJson(model);
    		connection = getHttpURLConnection(endPoint);
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            outputStream = connection.getOutputStream();
            byte[] input = jsonInputString.getBytes(charsetName);
            outputStream.write(input, 0, input.length);
            outputStream.flush();
//			response.setBody(getRequestBody(connection, charsetName));
			response.setBody(charsetName);
            response.setConnection(connection);
			outputStream.close();
        } catch (NullPointerException e) {
        	response.setError(e);
        } catch (ProtocolException e) {
        	response.setError(e);
		} catch (IOException e) {
        	response.setError(e);
		} finally {
			if(connection != null) connection.disconnect();
		}
        return response;
	}
	
	/**
	 * This method performs a DELETE request.
	 * @param endPoint
	 * @param id
	 * @return An object of type ApiRequestResponse that represents the response.
	 */
	protected RequestResponse<Object> delete(final String endPoint, final long id) {
        return delete(endPoint, id, "UTF-8");
	}
	
	/**
	 * This method performs a DELETE request.
	 * @param endPoint
	 * @param id
	 * @param charsetName to perform this request.
	 * @return An object of type ApiRequestResponse that represents the response.
	 */
	protected RequestResponse<Object> delete(final String endPoint, final long id, final String charsetName) {
		HttpURLConnection connection = null;
        OutputStream outputStream = null;
		RequestResponse<Object> response = new RequestResponse<Object>();
        try {
    		connection = getHttpURLConnection(endPoint + "/" + id);
            connection.setRequestMethod("DELETE");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            outputStream = connection.getOutputStream();
            byte[] input = String.format(("{\"id\": %d}"), id).getBytes(charsetName);
            outputStream.write(input, 0, input.length);
            outputStream.flush();
//            response.setBody(getRequestBody(connection, charsetName));
            response.setBody(charsetName);
            response.setConnection(connection);
			outputStream.close();
        } catch (NullPointerException e) {
        	response.setError(e);
        } catch (ProtocolException e) {
        	response.setError(e);
		} catch (IOException e) {
        	response.setError(e);
		} finally {
			if(connection != null) connection.disconnect();
		}
        return response;
	}
}