package com.jamesaq12wsx.gymtime.model.payload;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Getter
@Setter
public class UsernameAndPasswordAuthenticationRequest {

    @NotBlank
    private String email;

    @NotBlank
    private String password;

}
