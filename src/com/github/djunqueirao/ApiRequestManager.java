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
	
	protected <T> ApiRequestResponse<T> get(final String endPoint, Class<T> model) {
		return get(endPoint, model, "UTF-8");
	}
	
	protected <T> ApiRequestResponse<T> get(final String endPoint, Class<T> model, final String charsetname) {
		HttpURLConnection connection = null;
		BufferedReader bufferedReader = null;
		ApiRequestResponse<T> response = new ApiRequestResponse<T>();
		try {
			connection = getHttpURLConnection(endPoint);
			connection.setRequestMethod("GET");
			bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), charsetname));
			response = new ApiRequestResponse<T>(connection, new Gson().fromJson(bufferedReader, model));
			bufferedReader.close();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(connection != null) connection.disconnect();
		}
		
		return response;
	}
	
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
			response = new ApiRequestResponse<T>(connection);
			outputStream.close();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(connection != null) connection.disconnect();
		}
        return response;
	}
	
	protected <T> ApiRequestResponse<T> put(final String endPoint, T model) {
        return put(endPoint, model, "UTF-8");
	}
	
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
            response = new ApiRequestResponse<T>(connection);
			outputStream.close();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(connection != null) connection.disconnect();
		}
        return response;
	}
	
	protected ApiRequestResponse<Object> delete(final String endPoint, final long id) {
        return delete(endPoint, id, "UTF-8");
	}
	
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
            response = new ApiRequestResponse<Object>(connection);
			outputStream.close();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(connection != null) connection.disconnect();
		}
        return response;
	}
}