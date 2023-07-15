package com.kashif.BlogSite.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String body;


    private LocalDateTime createdAt;

    @NotNull
    @ManyToOne()
    @JoinColumn(name = "account_id",referencedColumnName = "id",nullable = false)
    private Account account;



    @OneToMany(mappedBy = "post")
    @OrderBy("id")
    private List<Comments> comments = new ArrayList<>();;

    @Column(columnDefinition = "integer default 0")
    private int likes;


}
