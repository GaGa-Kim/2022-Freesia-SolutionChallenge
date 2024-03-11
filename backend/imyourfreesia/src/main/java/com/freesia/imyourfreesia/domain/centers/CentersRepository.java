package com.freesia.imyourfreesia.domain.centers;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CentersRepository extends JpaRepository<Centers, Long> {
    List<Centers> findByAddressContains(String address);
}