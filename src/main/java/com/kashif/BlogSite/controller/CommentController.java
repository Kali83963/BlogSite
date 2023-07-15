package com.kashif.BlogSite.controller;


import com.kashif.BlogSite.model.Comments;
import com.kashif.BlogSite.model.Post;
import com.kashif.BlogSite.response.CommentRequest;
import com.kashif.BlogSite.response.CommentResponse;
import com.kashif.BlogSite.service.CommentService;
import com.kashif.BlogSite.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RestController
@RequestMapping("/comment/")
public class CommentController {


    @Autowired
    private CommentService commentService;

    @Autowired
    private PostService postService;

//    Get Comments of a Post
    @GetMapping("/{id}")
    public ResponseEntity<List<CommentResponse>> getcommentsbypost(@PathVariable Long id){

        List<CommentResponse> commentResponsesList = commentService.getCommentsByPostid(id);



        return ResponseEntity.ok(commentResponsesList);



    }

    @GetMapping("/account/")
    public ResponseEntity<List<CommentResponse>> getcommentbyuser(HttpServletRequest request){
        String headerValue = request.getHeader("Authorization").substring(7);

        List<CommentResponse> commentResponsesList = commentService.getCommentByUserid(headerValue);

        return ResponseEntity.ok(commentResponsesList);

    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<CommentResponse> editcomment(@PathVariable Long id,@RequestBody CommentRequest request){

        CommentResponse commentResponse = commentService.editcomment(request,id);

        return ResponseEntity.ok(commentResponse);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<Boolean> deletecomment(@PathVariable Long id){

        boolean isdelete = commentService.deletepost(id);

        return ResponseEntity.ok(isdelete);
    }

    @PostMapping("/")
    public ResponseEntity<CommentResponse> createcomment(HttpServletRequest request,@RequestBody CommentRequest commentRequest){

        String headerValue = request.getHeader("Authorization").substring(7);

        CommentResponse commentResponse = commentService.createcomment(commentRequest,headerValue);

        return ResponseEntity.ok(commentResponse);

    }

}
