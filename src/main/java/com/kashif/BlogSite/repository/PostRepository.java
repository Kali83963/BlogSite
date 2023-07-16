package com.kashif.BlogSite.repository;


import com.kashif.BlogSite.model.Account;
import com.kashif.BlogSite.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {

    List<Post> findAllByOrderByIdDesc();

    List<Post> findByAccountOrderByIdDesc(Account account);

}
