//package com.kashif.BlogSite.config;
//
//import com.kashif.BlogSite.model.Account;
//import com.kashif.BlogSite.model.Post;
//import com.kashif.BlogSite.repository.AccountRepository;
//import com.kashif.BlogSite.service.PostService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
//@Component
//public class SeedData implements CommandLineRunner {
//
//    @Autowired
//    private PostService postService;
//
//    private AccountRepository accountRepository;
//
//    @Override
//    public void run(String... args) throws Exception{
//        List<Post> posts = postService.getAll();
//
//        if(posts.size() == 0){
//
//
//
////                Account account = accountRepository.getReferenceById(1L);
////                System.out.println(account);
//                Post post1 = new Post();
//                post1.setTitle("This is The Title of Post 1");
//                post1.setBody("This is the Body of Post 2");
////                post1.setAccount(account);
////
//                postService.save(post1);
//        }
//
//    }
//
//
//}
