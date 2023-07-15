

package com.kashif.BlogSite.service;

import com.kashif.BlogSite.model.Account;
import com.kashif.BlogSite.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //   load user from database
        Account user = accountRepository.findByEmail(username).orElseThrow(null);
        return user;
    }
}
