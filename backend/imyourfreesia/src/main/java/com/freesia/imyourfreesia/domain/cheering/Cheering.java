package com.freesia.imyourfreesia.domain.cheering;

import com.freesia.imyourfreesia.domain.BaseTimeEntity;
import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(notes = "응원 아이디", dataType = "Long", example = "1")
    @Column(name = "cheeringId")
    private Long id;

    @ApiModelProperty(notes = "응원 받은 회원", dataType = "String", example = "reboot@gmail.com")
    private String recipientEmail;

    @ApiModelProperty(notes = "응원 누른 회원", dataType = "String", example = "freesia@gmail.com")
    private String senderEmail;

    @Builder
    public Cheering(Long id, String recipientEmail, String senderEmail) {
        this.id = id;
        this.recipientEmail = recipientEmail;
        this.senderEmail = senderEmail;
    }
}
