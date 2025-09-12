package com.neonets.Book.payload;


import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationDTO {

    @NotBlank(message = "Field cannot be empty")
    private String firstName;
    @NotBlank(message = "Field cannot be empty")
    private String lastName;
    @NotBlank(message = "Field cannot be empty")
    private String email;
    @NotBlank(message = "Field cannot be empty")
    @Size(min = 8, message = "Password must be at least 8 characters minimum")
    private String password;

}
