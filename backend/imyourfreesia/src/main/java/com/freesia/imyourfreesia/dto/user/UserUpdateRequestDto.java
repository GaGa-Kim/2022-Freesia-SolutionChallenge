package com.freesia.imyourfreesia.dto.user;

import com.freesia.imyourfreesia.dto.auth.UserRequestVO;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserUpdateRequestDto {
    @ApiModelProperty(notes = "회원 닉네임")
    @NotNull
    @Size(min = 1, max = 100)
    private String nickName;

    @Builder
    public UserUpdateRequestDto(UserRequestVO userRequestVO) {
        this.nickName = userRequestVO.getNickName();
    }
}
