package com.mertgolcu.interceptor;

import com.mertgolcu.util.Constants;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class AuthInterceptor implements Interceptor {

    private String apiKey;
    private String authKey;

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getAuthKey() {
        return authKey;
    }

    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }

    public AuthInterceptor(String apiKey, String authKey) {
        this.apiKey = apiKey;
        this.authKey = authKey;
    }


    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request request = chain.request();
        request = request.newBuilder()
                // API_KEY coming from application.properties
                // or client defined
                .addHeader(getAuthKey(), getApiKey())
                .build();
        return chain.proceed(request);
    }
}
