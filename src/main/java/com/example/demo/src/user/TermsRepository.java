package com.example.demo.src.user;

import com.example.demo.src.user.entity.Terms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TermsRepository extends JpaRepository<Terms, Long> {
    @Query(
            "SELECT t FROM Terms t " +
                    "WHERE t.user.id = :userId " +
                    "AND t.state = com.example.demo.common.entity.BaseEntity$State.ACTIVE "
    )
    Optional<Terms> findByUserId(@Param("userId") Long userId);

    @Query(
            "SELECT t FROM Terms t JOIN FETCH t.user " +
                    "WHERE t.consentDate <= :date " +
                    "AND t.state = com.example.demo.common.entity.BaseEntity$State.ACTIVE "
    )
    List<Terms> findAllByConsentDate(@Param("date") LocalDate date);
}
