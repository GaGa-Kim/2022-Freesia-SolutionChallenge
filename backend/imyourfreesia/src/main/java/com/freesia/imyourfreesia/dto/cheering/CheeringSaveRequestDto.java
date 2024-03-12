package com.freesia.imyourfreesia.dto.cheering;

import com.freesia.imyourfreesia.domain.cheering.Cheering;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CheeringSaveRequestDto {
    @ApiModelProperty(notes = "응원 누른 회원")
    @NotBlank
    @Email
    private String senderEmail;

    @ApiModelProperty(notes = "응원 받은 회원")
    @NotBlank
    @Email
    private String recipientEmail;

    @Builder
    public CheeringSaveRequestDto(String senderEmail, String recipientEmail) {
        this.senderEmail = senderEmail;
        this.recipientEmail = recipientEmail;
    }

    public Cheering toEntity() {
        return Cheering.builder()
                .senderEmail(senderEmail)
                .recipientEmail(recipientEmail)
                .build();
    }
}
