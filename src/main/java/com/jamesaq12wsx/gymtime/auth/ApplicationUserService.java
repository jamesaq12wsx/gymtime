package com.jamesaq12wsx.gymtime.auth;

import com.jamesaq12wsx.gymtime.database.ApplicationUserRepository;
import com.jamesaq12wsx.gymtime.exception.ApiRequestException;
import com.jamesaq12wsx.gymtime.model.payload.SignUpRequest;
import com.jamesaq12wsx.gymtime.security.PasswordConfig;
import com.jamesaq12wsx.gymtime.validator.EmailValidator;
import com.jamesaq12wsx.gymtime.validator.PasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.jamesaq12wsx.gymtime.security.ApplicationUserRole.USER;

@Service
public class ApplicationUserService implements SelfUserDetailsService {

    private final ApplicationUserRepository applicationUserRepository;

    private final PasswordConfig passwordConfig;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationUserService(ApplicationUserRepository applicationUserRepository, PasswordConfig passwordConfig, PasswordEncoder passwordEncoder) {
        this.applicationUserRepository = applicationUserRepository;
        this.passwordConfig = passwordConfig;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ApplicationUser user = applicationUserRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Email % is not found", username)));

        if (user.getAuthProvider() != AuthProvider.LOCAL){
            throw new BadCredentialsException(
                    String.format("Looks like Email %s has sign up with %s, you should use %s to login",
                            user.getEmail(),
                            user.getAuthProvider().getValue().toLowerCase(),
                            user.getAuthProvider().getValue().toLowerCase())
            );
        }

        return new UserPrincipal(
                user.getUuid(),
                user.getEmail(),
                user.getPassword(),
                user.getRole().getGrantedAuthorities(),
                user.getAttributes()
        );

    }

    public UserDetails loadUserById(UUID id) throws UsernameNotFoundException {
        ApplicationUser user = applicationUserRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Id % is not found", id.toString())));

        return new UserPrincipal(
                user.getUuid(),
                user.getEmail(),
                user.getPassword(),
                user.getRole().getGrantedAuthorities(),
                user.getAttributes()
        );

    }

    @Override
    public void addNewUser(SignUpRequest request) {

        PasswordValidator passwordValidator = new PasswordValidator(passwordConfig);

        EmailValidator emailValidator = new EmailValidator();

        if (!emailValidator.test(request.getEmail())){
            throw new ApiRequestException(String.format("The email %s is not valid email", request.getEmail()));
        }

        if (!passwordValidator.test(request.getPassword())){
            throw new ApiRequestException(String.format("The password %s is not valid", request.getPassword()));
        }

        if(!request.getPassword().equals(request.getPasswordConfirm())){
            throw new ApiRequestException("Password confirm should be same as password");
        }

//        if(applicationUserDao.usernameExisted(request.getUsername())){
//            throw new ApiRequestException(String.format("This username %s is taken", request.getUsername()));
//        }

        ApplicationUser newUser = new ApplicationUser();

        newUser.setName(request.getName());
        newUser.setEmail(request.getEmail());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setAuthProvider(AuthProvider.LOCAL);
        newUser.setRole(USER);


//        applicationUserDao.save(new ApplicationUser(null,request.getUsername(), request.getPassword(), request.getEmail(), USER,true,true,true,true,null,null));

    }
}
