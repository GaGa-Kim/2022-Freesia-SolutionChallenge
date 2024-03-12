package com.freesia.imyourfreesia.domain.emoticon;

import com.freesia.imyourfreesia.domain.challenge.Challenge;
import com.freesia.imyourfreesia.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmoticonRepository extends JpaRepository<Emoticon, Long> {
    void deleteByUserAndChallengeAndName(User user, Challenge challenge, String name);

    Long countByChallengeAndUserAndName(Challenge challenge, User user, String name);

    Long countByChallengeAndName(Challenge challenge, String name);
}

