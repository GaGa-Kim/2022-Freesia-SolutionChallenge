package com.freesia.imyourfreesia.domain.comment;

import com.freesia.imyourfreesia.domain.community.Community;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByCommunity(Community community);
}