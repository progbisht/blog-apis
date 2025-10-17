package com.myProject.blogApis.payloads;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {

    private int id;

    @NotEmpty
    @Size(min = 4, message = "User name must be of 4 characters.")
    private String name;

    @Email(message = "Email address is not valid!!")
    private String email;

    @NotEmpty
    @Size(min = 3, max = 10, message = "Password must be between 3 to 10 characters.")
//    @Pattern(regexp = "")
    private String password;

    @NotEmpty
    private String about;

}
