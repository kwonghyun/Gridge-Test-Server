package com.example.demo.src.user;

import com.example.demo.src.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(
            "SELECT u FROM  User u " +
                    "WHERE (u.id = :userId) " +
                    "AND (u.state = com.example.demo.common.entity.BaseEntity$State.ACTIVE) "
    )
    Optional<User> findActiveUserById(@Param("userId") Long userId);

    @Query(
            "SELECT u FROM  User u JOIN FETCH u.billingKey " +
                    "WHERE (u.id = :userId) " +
                    "AND (u.state = com.example.demo.common.entity.BaseEntity$State.ACTIVE) "
    )
    Optional<User> findActiveUserWithBillingKeyById(@Param("userId") Long userId);

    @Query(
            "SELECT u FROM User u " +
                    "WHERE (u.loginId = :loginId) " +
                    "AND (u.state = com.example.demo.common.entity.BaseEntity$State.ACTIVE) "
    )
    Optional<User> findActiveUserByLoginId(@Param("loginId") String loginId);

    @Query(
            "SELECT u FROM User u " +
                    "WHERE (u.loginId = :loginId) "
    )
    Optional<User> findUserByLoginId(@Param("loginId") String loginId);


}
