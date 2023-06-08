package com.DiscussionCommunity.domain;

import com.DiscussionCommunity.domain.enumeration.VoteStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "_post_vote")
public class PostVote extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private VoteStatus voteStatus = VoteStatus.NEUTRAL;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public PostVote(VoteStatus voteStatus) {
        this.voteStatus = voteStatus;
    }
}
