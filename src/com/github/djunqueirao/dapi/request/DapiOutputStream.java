package com.github.djunqueirao.dapi.request;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.charset.Charset;

final class DapiOutputStream {

	private final HttpURLConnection connection;

	DapiOutputStream(final HttpURLConnection connection) {
		this.connection = connection;
	}

	public void writeFlush(String model, String charsetName) throws IOException {
		if (model == null) {
			return;
		}
		writeFlush(model.getBytes(Charset.forName(charsetName)));
	}

	public void writeFlush(byte[] input) throws IOException {
		if (input == null) {
			return;
		}
		if (connection == null) {
			throw new IOException("HttpURLConnection is null");
		}

		connection.setDoOutput(true);
		connection.setFixedLengthStreamingMode(input.length);

		try (OutputStream os = connection.getOutputStream()) {
			os.write(input, 0, input.length);
			os.flush();
		}
	}

	public void write(byte[] input, int offset, int length) throws IOException {
		if (input == null)
			return;
		if (connection == null)
			throw new IOException("HttpURLConnection is null");

		connection.setDoOutput(true);
		connection.setChunkedStreamingMode(0);

		try (OutputStream os = connection.getOutputStream()) {
			os.write(input, offset, length);
			os.flush();
		}
	}
}