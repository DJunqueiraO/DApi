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
    
    private static final String GET = "GET";
    private static final String POST = "POST";
    private static final String PUT = "PUT";
    private static final String DELETE = "DELETE";
    
    DapiOnConnect onConnect = new DapiOnConnect() {
        @Override
        public void accept(HttpsURLConnection connection) {}
    };

    public DapiRequestManager(String url) {
        this.url = url;
    }

    public void setOnConnect(DapiOnConnect onConnect) {
        this.onConnect = onConnect;
    }

    public void setSSLVerification(final boolean enabled) {
        if (enabled) {
            SSLSocketFactory defaultSSLSocketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            HttpsURLConnection.setDefaultSSLSocketFactory(defaultSSLSocketFactory);
        } else {
            try {
                SSLContext sslContext = SSLContext.getInstance("SSL");
                sslContext.init(null, new TrustManager[] { new UnTrustManager() }, new java.security.SecureRandom());
                HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
            } catch (NoSuchAlgorithmException | KeyManagementException e) {
                e.printStackTrace();
            }
        }
    }

    private HttpURLConnection getHttpURLConnection(String endPoint) {
        try {
            URL target = new URL(this.url + endPoint);
            HttpURLConnection connection = (HttpURLConnection) target.openConnection();

            connection.setRequestProperty(
                DapiRequestProperty.Key.CONTENT_TYPE,
                DapiRequestProperty.Value.APPLICATION_JSON
            );
            connection.setRequestProperty("Accept", DapiRequestProperty.Value.APPLICATION_JSON);

            if (connection instanceof HttpsURLConnection) {
                onConnect.accept((HttpsURLConnection) connection);
            }

            return connection;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public DapiRequestResponse get(String endPoint) {
        return request(GET, endPoint, null, "UTF-8");
    }

    public DapiRequestResponse get(String endPoint, String model) {
        return request(GET, endPoint, model, "UTF-8");
    }

    public DapiRequestResponse get(String endPoint, String model, String charsetName) {
        return request(GET, endPoint, model, charsetName);
    }

    public DapiRequestResponse post(String endPoint) {
        return request(POST, endPoint, null, "UTF-8");
    }

    public DapiRequestResponse post(String endPoint, String model) {
        return request(POST, endPoint, model, "UTF-8");
    }

    public DapiRequestResponse post(String endPoint, String model, String charsetName) {
        return request(POST, endPoint, model, charsetName);
    }

    public DapiRequestResponse put(String endPoint) {
        return request(PUT, endPoint, null, "UTF-8");
    }

    public DapiRequestResponse put(String endPoint, String model) {
        return request(PUT, endPoint, model, "UTF-8");
    }

    public DapiRequestResponse put(String endPoint, String model, String charsetName) {
        return request(PUT, endPoint, model, charsetName);
    }

    public DapiRequestResponse delete(String endPoint) {
        return request(DELETE, endPoint, null, "UTF-8");
    }

    public DapiRequestResponse delete(String endPoint, String model) {
        return request(DELETE, endPoint, model, "UTF-8");
    }

    public DapiRequestResponse delete(String endPoint, String model, String charsetName) {
        return request(DELETE, endPoint, model, charsetName);
    }

    private DapiRequestResponse request(String method, String endPoint, String model, String charsetName) {
        HttpURLConnection connection = null;
        DapiRequestResponse response = new DapiRequestResponse();

        try {
            connection = getHttpURLConnection(endPoint);
            if (connection == null) {
                throw new IOException("Could not open connection (null). Check URL/endPoint.");
            }

            connection.setRequestMethod(method);

            if (model != null) {
                connection.setRequestProperty(
                    DapiRequestProperty.Key.CONTENT_TYPE,
                    DapiRequestProperty.Value.APPLICATION_JSON + "; charset=" + charsetName
                );

                new DapiOutputStream(connection).writeFlush(model, charsetName);
            }

            response.setConnection(connection);
            response.setBody(connection, charsetName);

        } catch (ProtocolException e) {
            response.setError(e);
        } catch (IOException e) {
            response.setError(e);
        } catch (RuntimeException e) {
            response.setError(e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return response;
    }
}
