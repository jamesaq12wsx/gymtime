package com.jamesaq12wsx.gymtime.auth;

import com.jamesaq12wsx.gymtime.exception.ApiRequestException;
import com.jamesaq12wsx.gymtime.jwt.SignUpRequest;
import com.jamesaq12wsx.gymtime.security.PasswordConfig;
import com.jamesaq12wsx.gymtime.validator.EmailValidator;
import com.jamesaq12wsx.gymtime.validator.PasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.jamesaq12wsx.gymtime.security.ApplicationUserRole.USER;

@Service
public class ApplicationUserService implements SelfUserDetailsService {

    private final ApplicationUserDao applicationUserDao;

    private final PasswordConfig passwordConfig;

    @Autowired
    public ApplicationUserService(@Qualifier("postgres") ApplicationUserDao applicationUserDao, PasswordConfig passwordConfig) {
        this.applicationUserDao = applicationUserDao;
        this.passwordConfig = passwordConfig;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return applicationUserDao.selectApplicationUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Username %s is not found", username)));
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

        if(applicationUserDao.usernameExisted(request.getUsername())){
            throw new ApiRequestException(String.format("This username %s is taken", request.getUsername()));
        }

        applicationUserDao.save(new ApplicationUser(null,request.getUsername(), request.getPassword(), request.getEmail(), USER,true,true,true,true,null,null));

    }
}
