package com.mertgolcu.util;

public interface ResponseCallback<T> {
    void onFail(Exception e);

    void onSuccess(T data);
}
