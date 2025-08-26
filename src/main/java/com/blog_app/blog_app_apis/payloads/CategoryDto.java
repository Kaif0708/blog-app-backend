package com.blog_app.blog_app_apis.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDto {
    private Integer categoryId;
    @NotEmpty
    @Size(min = 3,message = "Title should be minimum 3 characters.")
    private String categoryTitle;
    @NotEmpty
    @Size(min = 3,max=20,message = "Description should be min 3 and max 20 characters.")
    private String categoryDescription;
}
