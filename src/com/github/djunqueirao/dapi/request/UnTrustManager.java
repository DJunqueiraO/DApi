package com.github.djunqueirao.dapi.request;

import java.security.cert.CertificateException;

import javax.net.ssl.X509TrustManager;

class UnTrustManager implements X509TrustManager {
	
    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
        return null;
    }
    
	public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType)
			throws CertificateException {
	}
	
	public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType)
			throws CertificateException {
	}
}
