package com.kashif.BlogSite.controller;


import com.kashif.BlogSite.model.Account;
import com.kashif.BlogSite.model.Post;
import com.kashif.BlogSite.response.PostRequest;
import com.kashif.BlogSite.response.PostResponse;
import com.kashif.BlogSite.service.JwtService;
import com.kashif.BlogSite.service.LikeService;
import com.kashif.BlogSite.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@Controller
@RestController
@RequestMapping("/post/")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private LikeService likeService;

    @Autowired
    private JwtService jwtService;

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id,HttpServletRequest request){

        Account useraccount;
        try{
            String headerValue = request.getHeader("Authorization").substring(7);
            useraccount = jwtService.extractAccount(headerValue);
        }catch (Exception e){
            useraccount = null;
        }


        boolean isliked = false;



        Optional<Post> optionalPost = postService.getById(id);

        if (optionalPost.isPresent()){

            Post post = optionalPost.orElse(null);
            if(useraccount != null){
                isliked = likeService.hasUserlikePost(post,useraccount);
            }
            return ResponseEntity.ok(PostResponse
                    .builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .body(post.getBody())
                    .like(post.getLikes())
                    .comments(post.getComments().size())
                    .date(post.getCreatedAt())
                    .hasuserlikepost(isliked)
                    .account(post.getAccount().getEmail())
                    .build());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<PostResponse> editpost(@PathVariable Long id,@RequestBody PostRequest request){

        PostResponse post = postService.editpost(request,id);


        return ResponseEntity.ok(post);




    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<Boolean> deletepost(@PathVariable Long id){

        boolean isdelete = postService.deletepost(id);

        return ResponseEntity.ok(isdelete);
    }



    @PostMapping("/{postId}/like")
    public ResponseEntity<String> likePost(@PathVariable Long postId,HttpServletRequest request) {
        // Retrieve the post and user from the database
        String headerValue = request.getHeader("Authorization").substring(7);
        Account useraccount = jwtService.extractAccount(headerValue);
        Post post = postService.getById(postId).orElseThrow(null);

        // Like the post
        likeService.likePost(post, useraccount);

        return ResponseEntity.ok("Post liked");
    }

    @PostMapping("/{postId}/unlike")
    public ResponseEntity<String> unlikePost(@PathVariable Long postId, HttpServletRequest request) {
        // Retrieve the post and user from the database.
        String headerValue = request.getHeader("Authorization").substring(7);
        Account useraccount = jwtService.extractAccount(headerValue);
        Post post = postService.getById(postId).orElseThrow(null);

        // Unlike the post
        likeService.unlikePost(post, useraccount);

        return ResponseEntity.ok("Post unliked");
    }
    @PostMapping("/")
    public ResponseEntity<PostResponse> createPost(HttpServletRequest request, @RequestBody PostRequest requestBody){

        String headerValue = request.getHeader("Authorization").substring(7);


        PostResponse post = postService.createpost(requestBody,headerValue);


        return ResponseEntity.ok(post);
//
    }
}
