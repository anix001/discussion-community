package com.DiscussionCommunity.domain;

import com.DiscussionCommunity.domain.dto.CommentDto;
import com.DiscussionCommunity.domain.enumeration.VoteStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "_posts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Post extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    @Transient
    private MultipartFile image;
    @Column(name = "file_path")
    private String filePath;
    @Column(name="post_vote")
    private Long postVote = 0L;
//    @Transient
//    @Enumerated(EnumType.STRING)
//    private VoteStatus userVoteStatus;
    @Transient
    private List<CommentDto> postComments = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    private List<Comment> comments = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
