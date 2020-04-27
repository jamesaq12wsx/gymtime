package com.jamesaq12wsx.gymtime.auth;

import com.jamesaq12wsx.gymtime.database.*;
import com.jamesaq12wsx.gymtime.exception.ApiRequestException;
import com.jamesaq12wsx.gymtime.model.MeasurementType;
import com.jamesaq12wsx.gymtime.model.SimpleMeasurementUnit;
import com.jamesaq12wsx.gymtime.model.entity.*;
import com.jamesaq12wsx.gymtime.model.payload.*;
import com.jamesaq12wsx.gymtime.security.PasswordConfig;
import com.jamesaq12wsx.gymtime.service.ImageService;
import com.jamesaq12wsx.gymtime.validator.EmailValidator;
import com.jamesaq12wsx.gymtime.validator.PasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

import static com.jamesaq12wsx.gymtime.security.ApplicationUserRole.USER;

@Service
public class ApplicationUserService implements SelfUserDetailsService {

    private final ApplicationUserRepository applicationUserRepository;

    private final MeasurementUnitRepository measurementUnitRepository;

    private final HeightRepository heightRepository;

    private final WeightRepository weightRepository;

    private final BodyFatRepository bodyFatRepository;

    private final PasswordConfig passwordConfig;

    private final PasswordEncoder passwordEncoder;

    private final ImageService imageService;

    @Autowired
    public ApplicationUserService(ApplicationUserRepository applicationUserRepository, MeasurementUnitRepository measurementUnitRepository, HeightRepository heightRepository, WeightRepository weightRepository, BodyFatRepository bodyFatRepository, PasswordConfig passwordConfig, PasswordEncoder passwordEncoder, ImageService imageService) {
        this.applicationUserRepository = applicationUserRepository;
        this.measurementUnitRepository = measurementUnitRepository;
        this.heightRepository = heightRepository;
        this.weightRepository = weightRepository;
        this.bodyFatRepository = bodyFatRepository;
        this.passwordConfig = passwordConfig;
        this.passwordEncoder = passwordEncoder;
        this.imageService = imageService;
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

        newUser.setAudit(new Audit());

        UserUnitSetting defaultSetting = new UserUnitSetting();
        defaultSetting.setHeightUnit(new SimpleMeasurementUnit(1, MeasurementType.HEIGHT, "Centimeter", "cm"));
        defaultSetting.setWeightUnit(new SimpleMeasurementUnit(4, MeasurementType.WEIGHT, "Kilogram", "kg"));
        defaultSetting.setDistanceUnit(new SimpleMeasurementUnit(6, MeasurementType.DISTANCE, "kilometre", "km"));

        newUser.setUserUnitSetting(defaultSetting);

        applicationUserRepository.save(newUser);

//        applicationUserDao.save(new ApplicationUser(null,request.getUsername(), request.getPassword(), request.getEmail(), USER,true,true,true,true,null,null));

    }

    @Override
    public ApplicationUser loadUserInfoByEmail(String email) {
        return applicationUserRepository.findByEmail(email)
                .orElseThrow(() -> new ApiRequestException(String.format("Username %s not found", email)));
    }

    @Override
    public void updateUserName(UserInfoRequest request, Principal principal) {
        ApplicationUser user = applicationUserRepository.findByEmail(principal.getName()).orElseThrow(() -> getUserNotFoundException(principal));

        if (user.getAuthProvider() != AuthProvider.LOCAL){
            throw new ApiRequestException("Social login could not change the name");
        }

        user.setName(request.getName());

        applicationUserRepository.save(user);
    }

    @Override
    public void updateUserBirthday(UserInfoRequest request, Principal principal) {

        ApplicationUser user = applicationUserRepository.findByEmail(principal.getName()).orElseThrow(() -> getUserNotFoundException(principal));

        if (user.getUserInfo() == null){
            user.setUserInfo(new UserInfo());
        }

        user.getUserInfo().setBirthday(request.getBirthday());

        applicationUserRepository.save(user);

    }

