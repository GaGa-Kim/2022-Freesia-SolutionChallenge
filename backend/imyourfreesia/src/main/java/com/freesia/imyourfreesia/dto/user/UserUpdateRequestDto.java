package com.freesia.imyourfreesia.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserUpdateRequestDto {
    private String nickName;


    @Builder
    public UserUpdateRequestDto(String nickName) {
        this.nickName = nickName;
    }
}
