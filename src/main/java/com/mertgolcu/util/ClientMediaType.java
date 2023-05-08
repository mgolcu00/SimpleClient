package com.mertgolcu.util;

import okhttp3.MediaType;

public enum ClientMediaType {


    JSON("application/json; charset=utf-8");
    public final String label;

    ClientMediaType(String label) {
        this.label = label;
    }

    public MediaType getMediaType() {
        return MediaType.parse(label);
    }
}