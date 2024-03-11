package com.freesia.imyourfreesia.domain.community;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityPhotoRepository extends JpaRepository<CommunityPhoto, Long> {
    List<CommunityPhoto> findAllByCommunity(Community community);
}
