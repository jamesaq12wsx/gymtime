package com.jamesaq12wsx.gymtime.auth;

import com.jamesaq12wsx.gymtime.database.ApplicationUserRepository;
import com.jamesaq12wsx.gymtime.database.BodyFatRepository;
import com.jamesaq12wsx.gymtime.database.HeightRepository;
import com.jamesaq12wsx.gymtime.database.WeightRepository;
import com.jamesaq12wsx.gymtime.exception.ApiRequestException;
import com.jamesaq12wsx.gymtime.model.SimpleMeasurementUnit;
import com.jamesaq12wsx.gymtime.model.entity.*;
import com.jamesaq12wsx.gymtime.model.payload.NewBodyFatRequest;
import com.jamesaq12wsx.gymtime.model.payload.NewHeightRequest;
import com.jamesaq12wsx.gymtime.model.payload.NewWeightRequest;
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

import java.security.Principal;
import java.util.UUID;

import static com.jamesaq12wsx.gymtime.security.ApplicationUserRole.USER;

@Service
public class ApplicationUserService implements SelfUserDetailsService {

    private final ApplicationUserRepository applicationUserRepository;

    private final HeightRepository heightRepository;

    private final WeightRepository weightRepository;

    private final BodyFatRepository bodyFatRepository;

    private final PasswordConfig passwordConfig;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationUserService(ApplicationUserRepository applicationUserRepository, HeightRepository heightRepository, WeightRepository weightRepository, BodyFatRepository bodyFatRepository, PasswordConfig passwordConfig, PasswordEncoder passwordEncoder) {
        this.applicationUserRepository = applicationUserRepository;
        this.heightRepository = heightRepository;
        this.weightRepository = weightRepository;
        this.bodyFatRepository = bodyFatRepository;
        this.passwordConfig = passwordConfig;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        ApplicationUser user = applicationUserRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Email % is not found", username)));

        if (user.getAuthProvider() != AuthProvider.LOCAL) {
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

        if (!emailValidator.test(request.getEmail())) {
            throw new ApiRequestException(String.format("The email %s is not valid email", request.getEmail()));
        }

        if (!passwordValidator.test(request.getPassword())) {
            throw new ApiRequestException(String.format("The password %s is not valid", request.getPassword()));
        }

        if (!request.getPassword().equals(request.getPasswordConfirm())) {
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

    @Override
    public ApplicationUser loadUserInfoByEmail(String email) {
        return applicationUserRepository.findByEmail(email)
                .orElseThrow(() -> new ApiRequestException(String.format("Username %s not found", email)));
    }

    @Override
    public ApplicationUser updateUserUnitSetting(String email, UserUnitSetting userUnitSetting) {

        ApplicationUser updateUser = applicationUserRepository.findByEmail(email).get();

        UserUnitSetting preSetting = updateUser.getUserUnitSetting();

        if (preSetting.getWeightUnit() == userUnitSetting.getWeightUnit() &&
                preSetting.getHeightUnit() == userUnitSetting.getHeightUnit() &&
                preSetting.getDistanceUnit() == userUnitSetting.getDistanceUnit()) {
            return updateUser;
        }

        updateUser.setUserUnitSetting(userUnitSetting);

        return applicationUserRepository.save(updateUser);
    }

    @Override
    public ApplicationUser updateUserInfo(String email, UserInfo userInfo) {

        ApplicationUser updateUser = applicationUserRepository.findByEmail(email).get();

        UserInfo preInfo = updateUser.getUserInfo();

        if (preInfo.getGender() == userInfo.getGender() &&
                preInfo.getBirthday().equals(userInfo.getBirthday())) {
            return updateUser;
        }

        updateUser.setUserInfo(userInfo);

        return applicationUserRepository.save(updateUser);
    }

    @Override
    public UserBodyStat getUserBodyStat(Principal principal) {

        UserBodyStat userBodyStat = new UserBodyStat();

        userBodyStat.setHeight(heightRepository.findByUsername(principal.getName()).orElse(null));

        userBodyStat.setWeights(weightRepository.findAllByUsername(principal.getName()));

        userBodyStat.setBodyFats(bodyFatRepository.findAllByUsername(principal.getName()));

        return userBodyStat;
    }

    @Override
    public void updateUserHeight(NewHeightRequest request, Principal principal) {

        ApplicationUser user = applicationUserRepository.findByEmail(principal.getName()).orElseThrow(() -> getUserNotFoundException(principal));

        SimpleMeasurementUnit heightUnit = user.getUserUnitSetting().getHeightUnit();

        if (heightRepository.existsByUser(user)) {
            SimpleUserHeight userHeight = heightRepository.findByUser(user).get();

            if (userHeight.getMeasurementUnit().getId() != heightUnit.getId() || !userHeight.getHeight().equals(request.getHeight())) {
                userHeight.setMeasurementUnit(heightUnit);
                userHeight.setHeight(request.getHeight());

                heightRepository.save(userHeight);

                return;
            }
        } else {

            heightRepository.save(new SimpleUserHeight(null, user, heightUnit, request.getHeight()));

            return;

        }

    }

    @Override
    public void newUserWeight(NewWeightRequest request, Principal principal) {

        ApplicationUser user = getUserFromDb(principal);

        SimpleMeasurementUnit weightUnit = user.getUserUnitSetting().getWeightUnit();

        if (weightRepository.existsByUserAndDate(user, request.getDate())) {

            throw new ApiRequestException("You could not add two weight record at same date");

        } else {

            weightRepository.save(new SimpleUserWeight(null, user, weightUnit, request.getWeight(), request.getDate()));

        }
    }

    @Override
    public void deleteUserWeight(Integer id, Principal principal) {

        SimpleUserWeight userWeight = weightRepository
                .findById(id)
                .orElseThrow(() -> new ApiRequestException(String.format("User weight %s not found", id)));

        if (!userWeight.getUser().getEmail().equals(principal.getName())) {
            throw new ApiRequestException(String.format("You have no right delete this weight record"));
        }

        weightRepository.deleteById(id);
    }

    @Override
    public void newUserBodyFat(NewBodyFatRequest request, Principal principal) {

        ApplicationUser user = getUserFromDb(principal);

        if (bodyFatRepository.existsByUserAndDate(user, request.getDate())) {

            throw new ApiRequestException("You could not add two body fat record at same date");

        } else {

            bodyFatRepository.save(new SimpleUserBodyFat(null, user, request.getBodyFat(), request.getDate()));

        }
    }

    @Override
    public void deleteUserBodyFat(Integer id, Principal principal) {
        SimpleUserBodyFat userBodyFat = bodyFatRepository
                .findById(id)
                .orElseThrow(() -> new ApiRequestException(String.format("User body fat %s not found", id)));

        if (!userBodyFat.getUser().getEmail().equals(principal.getName())) {
            throw new ApiRequestException(String.format("You have no right delete this body fat record"));
        }

        weightRepository.deleteById(id);
    }

    private ApplicationUser getUserFromDb(Principal principal) {

        return applicationUserRepository.findByEmail(principal.getName()).orElseThrow(() -> getUserNotFoundException(principal));

    }

    private ApiRequestException getUserNotFoundException(Principal principal) {
        return new ApiRequestException(String.format("Username %s not found", principal.getName()));
    }
}
