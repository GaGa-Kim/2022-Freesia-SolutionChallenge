package com.freesia.imyourfreesia.domain.file;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityFileRepository extends JpaRepository<CommunityFile, Long> {
}
