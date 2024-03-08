package com.github.djunqueirao.dapi.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

class DapiBufferedReader {

	BufferedReader bufferedReader;
	
	DapiBufferedReader(final InputStream inputStream, final String charsetName) {
		try {
			bufferedReader = new BufferedReader(new InputStreamReader(inputStream, charsetName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String readLine() {
		try {
			return bufferedReader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	public void close() {
		try {
			bufferedReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
