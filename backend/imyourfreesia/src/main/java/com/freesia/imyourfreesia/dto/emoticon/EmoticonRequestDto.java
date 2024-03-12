package com.freesia.imyourfreesia.dto.emoticon;

import com.freesia.imyourfreesia.domain.emoticon.Emoticon;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmoticonRequestDto {
    @ApiModelProperty(notes = "이모티콘 작성자 이메일")
    @Email
    private String email;

    @ApiModelProperty(notes = "챌린지 아이디")
    @NotNull
    private Long challengeId;

    @ApiModelProperty(notes = "이모티콘 이름")
    @NotBlank
    private String emoticonName;

    @Builder
    public EmoticonRequestDto(String email, Long challengeId, String emoticonName) {
        this.email = email;
        this.challengeId = challengeId;
        this.emoticonName = emoticonName;
    }

    public Emoticon toEntity() {
        return Emoticon.builder()
                .name(emoticonName)
                .build();
    }
}