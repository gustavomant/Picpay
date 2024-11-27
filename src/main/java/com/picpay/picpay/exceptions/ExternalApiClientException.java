package com.picpay.picpay.exceptions;

public class ExternalApiClientException extends RuntimeException {
    public ExternalApiClientException(String message) {
        super(message);
    }

    public ExternalApiClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
