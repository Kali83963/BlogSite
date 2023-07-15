package com.kashif.BlogSite.response;


import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequest {

    public Long post_id;
    public String content;

}
