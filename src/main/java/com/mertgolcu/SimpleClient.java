package com.mertgolcu;

import com.mertgolcu.exception.ResponseException;
import com.mertgolcu.statement.ISimpleClientStatement;
import com.mertgolcu.util.ClientMediaType;
import com.mertgolcu.util.ResponseCallback;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import kotlin.Pair;
import okhttp3.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.Duration;
import java.util.Map;
import java.util.Objects;


public class SimpleClient implements ISimpleClientStatement {

    /**
     * AppClient singleton instance
     */
    private static SimpleClient instance;

    /**
     * Moshi for JSON mapping
     */
    private final Moshi moshi;

    /**
     * OkHttpClient for network requests
     */
    private final OkHttpClient client;


    // region instance
    public SimpleClient(Interceptor interceptor) {
        client = createOkHttpClient();
        moshi = createMoshi();
    }

    public SimpleClient() {
        client = createOkHttpClient();
        moshi = createMoshi();
    }

    public static synchronized SimpleClient getInstance() {
        if (instance == null)
            instance = new SimpleClient();
        return instance;
    }

    public static synchronized SimpleClient getInstance(Interceptor interceptor) {
        if (instance == null)
            instance = new SimpleClient(interceptor);
        return instance;
    }

    // endregion

    // region CREATORS
    @NotNull
    @Contract(" -> new")
    private Moshi createMoshi() {
        return new Moshi.Builder()
                .build();
    }

    @NotNull
    private OkHttpClient createOkHttpClient(Interceptor interceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .callTimeout(Duration.ofSeconds(20))
                .readTimeout(Duration.ofSeconds(20))
                .build();
    }

    @NotNull
    private OkHttpClient createOkHttpClient() {
        return new OkHttpClient.Builder()
                .callTimeout(Duration.ofSeconds(20))
                .readTimeout(Duration.ofSeconds(20))
                .build();
    }

    @Nullable
    private <T> T executeSync(Request request, Class<T> clazz) {
        JsonAdapter<T> genericAdapter = moshi.adapter(clazz);
        try (Response response = client.newCall(request).execute()) {
            return genericAdapter
                    .fromJson(Objects.requireNonNull(response.body()).source());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private <T> void execute(Request request, Class<T> clazz, ResponseCallback<T> callback) {
        JsonAdapter<T> genericAdapter = moshi.adapter(clazz);
        client.newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        callback.onFail(e);
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        try {
                            if (response.isSuccessful()) {
                                T responseObject = genericAdapter
                                        .fromJson(Objects.requireNonNull(response.body()).source());
                                callback.onSuccess(responseObject);
                            } else {
                                callback.onFail(new ResponseException(response.message(), response.code()));
                            }
                        } catch (Exception e) {
                            callback.onFail(e);
                        }
                    }
                });
    }

    @NotNull
    private <V> Request createPostRequest(String rawUrl,
                                          @Nullable Pair<String, String> header,
                                          @Nullable Map<String, String> headers,
                                          @Nullable Map<String, String> params,
                                          @NotNull V body) {
        Request.Builder builder = getRequestBuilder(rawUrl, header, headers, params);
        Type requestBodyType = Types.getRawType(body.getClass());
        JsonAdapter<V> jsonAdapter = moshi.adapter(requestBodyType);
        String requestBodyJson = jsonAdapter.toJson(body);
        RequestBody requestBody = RequestBody.create(ClientMediaType.JSON.getMediaType(), requestBodyJson);
        builder.post(requestBody);
        return builder.build();
    }

    @NotNull
    private Request createGetRequest(
            String rawUrl,
            @Nullable Pair<String, String> header,
            @Nullable Map<String, String> headers,
            @Nullable Map<String, String> params
    ) {
        return getRequestBuilder(rawUrl, header, headers, params).build();
    }

    private Request.Builder getRequestBuilder(String rawUrl,
                                              @Nullable Pair<String, String> header,
                                              @Nullable Map<String, String> headers,
                                              @Nullable Map<String, String> params) {
        Request.Builder builder = new Request.Builder();
        builder.url(createUrl(rawUrl, params));
        if (header != null)
            builder.addHeader(header.getFirst(), header.getSecond());
        builder = setHeaders(builder, headers);
        return builder;
    }

    // endregion

    // region Utils
    private Request.Builder setHeaders(Request.Builder builder, @Nullable Map<String, String> headers) {
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                builder.addHeader(entry.getKey(), entry.getValue());
            }
            return builder;
        }
        return builder;
    }

    private Request.Builder setHeader(Request.Builder builder, String name, String value) {
        if (name != null && value != null) {
            builder.addHeader(name, value);
            return builder;
        }
        return builder;
    }

    private HttpUrl createUrl(@NotNull String rawUrl, @Nullable Map<String, String> params) {
        HttpUrl.Builder builder = Objects.requireNonNull(HttpUrl.parse(rawUrl)).newBuilder();
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.addQueryParameter(entry.getKey(), entry.getValue());
            }
        }
        return builder.build();
    }

    // endregion

    // region API

    @Override
    public <T> void get(String url, @Nullable Pair<String, String> header, @Nullable Map<String, String> headers, @Nullable Map<String, String> params, Class<T> clazz, ResponseCallback<T> callback) {
        Request request = createGetRequest(url, header, headers, params);
        execute(request, clazz, callback);
    }

    @Override
    public <T> void get(String url, @Nullable Map<String, String> params, Class<T> clazz, ResponseCallback<T> callback) {
        get(url, null, null, params, clazz, callback);
    }

    @Override
    public <T> void get(String url, Class<T> clazz, ResponseCallback<T> callback) {
        get(url, null, clazz, callback);
    }

    @Override
    public <T> T get(String url, @Nullable Pair<String, String> header, @Nullable Map<String, String> headers, @Nullable Map<String, String> params, Class<T> clazz) {
        Request request = createGetRequest(url, header, headers, params);
        return executeSync(request, clazz);
    }

    @Override
    public <T> T get(String url, @Nullable Map<String, String> params, Class<T> clazz) {
        return get(url, null, null, params, clazz);
    }

    @Override
    public <T> T get(String url, Class<T> clazz) {
        return get(url, null, clazz);
    }

    @Override
    public <T, V> void post(String url, @Nullable Pair<String, String> header, @Nullable Map<String, String> headers, @Nullable Map<String, String> params, V requestBody, Class<T> clazz, ResponseCallback<T> callback) {
        Request request = createPostRequest(url, header, headers, params, requestBody);
        execute(request, clazz, callback);
    }

    @Override
    public <T, V> void post(String url, @Nullable Map<String, String> params, V requestBody, Class<T> clazz, ResponseCallback<T> callback) {
        post(url, null, null, params, requestBody, clazz, callback);
    }

    @Override
    public <T, V> void post(String url, V requestBody, Class<T> clazz, ResponseCallback<T> callback) {
        post(url, null, requestBody, clazz, callback);
    }

    @Override
    public <T, V> T post(String url, @Nullable Pair<String, String> header, @Nullable Map<String, String> headers, @Nullable Map<String, String> params, V requestBody, Class<T> clazz) {
        Request request = createPostRequest(url, header, headers, params, requestBody);
        return executeSync(request, clazz);
    }

    @Override
    public <T, V> T post(String url, @Nullable Map<String, String> params, V requestBody, Class<T> clazz) {
        return post(url, null, null, params, requestBody, clazz);
    }

    @Override
    public <T, V> T post(String url, V requestBody, Class<T> clazz) {
        return post(url, null, requestBody, clazz);
    }
    // endregion

}
