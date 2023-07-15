package com.kashif.BlogSite.repository;

import com.kashif.BlogSite.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account,Long> {

    public Optional<Account> findByEmail(String email);



}
