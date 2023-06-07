package com.DiscussionCommunity.service.impl;

import com.DiscussionCommunity.domain.Comment;
import com.DiscussionCommunity.domain.Post;
import com.DiscussionCommunity.domain.Reply;
import com.DiscussionCommunity.domain.User;
import com.DiscussionCommunity.domain.dto.PostDto;
import com.DiscussionCommunity.domain.enumeration.Role;
import com.DiscussionCommunity.exception.custom.BadRequestException;
import com.DiscussionCommunity.exception.custom.InternalServerException;
import com.DiscussionCommunity.exception.custom.NotFoundException;
import com.DiscussionCommunity.exception.custom.UnauthorizedException;
import com.DiscussionCommunity.repository.CommentRepository;
import com.DiscussionCommunity.repository.PostRepository;
import com.DiscussionCommunity.repository.ReplyRepository;
import com.DiscussionCommunity.repository.UserRepository;
import com.DiscussionCommunity.service.PostService;
import com.DiscussionCommunity.service.UserService;
import com.DiscussionCommunity.service.mapper.custom.CommentMapper;
import com.DiscussionCommunity.service.mapper.custom.PostMapper;
import com.DiscussionCommunity.service.mapper.custom.ReplyMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final UserService userService;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final ReplyMapper replyMapper;
    private final ReplyRepository replyRepository;

    public PostServiceImpl(PostRepository postRepository, PostMapper postMapper, UserService userService, UserRepository userRepository, CommentRepository commentRepository, CommentMapper commentMapper, ReplyMapper replyMapper, ReplyRepository replyRepository) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
        this.userService = userService;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
        this.replyMapper = replyMapper;
        this.replyRepository = replyRepository;
    }

    private final String UPLOAD_FOLDER_PATH = "/home/anix/Documents/DiscussionCommunity/upload/";

    @Override
    public void store(Post post) {
        User user = userRepository.findByEmail(userService.getCurrentLoggedInUser().getUsername()).orElseThrow(()->new NotFoundException("User is not authorized"));

        if (post.getTitle() == null || post.getDescription() == null) {
            throw new BadRequestException("Title and Description are required.");
        }

        if(post.getImage() != null){
        try {
            String filePath = UPLOAD_FOLDER_PATH + post.getImage().getOriginalFilename();
            post.setFilePath(post.getImage().getOriginalFilename());
            post.getImage().transferTo(new File(filePath));
        } catch (Exception e) {
            throw new BadRequestException("Data format does not match");
        }}else{
            post.setFilePath(null);
        }
        post.setUser(user);
        postRepository.save(post);

    }

    @Override
    public List<PostDto> index() {
      List<Post> posts = postRepository.findAll();
      for(Post post : posts){
          List<Comment> comments = post.getComments();
          for(Comment comment: comments){
              comment.setReplyList(replyMapper.toDtoList(comment.getReplies()));
          }
          post.setPostComments(commentMapper.toDtoList(comments));
      }
      return postMapper.toDtoList(posts);
    }

    @Override
    public PostDto get(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new NotFoundException("Post not found"));
        List<Comment> comments = post.getComments();
        for(Comment comment: comments){
            comment.setReplyList(replyMapper.toDtoList(comment.getReplies()));
        }
        post.setPostComments(commentMapper.toDtoList(comments));
        return postMapper.toDto(post);
    }

    @Override
    public byte[] download(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new NotFoundException("Post not found"));
        String filePath = post.getFilePath();
        try {
            return Files.readAllBytes(new File(filePath).toPath());
        } catch (Exception e) {
            throw new InternalServerException("File cannot converted");
        }
    }

    @Override
    public void delete(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new NotFoundException("Post not found"));
        User user = userRepository.findByEmail(userService.getCurrentLoggedInUser().getUsername()).orElseThrow(()->new NotFoundException("User is not authorized"));
        if (post.getUser().getId() == user.getId() || user.getRole() == Role.ADMIN){
            postRepository.deleteById(postId);
        }else {
            throw new UnauthorizedException("You are not authorized to delete this post.");
        }

    }

    @Override
    public void update(Long postId, Post post) {
        Post dbPost = postRepository.findById(postId).orElseThrow(() -> new NotFoundException("Post not found"));
        User user = userRepository.findByEmail(userService.getCurrentLoggedInUser().getUsername()).orElseThrow(()->new NotFoundException("User is not authorized"));

        if (dbPost.getUser().getId() == user.getId() || user.getRole() == Role.ADMIN) {
            if (Stream.of(post.getTitle(), post.getDescription(), post.getImage()).allMatch(Objects::isNull)) {
                throw new BadRequestException("[Title, description, Image]  fields can not be null");
            } else if (post.getImage() != null) {
                try {
                    String filePath = UPLOAD_FOLDER_PATH + post.getImage().getOriginalFilename();
                    dbPost.setFilePath(post.getImage().getOriginalFilename());
                    post.getImage().transferTo(new File(filePath));
                } catch (Exception e) {
                    throw new BadRequestException("Data format does not match");
                }
            } else if (post.getTitle() != null) dbPost.setTitle(post.getTitle());
            else dbPost.setDescription(post.getDescription());
            postRepository.save(dbPost);
        }else {
            throw new UnauthorizedException("You are not authorized to update this post.");
        }
    }


    //comments
    @Override
    public void postComment(Long postId, List<Comment> comment) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new NotFoundException("Post not found"));
        User user = userRepository.findByEmail(userService.getCurrentLoggedInUser().getUsername()).orElseThrow(()->new NotFoundException("User is not authorized"));
        List<Comment> postComments = commentRepository.fetchAllComments(post.getId());
        for(Comment comment1 : comment){
            comment1.setUser(user);
            postComments.add(comment1);
        }
        post.setComments(postComments);
        postRepository.save(post);
    }

    @Override
    public void updatePostComment(Long commentId, Comment comment) {
        Comment dbComment = commentRepository.findById(commentId).orElseThrow(() -> new NotFoundException("Comment is not available."));
        User user = userRepository.findByEmail(userService.getCurrentLoggedInUser().getUsername()).orElseThrow(()->new NotFoundException("User is not authorized"));

        if (dbComment.getUser().getId() == user.getId() || user.getRole() == Role.ADMIN) {
            if (comment.getUserComment() != null) {
                dbComment.setUserComment(comment.getUserComment());
                commentRepository.save(dbComment);
            } else {
                throw new BadRequestException("Comment must not be null");
            }
        }else {
            throw new UnauthorizedException("Not authorized to edit this comment.");
        }
    }

    @Override
    public void deletePostComment(Long commentId) {
        Comment dbComment = commentRepository.findById(commentId).orElseThrow(() -> new NotFoundException("Comment is not available."));
        User user = userRepository.findByEmail(userService.getCurrentLoggedInUser().getUsername()).orElseThrow(()->new NotFoundException("User is not authorized"));
        if (dbComment.getUser().getId() == user.getId() || user.getRole() == Role.ADMIN) {
             commentRepository.deleteById(commentId);
        }else {
            throw new UnauthorizedException("Not authorized to delete this comment.");
        }
    }


    //    reply
    @Override
    public void replyComment( Long commentId, List<Reply> reply) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NotFoundException("Reply not possible"));
        List<Reply> commentReplies = replyRepository.fetchReplyByCommentId(comment.getId());
        User user = userRepository.findByEmail(userService.getCurrentLoggedInUser().getUsername()).orElseThrow(()->new NotFoundException("User Token expired"));
        for (Reply reply1: reply){
            reply1.setUser(user);
            commentReplies.add(reply1);
        }
        comment.setReplies(commentReplies);
        commentRepository.save(comment);
    }

    @Override
    public void updateReply(Long replyId, Reply reply) {
        Reply dbReply = replyRepository.findById(replyId).orElseThrow(() -> new NotFoundException("Reply not found"));
        User user = userRepository.findByEmail(userService.getCurrentLoggedInUser().getUsername()).orElseThrow(()->new NotFoundException("User is not authorized"));
        if (dbReply.getUser().getId() == user.getId() || user.getRole() == Role.ADMIN) {
            dbReply.setCommentReply(reply.getCommentReply());
            replyRepository.save(dbReply);
        }else {
            throw new UnauthorizedException("Not authorized");
        }
    }

    @Override
    public void deleteReply(Long replyId) {
        Reply dbReply = replyRepository.findById(replyId).orElseThrow(() -> new NotFoundException("Reply not found"));
        User user = userRepository.findByEmail(userService.getCurrentLoggedInUser().getUsername()).orElseThrow(()->new NotFoundException("User is not authorized"));
        if (dbReply.getUser().getId() == user.getId() || user.getRole() == Role.ADMIN) {
            replyRepository.deleteById(replyId);
        }else {
            throw new UnauthorizedException("Not authorized");
        }
    }


}
