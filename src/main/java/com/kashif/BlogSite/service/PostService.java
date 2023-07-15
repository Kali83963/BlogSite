package com.kashif.BlogSite.service;


import com.kashif.BlogSite.model.Account;
import com.kashif.BlogSite.model.Post;
import com.kashif.BlogSite.repository.PostRepository;
import com.kashif.BlogSite.response.PostRequest;
import com.kashif.BlogSite.response.PostResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private JwtService jwtService;

    public Optional<Post> getById(Long id){
        return postRepository.findById(id);
    }

    public List<PostResponse> getAll(){
        List<Post> post = postRepository.findAll();
        List<PostResponse> postResponses = post.stream().map(this::convertToPostResponse).collect(Collectors.toList());


        return postResponses;
    }

    public PostResponse editpost(PostRequest request,Long id){
        Optional<Post> optionalPost = this.getById(id);

        Post post = optionalPost.orElse(null);

        if(post!=null){
            post.setTitle(request.title);
            post.setBody(request.body);
            post.setCreatedAt(LocalDateTime.now());

            postRepository.save(post);
        }

        return convertToPostResponse(post);



    }

    public PostResponse createpost(PostRequest request, String token){


        Account useraccount = jwtService.extractAccount(token);

        Post post = null;
        try {
            post = Post.builder()
                    .title(request.getTitle())
                    .body(request.getBody())
                    .createdAt(LocalDateTime.now())
                    .account(useraccount)
                    .build();
            postRepository.save(post);

            return convertToPostResponse(post);


        }catch(Exception e) {
            System.out.println(e);

            return convertToPostResponse(null);
        }





    }

    public Post save(Post post){

        if( post.getId() == 0){
            post.setCreatedAt(LocalDateTime.now());
        }
        return postRepository.save(post);
    }

    private PostResponse convertToPostResponse(Post post) {
        return PostResponse
                .builder()
                .id(post.getId())
                .title(post.getTitle())
                .body(post.getBody())
                .date(post.getCreatedAt())
                .account(post.getAccount().getEmail())
                .like(post.getLikes())
//                .comments(post.getComments().size())
                .build();
    }

    public boolean deletepost(Long id) {
        Optional<Post> optionalpost = this.getById(id);
        Post post = optionalpost.orElse(null);

        postRepository.delete(post);
        return true;
    }
}
