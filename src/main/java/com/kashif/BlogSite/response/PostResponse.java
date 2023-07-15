package com.kashif.BlogSite.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
    private long id;
    private String title;
    private String body;
    private String account;
    private Integer like;
    private boolean hasuserlikepost;
    private Integer comments;
    private LocalDateTime date;

}
