package com.blog_app.blog_app_apis.exceptions;

public class ApiException extends RuntimeException{
    public  ApiException(String message){
        super(message);
    }
    public ApiException(){
        super();
    }
}
