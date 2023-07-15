package com.kashif.BlogSite.controller;

import com.kashif.BlogSite.response.AuthenticationRequest;
import com.kashif.BlogSite.response.AuthenticationResponse;
import com.kashif.BlogSite.response.RegisterRequest;
import com.kashif.BlogSite.service.AccountService;
import com.kashif.BlogSite.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.Console;
import java.io.IOException;

@RestController
@RequestMapping("/auth/")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @Autowired
    private AccountService accountService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(service.register(request));
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/{id}/profile-photo")
    public ResponseEntity<String> uploadProfilePhoto(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        // Logic to save the uploaded file, generate file path, and update the profilePhoto field in the Account entity

        try {
            String filePath = accountService.saveProfilePhoto(id, file);
            return ResponseEntity.ok(filePath);
        }catch (Exception e){
            System.out.println(e);
        }
        return ResponseEntity.ok("error");


    }

}