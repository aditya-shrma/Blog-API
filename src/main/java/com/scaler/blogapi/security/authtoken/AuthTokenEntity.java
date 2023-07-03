package com.scaler.blogapi.security.authtoken;

import com.scaler.blogapi.users.UserEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity(name = "auth_tokens")
@Getter
@Setter
public class AuthTokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne // used many to one as the user can use multiple client like mobile, web etc
    private UserEntity user;
}
