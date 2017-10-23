package com.masbie.travelohealth.util;

/*
 * This <client-android-travelohealth> created by : 
 * Name         : syafiq
 * Date / Time  : 25 August 2017, 12:20 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.gson.GsonBuilder;
import com.masbie.travelohealth.R;
import com.masbie.travelohealth.dao.TokenDao;
import com.masbie.travelohealth.pojo.auth.TokenPojo;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

public class Setting
{
    private static final Setting ourInstance = new Setting();

    private Networking networking;

    private Setting()
    {
        this.networking = new Networking();

        this.networking.setDomain("https://travelohealth.000webhostapp.com");
    }

    static Setting getInstance()
    {
        return ourInstance;
    }

    public static Setting getOurInstance()
    {
        return ourInstance;
    }

    public Networking getNetworking()
    {
        return networking;
    }

    public String getFileProvider()
    {
        return "com.masbie.travelohealth.fileprovider";
    }

    public static class Networking
    {
        private String domain;

        public static SSLContext getSSLSocketFactory(Context context)
        {
            final String CLIENT_TRUST_PASSWORD = "ez24get";
            final String CLIENT_AGREEMENT = "TLS";
            final String CLIENT_TRUST_KEYSTORE = "BKS";
            SSLContext sslContext = null;
            try
            {
                sslContext = SSLContext.getInstance(CLIENT_AGREEMENT);
                TrustManagerFactory trustManager = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                KeyStore tks = KeyStore.getInstance(CLIENT_TRUST_KEYSTORE);
                InputStream is;
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
                {
                    is = context.getResources().openRawResource(R.raw.travelohealth_000webhostapp_com_bks);
                }
                else
                {
                    is = context.getResources().openRawResource(R.raw.travelohealth_000webhostapp_com_v1_bks);
                }
                try
                {
                    tks.load(is, CLIENT_TRUST_PASSWORD.toCharArray());
                }
                finally
                {
                    is.close();
                }
                trustManager.init(tks);
                sslContext.init(null, trustManager.getTrustManagers(), null);
            }
            catch(Exception e)
            {
                e.printStackTrace();
                Timber.e("SslContextFactory", e.getMessage());
            }
            return sslContext;
        }

        public static HostnameVerifier getHostNameVerifier(String domain)
        {
            return new HostnameVerifier()
            {
                @Override public boolean verify(String host, SSLSession sslSession)
                {
                    boolean isVerified = false;
                    switch(host)
                    {
                        case "travelohealth.000webhostapp.com":
                        {
                            isVerified = true;
                            break;
                        }
                    }
                    return isVerified;
                }
            };
        }

        public static OkHttpClient getReservedClient(final Context context, final boolean needToken) throws NullPointerException
        {
            return new okhttp3.OkHttpClient.Builder()
                    .retryOnConnectionFailure(true)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(120, TimeUnit.SECONDS)
                    .sslSocketFactory(Setting.Networking.getSSLSocketFactory(context).getSocketFactory(), systemDefaultTrustManager())
                    .hostnameVerifier(Setting.Networking.getHostNameVerifier(Setting.getOurInstance().getNetworking().getDomain()))
                    .addInterceptor(new Interceptor()
                    {
                        @Override
                        public okhttp3.Response intercept(@NonNull Interceptor.Chain chain) throws IOException
                        {
                            Request original = chain.request();

                            Request.Builder builder = original.newBuilder()
                                                              .header("X-Requested-With", "XMLHttpRequest")
                                                              .header("Content-Type", "application/json; charset=utf-8");
                            if(needToken)
                            {
                                @Nullable TokenPojo token = TokenDao.retrieveToken(context);
                                if((token != null) && (token.getToken() != null))
                                {
                                    builder = builder.header("X-Access-Token", token.getToken());
                                }
                            }

                            builder = builder.method(original.method(), original.body());

                            return chain.proceed(builder.build());
                        }
                    })
                    .build();
        }

        public static X509TrustManager systemDefaultTrustManager()
        {
            try
            {
                TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
                        TrustManagerFactory.getDefaultAlgorithm());
                trustManagerFactory.init((KeyStore) null);
                TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
                if(trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager))
                {
                    throw new IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers));
                }
                return (X509TrustManager) trustManagers[0];
            }
            catch(GeneralSecurityException e)
            {
                throw new AssertionError(); // The system has no TLS. Just give up.
            }
        }

        @NonNull public static Retrofit createDefaultConnection(Context context, GsonBuilder gsonBuilder, boolean needToken)
        {
            return new Retrofit.Builder()
                    .baseUrl(Setting.getOurInstance().getNetworking().getDomain())
                    .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
                    .client(Setting.Networking.getReservedClient(context, true))
                    .build();
        }

        public String getDomain()
        {
            return domain;
        }

        public void setDomain(String domain)
        {
            this.domain = domain;
        }
    }
}
