package com.freesia.imyourfreesia.dto.user;

import com.freesia.imyourfreesia.dto.auth.UserRequestVO;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserUpdateRequestDto {
    @ApiModelProperty(notes = "회원 닉네임", dataType = "String", example = "freesia")
    @NotEmpty
    private String nickName;

    @Builder
    public UserUpdateRequestDto(UserRequestVO userRequestVO) {
        this.nickName = userRequestVO.getNickName();
    }
}