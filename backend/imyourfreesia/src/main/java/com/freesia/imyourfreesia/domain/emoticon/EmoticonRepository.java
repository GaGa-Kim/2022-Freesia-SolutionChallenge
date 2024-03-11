package com.freesia.imyourfreesia.domain.emoticon;

import com.freesia.imyourfreesia.domain.challenge.Challenge;
import com.freesia.imyourfreesia.domain.user.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmoticonRepository extends JpaRepository<Emoticon, Long> {
    Optional<Emoticon> findByUserAndChallengeAndName(User user, Challenge challenge, String name);

    void deleteByUserAndChallengeAndName(User user, Challenge challenge, String name);

    List<Emoticon> findByChallengeAndName(Challenge challenge, String name);

    List<Emoticon> findByChallengeAndUserAndName(Challenge challenge, User user, String name);
}

