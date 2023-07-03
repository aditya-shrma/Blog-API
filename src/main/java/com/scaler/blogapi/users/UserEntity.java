package com.scaler.blogapi.users;

import com.scaler.blogapi.articles.ArticlesEntity;
import com.scaler.blogapi.commons.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity(name = "users")
@Getter
@Setter
public class UserEntity extends BaseEntity {
    @Column(unique = true, nullable = false, length = 50)
    String username;
    String password;
     //TODO: hash password
     //TODO: add auth token field
    String email;
    String bio;
    String image;

    @ManyToMany(mappedBy = "likedBy")
    List<ArticlesEntity> likedArticles;

    @ManyToMany
    @JoinTable(
            name = "follows",
            joinColumns = @JoinColumn(name = "follower_id"),
            inverseJoinColumns = @JoinColumn(name = "following_id")
    )
    List<UserEntity> following;

    @ManyToMany(mappedBy = "following")
    List<UserEntity> followers;
}
