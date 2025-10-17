package com.myProject.blogApis.payloads;

import com.myProject.blogApis.models.Category;
import com.myProject.blogApis.models.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public class PostDto {
    private Integer postId;

    private String postTitle;

    private String content;

    private String imageName;

    private Date addedDate;

    private CategoryDto category;

    private UserDto user;

}
