package com.jamesaq12wsx.gymtime.jwt;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

@NoArgsConstructor
@Setter
@Getter
public class SignUpRequest {

    private String email;

    private String emailConfirm;

    private String password;

    private String passwordConfirm;

}
