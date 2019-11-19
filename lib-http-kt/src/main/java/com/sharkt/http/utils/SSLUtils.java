package com.sharkt.http.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;


import com.sharkt.http.ssl.TlsOnlySocketFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

public class SSLUtils {

    private static final String TAG = "SSLCustomSocketFactory";

    private static final String KEY_PASS04 = "ELPWfWdA";
    private static final String FILE04 = "idscertificate.p12";

    public SSLUtils(KeyStore trustStore) throws Throwable {
        super();
    }

    public static SSLSocketFactory getSocketFactory04(Context context) {
        SharedPreferences sp = context.getSharedPreferences("IHCertificateFileInfo", Context.MODE_PRIVATE);
        String filePath = sp.getString("cert_path", FILE04);
        String password = sp.getString("cert_password", KEY_PASS04);
        Log.e(TAG, "filePath = " + filePath);
        Log.e(TAG, "password = " + password);
        try {
            KeyStore ks = KeyStore.getInstance("PKCS12");//KeyStore.getDefaultType() //BKS-V1
            InputStream ins = new FileInputStream(filePath);
            ks.load(ins, password.toCharArray());
            SSLContext sslcontext = SSLContext.getInstance("TLS");
            KeyManagerFactory kmf = KeyManagerFactory.getInstance("X509");
            kmf.init(ks, password.toCharArray());
            KeyManager[] keyManagers = kmf.getKeyManagers();
            sslcontext.init(keyManagers, null, null);

            SSLSocketFactory factory = sslcontext.getSocketFactory();

            return factory;
        } catch (FileNotFoundException e) {
            Log.i(TAG, "fnf" + e.getMessage());
            return getTlsSocketFactory();
        } catch (Throwable e) {
            Log.i(TAG, "throwable" + e.getMessage());
        }
        return (SSLSocketFactory) SSLSocketFactory.getDefault();
    }

    public static SSLSocketFactory getSocketFactory03(Context context, String otp) {

        try {
            KeyStore ks = null;
            ks = KeyStore.getInstance("PKCS12");
            FileInputStream fins = new FileInputStream(context.getFileStreamPath("03cert.p12"));
            ks.load(fins, otp.toCharArray());
            SSLContext sslcontext = SSLContext.getInstance("TLS");
            KeyManagerFactory kmf = KeyManagerFactory.getInstance("X509");
            kmf.init(ks, KEY_PASS04.toCharArray());
            KeyManager[] keyManagers = kmf.getKeyManagers();
            sslcontext.init(keyManagers, null, null);

            SSLSocketFactory factory = sslcontext.getSocketFactory();

            Log.e("", "getSocketFactory success");
            return factory;
        } catch (Throwable e) {
            Log.d(TAG, e.getMessage());
            e.printStackTrace();
        }
        Log.e("", "getSocketFactory failed");

        return (SSLSocketFactory) SSLSocketFactory.getDefault();
    }

    /**
     * 对SSLv3、v2协议进行屏蔽
     *
     * @return
     */
    public static SSLSocketFactory getTlsSocketFactory() {
        X509TrustManager trustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };


        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(
                    new KeyManager[0],
                    new X509TrustManager[]{trustManager},
                    new SecureRandom());

            return new TlsOnlySocketFactory(sslContext.getSocketFactory());

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }

        return (SSLSocketFactory) SSLSocketFactory.getDefault();
    }


    /**
     * OkHttp官方推荐的默认X509TrustManager
     *
     * @return
     */
    public static X509TrustManager getX509TrustManager() {

        TrustManagerFactory trustManagerFactory = null;
        try {
            trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            trustManagerFactory.init((KeyStore) null);
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
        if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
            throw new IllegalStateException("Unexpected default trust managers:"
                    + Arrays.toString(trustManagers));
        }
        return (X509TrustManager) trustManagers[0];
    }

    public static class MyX509TrustManager implements X509TrustManager {
        /*
         * The default X509TrustManager returned by SunX509.  We'll delegate
         * decisions to it, and fall back to the logic in this class if the
         * default X509TrustManager doesn't trust it.
         */
        X509TrustManager sunJSSEX509TrustManager;

        MyX509TrustManager(Context context, String otp) throws Exception {
            // create a "default" JSSE X509TrustManager.
            KeyStore ks = KeyStore.getInstance("PKCS12");
            ks.load(new FileInputStream(context.getFileStreamPath("03cert.p12")), otp.toCharArray());
            TrustManagerFactory tmf = TrustManagerFactory.getInstance("X509");
            tmf.init(ks);
            TrustManager tms[] = tmf.getTrustManagers();
            /*
             * Iterate over the returned trustmanagers, look
			 * for an instance of X509TrustManager.  If found, 
			 * use that as our "default" trust manager. 
			 */
            for (int i = 0; i < tms.length; i++) {
                if (tms[i] instanceof X509TrustManager) {
                    sunJSSEX509TrustManager = (X509TrustManager) tms[i];
                    return;
                }
            }
            /*
             * Find some other way to initialize, or else we have to fail the
			 * constructor. 
			 */
            throw new Exception("Couldn't initialize");
        }

        /*
         * Delegate to the default trust manager.
         */
        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
            try {
                sunJSSEX509TrustManager.checkClientTrusted(chain, authType);
            } catch (CertificateException excep) {
                // do any special handling here, or rethrow exception.
            }
        }

        /*
         * Delegate to the default trust manager.
         */
        public void checkServerTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
            try {
                sunJSSEX509TrustManager.checkServerTrusted(chain, authType);
            } catch (CertificateException excep) {
                /*
                 * Possibly pop up a dialog box asking whether to trust the
				 * cert chain. 
				 */
            }
        }

        /*
         * Merely pass this through.
         */
        public X509Certificate[] getAcceptedIssuers() {
            return sunJSSEX509TrustManager.getAcceptedIssuers();
        }
    }

    public static SSLSocketFactory getPKCS12SocketFactory(InputStream keyStream, String password) {
        try {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(keyStream, password.toCharArray());

            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("X509");
            keyManagerFactory.init(keyStore, KEY_PASS04.toCharArray());

            KeyManager[] keyManagers = keyManagerFactory.getKeyManagers();

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(keyManagers, null, null);

            return sslContext.getSocketFactory();

        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }

        return null;
    }
}
