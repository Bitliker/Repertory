package com.gxut.code.network.ssl;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

/**
 * Created by Bitliker on 2017/7/3.
 */

public interface SSLConfig {

    SSLSocketFactory createSSLSocketFactory();

    HostnameVerifier createHostnameVerifier();

    X509TrustManager createTrustAllCerts();
}
