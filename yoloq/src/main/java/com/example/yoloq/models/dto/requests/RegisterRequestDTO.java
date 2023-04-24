package com.example.yoloq.models.dto.requests;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDTO {
    @NotNull(message = "Username cannot be empty and must be between 3 and 20 characters length")
    @Size(min = 3, max = 20)
    private String username;

    @Email(message = "Your email should be valid")
    private String email;

    @NotNull(message = "First name field cannot be empty")
    @Size(min = 3, max = 30)
    private String firstName;

    @NotNull(message = "Last name field cannot be empty")
    @Size(min = 3, max = 30)
    private String lastName;

    @NotNull(message = "You must enter password at least 5 characters length")
    @Size(min = 5)
    private String password;

    private MultipartFile profilePicture;
}
