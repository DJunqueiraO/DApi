package com.github.djunqueirao.dapi.request;

import javax.net.ssl.HttpsURLConnection;

public interface DapiOnConnect {
	public void accept(HttpsURLConnection connection);
}
