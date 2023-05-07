package com.platform.WebNode.repository;

import com.platform.WebNode.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


//    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);
    User findByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.username = :username")
    public User getUserByUsername(@Param("username") String username);

//    @Modifying
//    @Transactional
//    @Query(value="delete from user where id= ?1", nativeQuery = true)
//    void deleteById(int id);
}
