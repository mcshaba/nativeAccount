package com.example.zexplore.network;

import android.content.Context;

import com.example.zexplore.BuildConfig;
import com.example.zexplore.R;
import com.example.zexplore.model.ZenithBaseResponse;
import com.example.zexplore.util.Constant;
import com.example.zexplore.util.ZxploreApp;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import okhttp3.CertificatePinner;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.zexplore.util.ZxploreApp.getContext;

public class NetworkClient {
    public static Retrofit retrofit = null;
    private static OkHttpClient okHttpClient;
    private static Context context;

    private static Retrofit getClient(String baseUrl){
        if(okHttpClient == null){
            initOkHttp();
        }

        if(retrofit == null){
            Gson builder = new GsonBuilder()
                    .serializeNulls()
                    .excludeFieldsWithoutExposeAnnotation()
                    .registerTypeAdapter(ZenithBaseResponse.class,new Deserializer<ZenithBaseResponse>())
                    .create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(okHttpClient)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(builder))
                    .build();
        }
        return retrofit;
    }

    private static void initOkHttp() {
        CertificatePinner certificatePinner = new CertificatePinner.Builder()
                .add(Constant.SESSION, Constant.SHA)
                .build();

        int REQUEST_TIMEOUT = 10;
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS);

        try{
            httpClient.sslSocketFactory(getSSLConfig().getSocketFactory());

        }catch (Exception e){
            e.printStackTrace();
        }

        httpClient.hostnameVerifier((hostname, sslSession) -> {
            HostnameVerifier hostnameVerifier = HttpsURLConnection.getDefaultHostnameVerifier();
            hostnameVerifier.verify(Constant.SESSION, sslSession);
            return true;
        });

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        if (BuildConfig.DEBUG){
            httpClient.addInterceptor(interceptor);
        }
        httpClient.addInterceptor(chain -> {
            Request original = chain.request();
            Request.Builder requestBuilder = original.newBuilder()
                    .addHeader("Accept", "application/json")
                    .addHeader("Content-Type", "application/json");
            Request request = requestBuilder.build();
            return chain.proceed(request);
        });
        okHttpClient = httpClient.certificatePinner(certificatePinner).build();
    }


    public static NetworkInterface getNetworkInterface(String baseUrl){
        return NetworkClient.getClient(baseUrl).create(NetworkInterface.class);
    }

    public static void resetNetworkClient(){
        if(retrofit != null) retrofit = null;
        if(okHttpClient !=null) okHttpClient = null;
    }

    private static SSLContext getSSLConfig() throws CertificateException, IOException,
            KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        //Load CAs from an InputStream
        CertificateFactory cf = null;
        cf = CertificateFactory.getInstance(Constant.LTTS);
        InputStream cert = context.getResources().openRawResource(R.raw.server_certificate);

        Certificate ca;

        try{
            ca = cf.generateCertificate(cert);
        }finally {
            cert.close();
        }

        // Crerating a KeyStore containing our trusted CAs
        String keyStoreType = KeyStore.getDefaultType();
        KeyStore keyStore = KeyStore.getInstance(keyStoreType);
        keyStore.load(null, null);
        keyStore.setCertificateEntry(Constant.CA, ca);

        //Creating a TrustManager that trust the CAs in our Keystore
        String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
        TrustManagerFactory trustManagerFactory =TrustManagerFactory.getInstance(tmfAlgorithm);
        trustManagerFactory.init(keyStore);

        //Creating an SSLSocketFactory that uses our TrustManager
        SSLContext sslContext = SSLContext.getInstance(Constant.LTS);
        sslContext.init(null, trustManagerFactory.getTrustManagers(), null);
        return  sslContext;
    }

}
