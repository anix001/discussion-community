package com.DiscussionCommunity.domain;

import com.DiscussionCommunity.domain.dto.UserDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "_community")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Community extends Auditable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "_user_community",
            joinColumns = {
                    @JoinColumn(name = "community_id", referencedColumnName = "id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name="user_id", referencedColumnName = "id")
            }
    )
//    @JsonIgnore
    private List<User> userList;

    @Transient
    private List<UserDto> users;

}
