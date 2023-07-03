package com.scaler.blogapi.articles;

import com.scaler.blogapi.commons.BaseEntity;
import com.scaler.blogapi.users.UserEntity;

import javax.persistence.*;
import java.util.List;

@Entity(name = "articles")
public class ArticlesEntity extends BaseEntity {

    @Column(nullable = false, unique = true,length = 150)
    String slug;
    @Column(nullable = false, length = 200)
    String title;

    String subtitle;
    @Column(nullable = false, length = 8000)
    String body;

    //TODO: add tags field
    //String[] tagList[] TODO: implement this

    @ManyToOne // Relationship of article with user
    UserEntity author;
    @ManyToMany
    @JoinTable(
            name = "likes",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    List<UserEntity> likedBy;

}
