package com.github.djunqueirao.main;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

class DapiOutputStream {

	OutputStream outputStream;
	
	DapiOutputStream(final HttpURLConnection connection) {
		try {
			this.outputStream = connection.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void write(byte[] input, int i, int length) {
		try {
			outputStream.write(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void flush() {
		try {
			outputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
