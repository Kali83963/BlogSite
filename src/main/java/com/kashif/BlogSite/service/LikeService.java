package com.kashif.BlogSite.service;


import com.kashif.BlogSite.model.Account;
import com.kashif.BlogSite.model.Likes;
import com.kashif.BlogSite.model.Post;
import com.kashif.BlogSite.repository.LikeRepository;
import com.kashif.BlogSite.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private PostRepository postRepository;

    public void likePost(Post post, Account user){

        if(!hasUserlikePost(post,user)){
            Likes likes = Likes.builder()
                    .post(post)
                    .user(user)
                    .build();

            likeRepository.save(likes);

            post.setLikes(post.getLikes()+1);
            postRepository.save(post);


        }
    }

    public boolean hasUserlikePost(Post post, Account user) {
        return likeRepository.existsByPostAndUser(post, user);
    }

    public void unlikePost(Post post, Account user){


        Likes likes = likeRepository.findByPostAndUser(post, user);

        if(likes!=null){
            likeRepository.delete(likes);

            post.setLikes(post.getLikes()-1);
            postRepository.save(post);
        }



    }



}
