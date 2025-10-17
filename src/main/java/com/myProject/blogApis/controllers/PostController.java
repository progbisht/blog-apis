package com.myProject.blogApis.controllers;

import com.myProject.blogApis.payloads.ApiResponse;
import com.myProject.blogApis.payloads.PostDto;
import com.myProject.blogApis.payloads.PostResponse;
import com.myProject.blogApis.services.FileService;
import com.myProject.blogApis.services.PostService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpResponse;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable Integer userId, @PathVariable Integer categoryId){
        PostDto post = this.postService.createPost(postDto, categoryId, userId);

        return new ResponseEntity<PostDto>(post, HttpStatus.CREATED);
    }


    //  Pagination
    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Integer categoryId){
        List<PostDto> posts = this.postService.getPostByCategory(categoryId);

        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    //  Pagination
    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable Integer userId){
        List<PostDto> posts = this.postService.getPostByUser(userId);

        return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);

    }

    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "ASC", required = false) String sortDir
            ){
        PostResponse postResponse = this.postService.getAllPost(pageNumber, pageSize, sortBy, sortDir);

        return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
    }


    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDto> getSinglePost(@PathVariable Integer postId){
        PostDto post = this.postService.getPostById(postId);

        return new ResponseEntity<PostDto>(post, HttpStatus.OK);
    }

    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Integer postId){
        PostDto postDto1 = this.postService.updatePost(postDto, postId);

        return new ResponseEntity<PostDto>(postDto1, HttpStatus.OK);
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<ApiResponse> deleteSinglePost(@PathVariable Integer postId){
        this.postService.deletePost(postId);

        return new ResponseEntity<ApiResponse>(new ApiResponse("Post deleted successfully", false), HttpStatus.OK);
    }

    @GetMapping("/posts/search/{keyword}")
    public ResponseEntity<List<PostDto>> searchPost(@PathVariable String keyword){
        List<PostDto> postDtos = this.postService.searchPost(keyword);

        return new ResponseEntity<List<PostDto>>(postDtos, HttpStatus.OK);
    }

    @PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image")MultipartFile image, @RequestParam Integer postId) throws IOException {
        String filename = this.fileService.uploadImage(path, image);
        PostDto postDto = this.postService.getPostById(postId);

        postDto.setImageName(filename);
        PostDto updatedPost= this.postService.updatePost(postDto, postId);

        return new ResponseEntity<PostDto>(updatedPost, HttpStatus.OK);
    }

    @GetMapping(value = "/post/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(@PathVariable("imageName") String imageName, HttpServletResponse response) throws IOException{
        InputStream resource = this.fileService.getResource(path, imageName);

        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
    }

}
