package com.freesia.imyourfreesia.domain.challenge;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChallengePhotoRepository extends JpaRepository<ChallengePhoto, Long> {
    List<ChallengePhoto> findAllByChallenge(Challenge challenge);

    List<ChallengePhoto> findByChallenge(Challenge challenge);
}
