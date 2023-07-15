package com.kashif.BlogSite.service;





import com.kashif.BlogSite.model.Account;
import com.kashif.BlogSite.repository.AccountRepository;
import com.kashif.BlogSite.response.AuthenticationRequest;
import com.kashif.BlogSite.response.AuthenticationResponse;
import com.kashif.BlogSite.response.RegisterRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import com.kashif.BlogSite.model.Role;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AccountRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;
    public AuthenticationResponse register(RegisterRequest request) {
        var user = Account.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        repository.save(user);


        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .id(user.getId())
                .name(user.getFirstname() + " "+ user.getLastname())
                .email(user.getEmail())
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {


        this.Authenticate(request.getEmail(), request.getPassword());


        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());

        Optional<Account> optionaluser = repository.findByEmail(request.getEmail());

        Account user = optionaluser.orElse(null);

        String jwtToken = jwtService.generateToken(userDetails);



        return AuthenticationResponse.builder()
                .token(jwtToken)
                .id(user.getId())
                .name(user.getFirstname() + " "+ user.getLastname())
                .email(user.getEmail())
                .build();


    }
    private void Authenticate(String email, String password) {

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            authenticationManager.authenticate(authentication);


        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(" Invalid Username or Password  !!");
        }
    }


}

