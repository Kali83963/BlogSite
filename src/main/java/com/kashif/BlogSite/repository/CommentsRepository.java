package com.kashif.BlogSite.repository;

import com.kashif.BlogSite.model.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentsRepository extends JpaRepository<Comments,Long> {
    List<Comments> findByPostId(Long postId);

    List<Comments> findByAccountId(Long accountId);

}
