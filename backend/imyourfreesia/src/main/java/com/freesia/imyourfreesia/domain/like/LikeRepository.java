package com.freesia.imyourfreesia.domain.like;

import com.freesia.imyourfreesia.domain.community.Community;
import com.freesia.imyourfreesia.domain.user.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    List<Like> findAllByCommunity(Community community);

    Long countByCommunity(Community community);

    List<Like> findByUser(User user);

    void deleteById(Long id);
}
