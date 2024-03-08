package com.github.djunqueirao.dapi.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

class DapiBufferedReader {

	BufferedReader bufferedReader;

	DapiBufferedReader(final InputStream inputStream, final String charsetName) {
		setBufferedReader(inputStream, charsetName);
	}

	public void setBufferedReader(final InputStream inputStream, final String charsetName) {
		if (inputStream == null) {
			return;
		}
		try {
			final InputStreamReader inputStreamReader = new InputStreamReader(inputStream, charsetName);
			if (inputStreamReader != null) {
				bufferedReader = new BufferedReader(inputStreamReader);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String readLine() {
		if (bufferedReader == null) {
			return "";
		}
		try {
			return bufferedReader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	public void close() {
		if (bufferedReader == null) {
			return;
		}
		try {
			bufferedReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
