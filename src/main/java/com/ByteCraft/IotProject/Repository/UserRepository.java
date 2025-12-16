package com.ByteCraft.IotProject.Repository;

import com.ByteCraft.IotProject.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
/*

 Burada User bulacagız
 */
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
