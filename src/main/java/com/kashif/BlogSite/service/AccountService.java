package com.kashif.BlogSite.service;


import com.kashif.BlogSite.model.Account;
import com.kashif.BlogSite.repository.AccountRepository;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public List<Account> getUser(){
        return accountRepository.findAll();
    }
    private String uploadDir = "src/main/resources/static/image";
    public String saveProfilePhoto(Long id, MultipartFile file) throws IOException {
        // Logic to save the file to a directory and generate the file path

        String filePath = uploadDir + File.separator + "profile-photos" + File.separator + id + File.separator + file.getOriginalFilename();

        // Create the directory if it doesn't exist
        File directory = new File(uploadDir + File.separator + "profile-photos" + File.separator + id);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Save the file to the specified location
        Path targetPath = Path.of(filePath);
        Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
        // Update the Account entity with the file path
        Account account = accountRepository.findById(id).orElseThrow();
        account.setProfilePhoto(filePath);
        accountRepository.save(account);
        return filePath;
    }
}
