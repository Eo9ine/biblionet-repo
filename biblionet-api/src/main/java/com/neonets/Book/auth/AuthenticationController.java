package com.neonets.Book.auth;


import com.neonets.Book.payload.RegistrationDTO;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private AuthenticationService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<RegistrationDTO> registration(
            @RequestBody @Valid RegistrationDTO registrationRequest
    )
    {
        authService.register(registrationRequest);
        return ResponseEntity.accepted().build();
    }
}
