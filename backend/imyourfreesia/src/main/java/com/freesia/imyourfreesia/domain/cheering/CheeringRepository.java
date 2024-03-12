package com.freesia.imyourfreesia.domain.cheering;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheeringRepository extends JpaRepository<Cheering, Long> {
    Long countByRecipientEmail(String recipientEmail);

    List<Cheering> findBySenderEmail(String senderEmail);

    Boolean existsBySenderEmailAndRecipientEmail(String senderEmail, String recipientEmail);

    Long countByCreatedDateBetweenAndRecipientEmail(LocalDate start, LocalDate end, String recipientEmail);

    void deleteById(Long id);
}