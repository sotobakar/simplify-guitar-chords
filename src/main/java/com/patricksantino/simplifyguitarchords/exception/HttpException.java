package com.patricksantino.simplifyguitarchords.exception;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HttpException extends RuntimeException {
    private int statusCode;

    public HttpException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }
}
