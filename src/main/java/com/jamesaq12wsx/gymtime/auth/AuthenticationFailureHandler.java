package com.jamesaq12wsx.gymtime.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jamesaq12wsx.gymtime.exception.ApiException;
import com.jamesaq12wsx.gymtime.exception.ApiRequestException;
import com.jamesaq12wsx.gymtime.util.CookieUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

import static com.jamesaq12wsx.gymtime.auth.oauth2.HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

@Component
public class AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        ApiException apiException = new ApiException(
                exception.getMessage(),
                HttpStatus.FORBIDDEN,
                LocalDateTime.now()
        );

        ObjectMapper mapper = new ObjectMapper();

        response.getOutputStream().println(
                mapper.writeValueAsString(apiException)
        );

//        String targetUrl = CookieUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
//                .map(Cookie::getValue)
//                .orElse(("/"));
//
//        targetUrl = UriComponentsBuilder.fromUriString(targetUrl)
//                .queryParam("error", exception.getLocalizedMessage())
//                .build().toUriString();

//        getRedirectStrategy().sendRedirect(request, response, targetUrl);

    }

}
