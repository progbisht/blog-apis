package com.myProject.blogApis.UserServiceImpl;

import com.myProject.blogApis.exceptions.ResourceNotFoundException;
import com.myProject.blogApis.models.Category;
import com.myProject.blogApis.models.Post;
import com.myProject.blogApis.models.User;
import com.myProject.blogApis.payloads.PostDto;
import com.myProject.blogApis.payloads.PostResponse;
import com.myProject.blogApis.repositories.CategoryRepo;
import com.myProject.blogApis.repositories.PostRepo;
import com.myProject.blogApis.repositories.UserRepo;
import com.myProject.blogApis.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {

        User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "id", userId));
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "id", categoryId));

        Post post = this.modelMapper.map(postDto, Post.class);
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setCategory(category);
        post.setUser(user);

        Post newPost = this.postRepo.save(post);


        return this.modelMapper.map(newPost, PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "postId", postId));

        post.setPostTitle(postDto.getPostTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());
        post.setAddedDate(postDto.getAddedDate());


        Post updatedPost = this.postRepo.save(post);

        return this.modelMapper.map(updatedPost, PostDto.class);
    }

    @Override
    public void deletePost(Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "postId", postId));

        this.postRepo.delete(post);

    }

    @Override
    public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Sort sort = null;

        if(sortDir.equalsIgnoreCase("asc")){
            sort = Sort.by(sortBy).ascending();
        }
        else{
            sort = Sort.by(sortBy).descending();
        }

        Pageable page = PageRequest.of(pageNumber, pageSize, sort);
        Page<Post> pagePost = this.postRepo.findAll(page);

        List<Post> posts = pagePost.getContent();

        List<PostDto> allPosts = posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();

        postResponse.setContent(allPosts);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setTotalElements(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());

        return postResponse;
    }

    @Override
    public PostDto getPostById(Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "postId", postId));

        return this.modelMapper.map(post, PostDto.class);
    }

    @Override
    public List<PostDto> getPostByCategory(Integer categoryId) {
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "categoryId", categoryId));
        List<Post> posts = this.postRepo.findByCategory(category);


        return posts.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<PostDto> getPostByUser(Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "id", userId));
        List<Post> posts = this.postRepo.findByUser(user);

        return posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<PostDto> searchPost(String keyword) {
        List<Post> posts = this.postRepo.findByTitleContaining(keyword);
        return posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
    }
}
