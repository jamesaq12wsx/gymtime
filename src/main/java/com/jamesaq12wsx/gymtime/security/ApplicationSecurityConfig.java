package com.jamesaq12wsx.gymtime.security;

import com.jamesaq12wsx.gymtime.auth.ApplicationUserService;
import com.jamesaq12wsx.gymtime.auth.AuthenticationFailureHandler;
import com.jamesaq12wsx.gymtime.auth.oauth2.CustomOAuth2UserService;
import com.jamesaq12wsx.gymtime.auth.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;
import com.jamesaq12wsx.gymtime.auth.oauth2.OAuth2AuthenticationFailureHandler;
import com.jamesaq12wsx.gymtime.auth.oauth2.OAuth2AuthenticationSuccessHandler;
import com.jamesaq12wsx.gymtime.jwt.JwtAuthenticationFilter;
import com.jamesaq12wsx.gymtime.jwt.JwtConfig;
import com.jamesaq12wsx.gymtime.jwt.JwtTokenVerifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.crypto.SecretKey;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final ApplicationUserService applicationUserService;
    private final SecretKey secretKey;
    private final JwtConfig jwtConfig;

    private final HttpCookieOAuth2AuthorizationRequestRepository auth2AuthorizationRequestRepository;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

    private final AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder, ApplicationUserService applicationUserService, SecretKey secretKey, JwtConfig jwtConfig, HttpCookieOAuth2AuthorizationRequestRepository auth2AuthorizationRequestRepository, CustomOAuth2UserService customOAuth2UserService, OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler, OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler, AuthenticationFailureHandler authenticationFailureHandler) {
        this.passwordEncoder = passwordEncoder;
        this.applicationUserService = applicationUserService;
        this.secretKey = secretKey;
        this.jwtConfig = jwtConfig;

        this.auth2AuthorizationRequestRepository = auth2AuthorizationRequestRepository;
        this.customOAuth2UserService = customOAuth2UserService;
        this.oAuth2AuthenticationSuccessHandler = oAuth2AuthenticationSuccessHandler;
        this.oAuth2AuthenticationFailureHandler = oAuth2AuthenticationFailureHandler;

        this.authenticationFailureHandler = authenticationFailureHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .requiresChannel()
                    .anyRequest()
                    .requiresSecure()
                    .and()
                .cors()
                    .and()
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .oauth2Login()
                    .authorizationEndpoint()
                        .authorizationRequestRepository(auth2AuthorizationRequestRepository)
                    .and()
                    .successHandler(oAuth2AuthenticationSuccessHandler)
                    .failureHandler(oAuth2AuthenticationFailureHandler)
                    .userInfoEndpoint()
                        .userService(customOAuth2UserService)
                .and().and()
//                .addFilter(new JwtAuthenticationFilter(authenticationManager(), jwtConfig, secretKey))
                .addFilter(tokenAuthenticationFilter())
//                .addFilterBefore(new ExceptionTranslationFilter(
//                        new Http403ForbiddenEntryPoint(),
//                ))
//                    .exceptionHandling()
                .addFilterAfter(new JwtTokenVerifier(secretKey, jwtConfig), JwtAuthenticationFilter.class)
                .antMatcher("/**").authorizeRequests()
                .antMatchers("/", "index", "/css/*", "/js/*").permitAll()
                .antMatchers("/api/v1/clubs/**").permitAll()
                .antMatchers("/api/v1/auth/check").hasAuthority("user:read")
                .antMatchers("/api/v1/auth/signup").permitAll()
                .anyRequest()
                .authenticated();
    }

    @Bean
    AbstractAuthenticationProcessingFilter tokenAuthenticationFilter() throws Exception {
        final AbstractAuthenticationProcessingFilter filter = new JwtAuthenticationFilter(authenticationManager(), jwtConfig, secretKey);
        filter.setAuthenticationManager(authenticationManager());
//        filter.setAuthenticationFailureHandler(authenticationFailureHandler);
        // maybe error handling to provide some custom response?
        return filter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(applicationUserService);

        return provider;
    }

//    @Override
//    @Bean
//    protected UserDetailsService userDetailsService() {
//
//        UserDetails annaUser = User.builder()
//                .username("annasmith")
//                .password(passwordEncoder.encode("password"))
////                .roles(STUDENT.name())
//                .authorities(STUDENT.getGrantedAuthorities())
//                .build();
//
//        UserDetails adminUser = User.builder()
//                .username("linda")
//                .password(passwordEncoder.encode("password123"))
////                .roles(ADMIN.name())
//                .authorities(ADMIN.getGrantedAuthorities())
//                .build();
//
//        UserDetails administraneeUser = User.builder()
//                .username("tom")
//                .password(passwordEncoder.encode("password123"))
////                .roles(ADMIN.name())
//                .authorities(ADMINTRAINEE.getGrantedAuthorities())
//                .build();
//
//        return new InMemoryUserDetailsManager(annaUser, adminUser, administraneeUser);
//    }
}
