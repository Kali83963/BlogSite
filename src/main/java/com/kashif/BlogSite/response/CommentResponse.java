package com.kashif.BlogSite.response;


import com.kashif.BlogSite.model.Account;
import com.kashif.BlogSite.model.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse {

    public Long id;
    public String content;
    public Long post;
    public String user;



}


