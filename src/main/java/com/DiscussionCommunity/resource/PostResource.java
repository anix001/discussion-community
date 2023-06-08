package com.DiscussionCommunity.resource;

import com.DiscussionCommunity.domain.Comment;
import com.DiscussionCommunity.domain.Post;
import com.DiscussionCommunity.domain.Reply;
import com.DiscussionCommunity.domain.dto.ApiResponse;
import com.DiscussionCommunity.domain.dto.PostDto;
import com.DiscussionCommunity.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
public class PostResource {
    private final PostService postService;

    public PostResource(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> store(Post post)  {
        postService.store(post);
        ApiResponse response = new ApiResponse(true,HttpStatus.CREATED,"Post is successfully created.", new ArrayList<>());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PostDto>>> index()  {
        ApiResponse response = new ApiResponse(true,HttpStatus.OK,"Posts fetched successfully.", postService.index());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/image/{postId}")
    public ResponseEntity<?> downloadImage(@PathVariable Long postId){
        byte[] image = postService.download(postId);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(image);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<ApiResponse<PostDto>> get(@PathVariable Long postId){
        ApiResponse response = new ApiResponse(true,HttpStatus.OK,"Post fetched successfully .",  postService.get(postId));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PutMapping("/{postId}")
    public ResponseEntity<ApiResponse> update(@PathVariable Long postId, Post post){
        postService.update(postId, post);
        ApiResponse response = new ApiResponse(true,HttpStatus.OK,"Post updated",new ArrayList<>());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long postId){
        postService.delete(postId);
        ApiResponse response = new ApiResponse(true,HttpStatus.OK,"Post deleted",new ArrayList<>());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

   //comments
    @PostMapping("/{postId}/comment")
    public ResponseEntity<ApiResponse> addComment(@PathVariable Long postId, @RequestBody List<Comment> commentList){
        postService.postComment(postId, commentList);
        ApiResponse response = new ApiResponse(true,HttpStatus.CREATED,"Comment added on Post.", new ArrayList<>());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/comment")
    public ResponseEntity<ApiResponse> updateComment(@RequestParam Long commentId, @RequestBody Comment comment){
        postService.updatePostComment(commentId, comment);
        ApiResponse response = new ApiResponse(true,HttpStatus.OK,"Comment  updated", new ArrayList<>());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/comment")
    public ResponseEntity<ApiResponse> deleteComment(@RequestParam Long commentId){
        postService.deletePostComment(commentId);
        ApiResponse response = new ApiResponse(true,HttpStatus.OK,"Comment deleted", new ArrayList<>());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



    //comments reply
    @PostMapping("/comment/reply")
     public ResponseEntity<ApiResponse> replyComment(@RequestParam(value = "commentId") Long commentId ,@RequestBody List<Reply> replies){
        postService.replyComment(commentId, replies);
        ApiResponse response = new ApiResponse(true,HttpStatus.CREATED,"Your reply added.", new ArrayList<>());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
     }

    @PutMapping("/comment/reply")
    public ResponseEntity<ApiResponse> updateReply(@RequestParam Long replyId, @RequestBody Reply reply){
        postService.updateReply(replyId, reply);
        ApiResponse response = new ApiResponse(true,HttpStatus.OK,"Reply  updated", new ArrayList<>());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/comment/reply")
    public ResponseEntity<ApiResponse> deleteReply(@RequestParam Long replyId){
        postService.deleteReply(replyId);
        ApiResponse response = new ApiResponse(true,HttpStatus.OK,"Reply deleted", new ArrayList<>());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    //post Vote
    @PostMapping("/{postId}/vote")
    public ResponseEntity<ApiResponse> postVote(@PathVariable Long postId, @RequestParam(value = "upVote", required = false) String upVote, @RequestParam(value = "downVote", required = false) String downVote){
        postService.postVote(postId, upVote, downVote);
        ApiResponse response = new ApiResponse<>(true, HttpStatus.OK, "Post Voted",new ArrayList<>());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{postId}/vote")
    public ResponseEntity<ApiResponse> updatePostVote(@PathVariable Long postId, @RequestParam(value = "upVote", required = false) String upVote, @RequestParam(value = "downVote", required = false) String downVote){
        postService.postVoteUpdate(postId, upVote, downVote);
        ApiResponse response = new ApiResponse<>(true, HttpStatus.OK, "Post Vote updated",new ArrayList<>());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{postId}/vote-check")
    public ResponseEntity<ApiResponse<Boolean>> userVoteCheck(@PathVariable Long postId){
        ApiResponse response = new ApiResponse<>(true, HttpStatus.OK, "",postService.isUserAlreadyVotedPost(postId));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
