package com.freesia.imyourfreesia.domain.youtube;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface YoutubeRepository extends JpaRepository<Youtube, Long> {
    List<Youtube> findAll();
}