package com.blog_app.blog_app_apis.exceptions;

import lombok.Getter;
import lombok.Setter;

import static java.lang.String.format;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException{
    String resourceName;
    String fieldName;
    long fieldValue;

    public ResourceNotFoundException(String resourceName,String fieldName,long fieldValue) {
        super(format("%s is not found with %s : %s",resourceName,fieldName,fieldValue));
        this.resourceName = resourceName;
        this.fieldValue=fieldValue;
        this.fieldName=fieldName;
    }
}
