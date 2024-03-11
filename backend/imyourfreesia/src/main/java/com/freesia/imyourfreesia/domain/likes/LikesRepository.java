package com.freesia.imyourfreesia.domain.likes;

import com.freesia.imyourfreesia.domain.community.Community;
import com.freesia.imyourfreesia.domain.user.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Long> {
    List<Likes> findAllByCommunity(Community community);

    Long countByCommunity(Community community);

    List<Likes> findByUser(User user);
}
