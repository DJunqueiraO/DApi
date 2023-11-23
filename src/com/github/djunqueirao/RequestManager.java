package com.github.djunqueirao;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

public class RequestManager {
	
   final String url;

   public RequestManager(String url) {
      this.url = url;
   }

   private HttpURLConnection getHttpURLConnection(String endPoint) {
      HttpURLConnection httpURLConnection = null;
      try {
         httpURLConnection = (HttpURLConnection)(new URL(this.url + endPoint)).openConnection();
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

   public <T> RequestResponse<Object> get(String endPoint) {
      return this.get(endPoint, "UTF-8");
   }

   public <T> RequestResponse<T> get(String endPoint, Class<T> model) {
      return this.get(endPoint, model, "UTF-8");
   }

   public <T> RequestResponse<T> get(String endPoint, Class<T> model, String charsetName) {
      RequestResponse<T> response = new RequestResponse<T>(this.get(endPoint, charsetName));
      RequestResponse<Object> responseBody = this.get(endPoint, charsetName);
      response.setData((new Gson()).fromJson(responseBody.getBody(), model));
      return response;
   }

   public RequestResponse<Object> get(String endPoint, String charsetName) {
      HttpURLConnection connection = null;
      RequestResponse<Object> response = new RequestResponse<Object>();
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

   public RequestResponse<Object> post(String endPoint, String model) {
      return this.post(endPoint, model, "UTF-8");
   }

   public RequestResponse<Object> post(String endPoint, String model, String charsetName) {
      HttpURLConnection connection = null;
      OutputStream outputStream = null;
      RequestResponse<Object> response = new RequestResponse<Object>();
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
      } catch (NullPointerException e) {
         response.setError(e);
      } catch (IOException e) {
         response.setError(e);
      } finally {
         if (connection != null) {
            connection.disconnect();
         }

      }
      return response;
   }

   public <T> RequestResponse<T> post(String endPoint, T model) {
      RequestResponse<Object> responseBody = this.post(endPoint, (new Gson()).toJson(model));
      RequestResponse<T> response = new RequestResponse<T>(responseBody);
      return response;
   }

   public <T> RequestResponse<T> put(String endPoint, T model) {
      return this.put(endPoint, model, "UTF-8");
   }

   public <T> RequestResponse<T> put(String endPoint, T model, String charsetName) {
      HttpURLConnection connection = null;
      OutputStream outputStream = null;
      RequestResponse<T> response = new RequestResponse<T>();
      try {
         String jsonInputString = (new Gson()).toJson(model);
         connection = this.getHttpURLConnection(endPoint);
         connection.setRequestMethod("PUT");
         connection.setRequestProperty("Content-Type", "application/json");
         connection.setDoOutput(true);
         outputStream = connection.getOutputStream();
         byte[] input = jsonInputString.getBytes(charsetName);
         outputStream.write(input, 0, input.length);
         outputStream.flush();
         response.setBody(connection, charsetName);
         response.setConnection(connection);
         outputStream.close();
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

      }
      return response;
   }

   public RequestResponse<Object> delete(String endPoint, long id) {
      return this.delete(endPoint, id, "UTF-8");
   }

   public RequestResponse<Object> delete(String endPoint, long id, String charsetName) {
      HttpURLConnection connection = null;
      OutputStream outputStream = null;
      RequestResponse<Object> response = new RequestResponse<Object>();
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
      }
      return response;
   }
}
