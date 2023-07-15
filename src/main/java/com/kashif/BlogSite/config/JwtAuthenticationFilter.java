package com.kashif.BlogSite.config;

import com.kashif.BlogSite.service.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private Logger logger = LoggerFactory.getLogger(OncePerRequestFilter.class);



    @Autowired
    private final JwtService jwtservice;
    @Autowired
    private final UserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        logger.info(" Header :  {}", authHeader);
        final String jwt;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String useremail = null;


        jwt = authHeader.substring(7);
        try {
            useremail = jwtservice.extractUsername(jwt);

        } catch (IllegalArgumentException e) {
            logger.info("Illegal Argument while fetching the username !!");
            e.printStackTrace();
        } catch (ExpiredJwtException e) {
            logger.info("Given jwt token is expired !!");
            e.printStackTrace();
        } catch (MalformedJwtException e) {
            logger.info("Some changed has done in token !! Invalid Token");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();

        }

        if (useremail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(useremail);

            if (jwtservice.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authtoken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authtoken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authtoken);
            } else {
                logger.info("Validation fails !!");

            }
            filterChain.doFilter(request, response);
        }
    }
}