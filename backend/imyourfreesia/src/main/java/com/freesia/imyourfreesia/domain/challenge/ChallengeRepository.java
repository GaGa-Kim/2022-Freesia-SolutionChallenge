package com.freesia.imyourfreesia.domain.challenge;

import com.freesia.imyourfreesia.domain.user.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
    @Query("SELECT c FROM Challenge c ORDER BY c.createdDate DESC")
    List<Challenge> findAllDesc();

    List<Challenge> findByUser(User user);

    void deleteById(Long id);
}
