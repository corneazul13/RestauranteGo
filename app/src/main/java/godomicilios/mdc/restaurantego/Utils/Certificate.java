package godomicilios.mdc.restaurantego.Utils;

import com.squareup.okhttp.OkHttpClient;
import java.security.SecureRandom;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Creado por Deimer el 22/08/17.
 */

public class Certificate {

    @SuppressWarnings("null")
    public static OkHttpClient createClient() {
        OkHttpClient client = new OkHttpClient();
        final TrustManager[] certs = new TrustManager[]{new X509TrustManager() {
            @Override
            public void checkClientTrusted(java.security.cert.X509Certificate[] x509Certificates, String s) throws java.security.cert.CertificateException {
                System.out.println(s);
            }
            @Override
            public void checkServerTrusted(java.security.cert.X509Certificate[] x509Certificates, String s) throws java.security.cert.CertificateException {
                System.out.println(s);
            }
            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[0];
            }
        }};
        SSLContext ctx = null;
        try {
            ctx = SSLContext.getInstance("TLS");
            ctx.init(null, certs, new SecureRandom());
        } catch (final java.security.GeneralSecurityException ex) {
            System.out.println(ex.toString());
        }
        try {
            final HostnameVerifier hostnameVerifier = new HostnameVerifier() {
                @Override
                public boolean verify(final String hostname, final SSLSession session) {
                    return true;
                }
            };
            client.setHostnameVerifier(hostnameVerifier);
            assert ctx != null;
            client.setSslSocketFactory(ctx.getSocketFactory());
        } catch (final Exception e) {
            System.out.println(e.toString());
        }
        return client;
    }

}
