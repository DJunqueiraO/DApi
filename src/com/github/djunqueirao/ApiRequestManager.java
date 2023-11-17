package com.github.djunqueirao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

import com.google.gson.Gson;

public abstract class ApiRequestManager {
	
	final String url;
	
	public ApiRequestManager(final String url) {
			this.url = url;
	}
	
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
	 * @param model of the appropriate type to perform a GET request.
	 * @return An object of type ApiRequestResponse that represents the response.
	 */
	protected <T> ApiRequestResponse<T> get(final String endPoint, Class<T> model) {
		return get(endPoint, model, "UTF-8");
	}
	
	/**
	 * This method performs a GET request.
	 * @param endPoint
	 * @param model of the appropriate type to perform this request.
	 * @param charsetName to perform this request.
	 * @return An object of type ApiRequestResponse that represents the response.
	 */
	protected <T> ApiRequestResponse<T> get(final String endPoint, Class<T> model, final String charsetName) {
		HttpURLConnection connection = null;
		BufferedReader bufferedReader = null;
		ApiRequestResponse<T> response = new ApiRequestResponse<T>();
		try {
			connection = getHttpURLConnection(endPoint);
			connection.setRequestMethod("GET");
			bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), charsetName));
			response.setConnection(connection);
			response.setData(new Gson().fromJson(bufferedReader, model));
			bufferedReader.close();
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
	 * This method performs a POST request.
	 * @param endPoint
	 * @param model of the appropriate type to perform this request.
	 * @return An object of type ApiRequestResponse that represents the response.
	 */
	protected <T> ApiRequestResponse<T> post(final String endPoint, T model) {
		HttpURLConnection connection = null;
        OutputStream outputStream = null;
        ApiRequestResponse<T> response = new ApiRequestResponse<T>();
        try {
    		final String jsonInputString = new Gson().toJson(model);
    		connection = getHttpURLConnection(endPoint);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            outputStream = connection.getOutputStream();
            byte[] input = jsonInputString.getBytes("utf-8");
            outputStream.write(input, 0, input.length);
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
	 * This method performs a PUT request.
	 * @param endPoint
	 * @param model of the appropriate type to perform this request.
	 * @return An object of type ApiRequestResponse that represents the response.
	 */
	protected <T> ApiRequestResponse<T> put(final String endPoint, T model) {
        return put(endPoint, model, "UTF-8");
	}
	
	/**
	 * This method performs a PUT request.
	 * @param endPoint
	 * @param model of the appropriate type to perform this request.
	 * @param charsetName to perform this request.
	 * @return An object of type ApiRequestResponse that represents the response.
	 */
	protected <T> ApiRequestResponse<T> put(final String endPoint, T model, final String charsetName) {
		HttpURLConnection connection = null;
        OutputStream outputStream = null;
        ApiRequestResponse<T> response = new ApiRequestResponse<T>();
        try {
    		final String jsonInputString = new Gson().toJson(model);
    		connection = getHttpURLConnection(endPoint);
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            outputStream = connection.getOutputStream();
            byte[] input = jsonInputString.getBytes(charsetName);
            outputStream.write(input, 0, input.length);
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
	protected ApiRequestResponse<Object> delete(final String endPoint, final long id) {
        return delete(endPoint, id, "UTF-8");
	}
	
	/**
	 * This method performs a DELETE request.
	 * @param endPoint
	 * @param id
	 * @param charsetName to perform this request.
	 * @return An object of type ApiRequestResponse that represents the response.
	 */
	protected ApiRequestResponse<Object> delete(final String endPoint, final long id, final String charsetName) {
		HttpURLConnection connection = null;
        OutputStream outputStream = null;
		ApiRequestResponse<Object> response = new ApiRequestResponse<Object>();
        try {
    		connection = getHttpURLConnection(endPoint + "/" + id);
            connection.setRequestMethod("DELETE");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            outputStream = connection.getOutputStream();
            byte[] input = String.format(("{\"id\": %d}"), id).getBytes(charsetName);
            outputStream.write(input, 0, input.length);
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