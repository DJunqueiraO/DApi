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

   public RequestResponse(RequestResponse<Object> apiRequestResponse) {
      this.setBody(apiRequestResponse.getBody());
      this.setError(apiRequestResponse.getError());
      this.setConnection(apiRequestResponse.getConnection());
   }

   public RequestResponse() {
   }

   protected HttpURLConnection getConnection() {
      return this.connection;
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

   protected void setData(T data) {
      this.data = data;
   }

   protected void setBody(String body) {
      this.body = body;
   }

   protected void setBody(HttpURLConnection connection, String charsetName) {
      String result = "";
      try {
         InputStream inputStream = connection.getResponseCode() >= 200 && connection.getResponseCode() < 300 ? connection.getInputStream() : connection.getErrorStream();
         BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, charsetName));
         for(String line = ""; (line = bufferedReader.readLine()) != null; result = result + line) {
         }
         bufferedReader.close();
      } catch (IOException e) {
         e.printStackTrace();
      }
      this.body = result;
   }

   public Exception getError() {
      return this.error;
   }

   public int getCode() {
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

   public T getData() {
      return this.data;
   }
}
