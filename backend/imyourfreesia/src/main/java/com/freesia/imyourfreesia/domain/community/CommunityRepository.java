package com.freesia.imyourfreesia.domain.community;

import com.freesia.imyourfreesia.domain.user.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityRepository extends JpaRepository<Community, Long> {
    List<Community> findByCategory(String category);

    List<Community> findByUser(User user);
}
