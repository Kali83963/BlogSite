package com.kashif.BlogSite.controller;


import com.kashif.BlogSite.model.Post;
import com.kashif.BlogSite.response.PostResponse;
import com.kashif.BlogSite.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/home/")
public class HomeController {

    @Autowired
    private PostService postService;

    @GetMapping("/")
    public ResponseEntity<List<PostResponse>> home(){
        List<PostResponse> posts = postService.getAll();
        return ResponseEntity.ok(posts);
    }

    }
