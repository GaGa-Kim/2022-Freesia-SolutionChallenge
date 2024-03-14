package com.freesia.imyourfreesia.domain.emoticon;

import com.freesia.imyourfreesia.domain.challenge.Challenge;
import com.freesia.imyourfreesia.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface EmoticonRepository extends JpaRepository<Emoticon, Long> {
    Long countByChallengeAndUserAndName(Challenge challenge, User user, EmoticonType emoticonType);

    Long countByChallengeAndName(Challenge challenge, EmoticonType emoticonType);

    @Transactional
    void deleteByUserAndChallengeAndName(User user, Challenge challenge, EmoticonType emoticonType);
}

