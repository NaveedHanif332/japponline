package com.japp.online.utills;

import android.content.Context;
import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.japp.online.utills.Network.AuthenticationInterceptor;


public class UrlController {

    private static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.MINUTES)
            .writeTimeout(60, TimeUnit.MINUTES)
            .readTimeout(60, TimeUnit.MINUTES)
            .retryOnConnectionFailure(true)
            .callTimeout(60, TimeUnit.MINUTES)
            .build();
// Warning for STRING_TOO_LARGE Please ignore it it wil not effect the project functionlaity.

    public static String Purchase_code = "34d3ffd3-1404-4777-8082-d763aafaa1ac";
    public static String Custom_Security = "34d3ffd3-911-4777-8082-d763aafaa1ac";
    public static String IP_ADDRESS = "https://jappclassifieds.com/";

//    public static String IP_ADDRESS = "https://www.yourdomainname.com/";//Enter You Ip_Address here here
//    public static String Purchase_code = "Enter the purchase code here";//Enter the purchase code here
//    public static String Custom_Security = "Enter the Custom Security code here";//Enter the Custom Security code here
    public static String Base_URL = IP_ADDRESS + "wp-json/adforest/v1/";

    public static final String LINKEDIN_CLIENT_ID = " 78kf5qc0cab145";//Enter Your LINKEDIN_CLIENT_ID here
    public static final String LINKEDIN_CLIENT_SECRET = "75IU45VZM3AmCi8A";//Enter Your LINKEDIN_CLIENT_SECRET here
    public static final String LINKEDIN_REDIRECT_URL = "https://www.jappclassifieds.com";//Enter Your LINKEDIN_REDIRECT_URL here


    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(Base_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = builder.build();

    public static <S> S createService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }

    public static <S> S createService(
            Class<S> serviceClass, String username, String password, Context context) {
        if (!TextUtils.isEmpty(username)
                && !TextUtils.isEmpty(password)) {
            String authToken = Credentials.basic(username, password);
            return createService(serviceClass, authToken, context);
        }
        return createService(serviceClass, null, null, context);
    }

    public static <S> S createService(
            Class<S> serviceClass, final String authToken, Context context) {
        if (!TextUtils.isEmpty(authToken)) {
            AuthenticationInterceptor interceptor =
                    new AuthenticationInterceptor(authToken, context);

            if (!httpClient.interceptors().contains(interceptor)) {
                httpClient.addInterceptor(interceptor);

                builder.client(httpClient.build());
                retrofit = builder.build();
            }
        }
        return retrofit.create(serviceClass);
    }

    public static Map<String, String> AddHeaders(Context context) {
        Map<String, String> map = new HashMap<>();
        if (SettingsMain.isSocial(context)) {
            map.put("AdForest-Login-Type", "social");
        }
        map.put("Purchase-Code", Purchase_code);
        map.put("custom-security", Custom_Security);
        map.put("Adforest-Lang-Locale", SettingsMain.getLanguageCode());
        map.put("Adforest-Request-From", "android");
        map.put("Content-Type", "application/json");
        map.put("Cache-Control", "max-age=640000");
        map.put("Adforest-Location-ID", SettingsMain.getlocationId());
        return map;
    }

    public static Map<String, String> UploadImageAddHeaders(Context context) {
        Map<String, String> map = new HashMap<>();
        if (SettingsMain.isSocial(context)) {
            map.put("AdForest-Login-Type", "social");
        }
        map.put("Purchase-Code", Purchase_code);
        map.put("custom-security", Custom_Security);
        map.put("Adforest-Lang-Locale", SettingsMain.getLanguageCode());
        map.put("Adforest-Request-From", "android");
        map.put("Cache-Control", "max-age=640000");

        return map;
    }
}
