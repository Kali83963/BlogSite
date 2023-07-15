package com.kashif.BlogSite.service;


import com.kashif.BlogSite.model.Account;
import com.kashif.BlogSite.model.Comments;
import com.kashif.BlogSite.model.Post;
import com.kashif.BlogSite.repository.CommentsRepository;
import com.kashif.BlogSite.repository.PostRepository;
import com.kashif.BlogSite.response.PostResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kashif.BlogSite.response.CommentRequest;
import com.kashif.BlogSite.response.CommentResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentsRepository commentsRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PostService postService;


    public Optional<Comments> getById(Long id){
        return commentsRepository.findById(id);
    }


    public CommentResponse createcomment(CommentRequest request,String token){

        Account useraccount = jwtService.extractAccount(token);

        Post post = postService.getById(request.getPost_id()).orElse(null);

        Comments comments = null;


        if(post != null){
            try {
                comments = Comments.builder()
                        .account(useraccount)
                        .post(post)
                        .content(request.getContent())
                        .createdAt(LocalDateTime.now())
                        .build();
                commentsRepository.save(comments);

                return convertToCommentResponse(comments);
            }catch (Exception e){
                System.out.println(e);

                return convertToCommentResponse(null);
            }
        }

        return null;


    }

    public List<CommentResponse> getCommentsByPostid(Long postid){
        Post post = postService.getById(postid).orElse(null);

        List<Comments> comments = commentsRepository.findByPostId(postid);

        List<CommentResponse> commentResponses = new ArrayList<>();

        for(Comments comment : comments){
            CommentResponse commentResponse = convertToCommentResponse(comment);

            commentResponses.add(commentResponse);
        }

        return commentResponses;

    }


    public List<CommentResponse> getCommentByUserid(String token){
        Account useraccount = jwtService.extractAccount(token);
        List<Comments> comments = commentsRepository.findByAccountId(useraccount.getId());

        List<CommentResponse> commentResponses = new ArrayList<>();

        for(Comments comment : comments){
            CommentResponse commentResponse = convertToCommentResponse(comment);

            commentResponses.add(commentResponse);
        }

        return commentResponses;

    }

    private CommentResponse convertToCommentResponse(Comments comment) {
        return CommentResponse
                .builder()
                .id(comment.getId())
                .content(comment.getContent())
                .user(comment.getAccount().getEmail())
                .post(comment.getPost().getId())
                .build();
    }


    public CommentResponse editcomment(CommentRequest request, Long id) {

        try {
            Comments comments = this.getById(id).orElseThrow(null);
            comments.setContent(request.getContent());
            commentsRepository.save(comments);

            return convertToCommentResponse(comments);
        }catch (Exception e){
            System.out.println(e);
        }

        return null;

    }

    public boolean deletepost(Long id) {

        try {
            Comments comments = this.getById(id).orElseThrow(null);
            commentsRepository.delete(comments);

        }catch (Exception e){
            System.out.println(e);
        }

        return true;


    }
}
