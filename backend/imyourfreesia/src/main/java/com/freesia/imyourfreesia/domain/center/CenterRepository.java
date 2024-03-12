package com.freesia.imyourfreesia.domain.center;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CenterRepository extends JpaRepository<Center, Long> {
    List<Center> findByAddressContains(String address);
}