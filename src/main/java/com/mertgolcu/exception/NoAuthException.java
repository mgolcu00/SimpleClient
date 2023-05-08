package com.mertgolcu.exception;

public class NoAuthException extends Exception{
    private final String NO_AUTH_MESSAGE= "Api Key is not valid";
    private final String NO_AUTH_MESSAGE_TR= "Api anahatrı geçersiz.";

    @Override
    public String getMessage() {
        return NO_AUTH_MESSAGE;
    }

    @Override
    public String getLocalizedMessage() {
        return NO_AUTH_MESSAGE_TR;
    }
}
