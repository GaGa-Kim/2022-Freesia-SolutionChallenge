package com.freesia.imyourfreesia.domain.cheering;

import com.freesia.imyourfreesia.domain.BaseTimeEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Cheering extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cheeringId")
    private Long id;

    private String recipientEmail;

    private String senderEmail;

    @Builder
    public Cheering(Long id, String recipientEmail, String senderEmail) {
        this.id = id;
        this.recipientEmail = recipientEmail;
        this.senderEmail = senderEmail;
    }
}