    @Override
    public void updateUserGender(UserInfoRequest request, Principal principal) {

        ApplicationUser user = applicationUserRepository.findByEmail(principal.getName()).orElseThrow(() -> getUserNotFoundException(principal));

        if (user.getUserInfo() == null){
            user.setUserInfo(new UserInfo());
        }

        user.getUserInfo().setGender(request.getGender());

        applicationUserRepository.save(user);

    }

    @Override
    public void updateUserPicture(MultipartFile picture, Principal principal) {

        String fileUrl = null;

        try {
            fileUrl = imageService.saveImage(picture);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ApiRequestException(String.format("Save picture failed"));
        }

        ApplicationUser user = applicationUserRepository.findByEmail(principal.getName()).orElseThrow(() -> getUserNotFoundException(principal));

        user.setImageUrl(fileUrl);

        applicationUserRepository.save(user);

    }

    @Override
    public void updateWeightUnit(UserUnitRequest request, Principal principal) {
        ApplicationUser user = getUserFromDb(principal);

        if(user.getUserUnitSetting().getWeightUnit().getId() != request.getWeightUnit()){
            SimpleMeasurementUnit unit = getUnitFromDb(request.getWeightUnit());

            if (!unit.getMeasurementType().equals(MeasurementType.WEIGHT)){
                throw new ApiRequestException(String.format("Cannot change weight unit, unit %s is not for weight", unit.getId()));
            }

            user.getUserUnitSetting().setWeightUnit(unit);

            applicationUserRepository.save(user);
        }
    }

    @Override
    public void updateHeightUnit(UserUnitRequest request, Principal principal) {
        ApplicationUser user = getUserFromDb(principal);

        if(user.getUserUnitSetting().getHeightUnit().getId() != request.getHeightUnit()){

            SimpleMeasurementUnit unit = getUnitFromDb(request.getHeightUnit());

            if (!unit.getMeasurementType().equals(MeasurementType.HEIGHT)){
                throw new ApiRequestException(String.format("Cannot change height unit, unit %s is not for height", unit.getId()));
            }

            user.getUserUnitSetting().setHeightUnit(unit);

            applicationUserRepository.save(user);
        }
    }

    @Override
    public void updateDistanceUnit(UserUnitRequest request, Principal principal) {
        ApplicationUser user = getUserFromDb(principal);

        if(user.getUserUnitSetting().getDistanceUnit().getId() != request.getDistanceUnit()){

            SimpleMeasurementUnit unit = getUnitFromDb(request.getDistanceUnit());

            if (!unit.getMeasurementType().equals(MeasurementType.DISTANCE)){
                throw new ApiRequestException(String.format("Cannot change distance unit, unit %s is not for distance", unit.getId()));
            }

            user.getUserUnitSetting().setDistanceUnit(unit);

            applicationUserRepository.save(user);
        }
    }

    @Override
    public void updateGender(UserInfoRequest request, Principal principal) {
        ApplicationUser user = getUserFromDb(principal);

        if(!user.getUserInfo().getGender().getValue().equals(request.getGender().getValue())){

            user.getUserInfo().setGender(request.getGender());

            applicationUserRepository.save(user);
        }
    }

    @Override
    public void updateBirthday(UserInfoRequest request, Principal principal) {
        ApplicationUser user = getUserFromDb(principal);

        if(!user.getUserInfo().getBirthday().equals(request.getBirthday())){

            user.getUserInfo().setBirthday(request.getBirthday());

            applicationUserRepository.save(user);
        }
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

        bodyFatRepository.deleteById(id);
    }

    private ApplicationUser getUserFromDb(Principal principal) {

        return applicationUserRepository.findByEmail(principal.getName()).orElseThrow(() -> getUserNotFoundException(principal));

    }

    private SimpleMeasurementUnit getUnitFromDb(Integer id){
        return measurementUnitRepository.findById(id).orElseThrow(() -> new ApiRequestException(String.format("Unit %s not found, can not update unit setting", id)));
    }

    private ApiRequestException getUserNotFoundException(Principal principal) {
        return new ApiRequestException(String.format("Username %s not found", principal.getName()));
    }
}
