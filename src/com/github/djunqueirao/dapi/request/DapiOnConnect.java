package com.github.djunqueirao.dapi.request;

import java.net.HttpURLConnection;

public interface DapiOnConnect {
	public void accept(HttpURLConnection connection);
}
