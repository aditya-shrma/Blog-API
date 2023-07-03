package com.scaler.blogapi.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public interface UsersRepository extends JpaRepository<UserEntity,Integer> {
    UserEntity findByUsername(String username);

}
