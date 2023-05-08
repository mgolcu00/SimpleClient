package com.mertgolcu.statement;

import com.mertgolcu.util.ResponseCallback;

import kotlin.Pair;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public interface ISimpleClientStatement {


    /**
     * Asynchronously sends a GET request with the provided URL, headers, and query parameters.
     * Returns the response as an instance of the provided class using the callback.
     *
     * @param url      target endpoint URL
     * @param header   single header as a Pair object
     * @param headers  additional headers as a Map
     * @param params   query parameters as a Map
     * @param clazz    class of the expected response type
     * @param callback callback to handle the response
     */
    <T> void get(String url,
                 @Nullable Pair<String, String> header,
                 @Nullable Map<String, String> headers,
                 @Nullable Map<String, String> params,
                 Class<T> clazz,
                 ResponseCallback<T> callback);

    /**
     * Asynchronously sends a GET request with the provided URL and parameters.
     * Returns the response as an instance of the provided class using the callback.
     *
     * @param url      target endpoint URL
     * @param params   query parameters as a Map
     * @param clazz    class of the expected response type
     * @param callback callback to handle the response
     */
    <T> void get(String url,
                 @Nullable Map<String, String> params,
                 Class<T> clazz,
                 ResponseCallback<T> callback);

    /**
     * Asynchronously sends a GET request with the provided URL.
     * Returns the response as an instance of the provided class using the callback.
     *
     * @param url      target endpoint URL
     * @param clazz    class of the expected response type
     * @param callback callback to handle the response
     */
    <T> void get(String url,
                 Class<T> clazz,
                 ResponseCallback<T> callback);

    /**
     * Synchronously sends a GET request with the provided URL, headers, and query parameters.
     * Returns the response as an instance of the provided class.
     *
     * @param url     target endpoint URL
     * @param header  single header as a Pair object
     * @param headers additional headers as a Map
     * @param params  query parameters as a Map
     * @param clazz   class of the expected response type
     * @return the response as an instance of the specified class
     */
    <T> T get(String url,
              @Nullable Pair<String, String> header,
              @Nullable Map<String, String> headers,
              @Nullable Map<String, String> params,
              Class<T> clazz);

    /**
     * Synchronously sends a GET request with the provided URL and parameters.
     * Returns the response as an instance of the provided class.
     *
     * @param url    target endpoint URL
     * @param params query parameters as a Map
     * @param clazz  class of the expected response type
     * @return the response as an instance of the specified class
     */
    <T> T get(String url,
              @Nullable Map<String, String> params,
              Class<T> clazz);

    /**
     * Synchronously sends a GET request with the provided URL.
     * Returns the response as an instance of the provided class.
     *
     * @param url   target endpoint URL
     * @param clazz class of the expected response type
     * @return the response as an instance of the specified class
     */
    <T> T get(String url,
              Class<T> clazz);

    /**
     * Asynchronously sends a POST request with the provided URL, headers, query parameters, and request body.
     * Returns the response as an instance of the provided class using the callback.
     *
     * @param url      target endpoint URL
     * @param header   single header as a Pair object
     * @param headers  additional headers as a Map
     * @param params   query parameters as a Map
     * @param requestBody request body object of type V
     * @param clazz    class of the expected response type
     * @param callback callback to handle the response
     */
    <T, V> void post(String url,
                     @Nullable Pair<String, String> header,
                     @Nullable Map<String, String> headers,
                     @Nullable Map<String, String> params,
                     V requestBody,
                     Class<T> clazz,
                     ResponseCallback<T> callback);

    /**
     * Asynchronously sends a POST request with the provided URL, query parameters, and request body.
     * Returns the response as an instance of the provided class using the callback.
     *
     * @param url      target endpoint URL
     * @param params   query parameters as a Map
     * @param requestBody request body object of type V
     * @param clazz    class of the expected response type
     * @param callback callback to handle the response
     */
    <T, V> void post(String url,
                     @Nullable Map<String, String> params,
                     V requestBody,
                     Class<T> clazz,
                     ResponseCallback<T> callback);

    /**
     * Asynchronously sends a POST request with the provided URL and request body.
     * Returns the response as an instance of the provided class using the callback.
     *
     * @param url      target endpoint URL
     * @param requestBody request body object of type V
     * @param clazz    class of the expected response type
     * @param callback callback to handle the response
     */
    <T, V> void post(String url,
                     V requestBody,
                     Class<T> clazz,
                     ResponseCallback<T> callback);



    /**
     * Synchronously sends a POST request with the provided URL, headers, query parameters, and request body.
     * Returns the response as an instance of the provided class.
     *
     * @param url         target endpoint URL
     * @param header      single header as a Pair object
     * @param headers     additional headers as a Map
     * @param params      query parameters as a Map
     * @param requestBody request body object of type V
     * @param clazz       class of the expected response type
     * @return T the response as an instance of the specified class
     */
    <T, V> T post(String url,
                  @Nullable Pair<String, String> header,
                  @Nullable Map<String, String> headers,
                  @Nullable Map<String, String> params,
                  V requestBody,
                  Class<T> clazz);

    /**
     * Synchronously sends a POST request with the provided URL, query parameters, and request body.
     * Returns the response as an instance of the provided class.
     *
     * @param url         target endpoint URL
     * @param params      query parameters as a Map
     * @param requestBody request body object of type V
     * @param clazz       class of the expected response type
     * @return T the response as an instance of the specified class
     */
    <T, V> T post(String url,
                  @Nullable Map<String, String> params,
                  V requestBody,
                  Class<T> clazz);

    /**
     * Synchronously sends a POST request with the provided URL and request body.
     * Returns the response as an instance of the provided class.
     *
     * @param url         target endpoint URL
     * @param requestBody request body object of type V
     * @param clazz       class of the expected response type
     * @return T the response as an instance of the specified class
     */
    <T, V> T post(String url,
                  V requestBody,
                  Class<T> clazz);
}
