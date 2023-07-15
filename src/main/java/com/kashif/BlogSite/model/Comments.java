package com.kashif.BlogSite.model;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class Comments {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;


    @Column(columnDefinition = "TEXT")
    private String content;


    private LocalDateTime createdAt;


    @ManyToOne
    @NonNull
    @JoinColumn(name = "post_id",referencedColumnName = "id",nullable = false)
    private Post post;



    @ManyToOne
    @NonNull
    @JoinColumn(name = "account_id",referencedColumnName = "id",nullable = false)
    private Account account;





}
