package com.kashif.BlogSite.repository;

import com.kashif.BlogSite.model.Account;
import com.kashif.BlogSite.model.Likes;
import com.kashif.BlogSite.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LikeRepository extends JpaRepository<Likes,Long> {
    boolean existsByPostAndUser(Post post, Account user);

    Likes findByPostAndUser(Post post, Account user);
}
