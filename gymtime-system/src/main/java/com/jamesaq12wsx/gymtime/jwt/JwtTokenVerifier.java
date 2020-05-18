package com.jamesaq12wsx.gymtime.jwt;

import com.google.common.base.Strings;
import com.jamesaq12wsx.gymtime.security.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class JwtTokenVerifier extends OncePerRequestFilter {

    private final SecretKey secretKey;
    private final JwtConfig jwtConfig;
    private final TokenProvider tokenProvider;

    public JwtTokenVerifier(SecretKey secretKey, JwtConfig jwtConfig, TokenProvider tokenProvider) {
        this.secretKey = secretKey;
        this.jwtConfig = jwtConfig;
        this.tokenProvider = tokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(jwtConfig.getAuthorizationHeader());

//        String secretKey = jwtConfig.getSecretKey();

        if (Strings.isNullOrEmpty(authorizationHeader) || !authorizationHeader.startsWith(jwtConfig.getTokenPrefix())){

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    request.getSession(),
                    null,
                    Role.ANONYMOUS.getGrantedAuthorities()

            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);
            return;
        }

        String token = authorizationHeader.replace(jwtConfig.getTokenPrefix(), "").trim();

        try{

//            Jws<Claims> claimsJws = Jwts.parser()
//                    .setSigningKey(secretKey)
//                    .parseClaimsJws(token);
//
//            Claims body = claimsJws.getBody();
//
//            String username = body.getSubject();
//
//            var authorities = (List<Map<String,String>>) body.get("authorities");
//
//            Set<SimpleGrantedAuthority> simpleGrantedAuthorities = authorities.stream().map(auth -> new SimpleGrantedAuthority(auth.get("authority"))).collect(Collectors.toSet());

            Authentication authentication = tokenProvider.getAuthentication(token);

            SecurityContextHolder.getContext().setAuthentication(authentication);

        }catch (JwtException e){
            throw new IllegalStateException(String.format(e.getMessage()));
        }

        filterChain.doFilter(request, response);
    }
}
