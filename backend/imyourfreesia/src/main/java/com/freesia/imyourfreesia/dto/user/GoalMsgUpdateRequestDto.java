package com.freesia.imyourfreesia.dto.user;

import com.freesia.imyourfreesia.dto.auth.UserRequestVO;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GoalMsgUpdateRequestDto {
    @ApiModelProperty(notes = "회원 목표")
    @NotBlank
    @Size(min = 3, max = 100)
    private String goalMsg;

    @Builder
    public GoalMsgUpdateRequestDto(UserRequestVO userRequestVO) {
        this.goalMsg = userRequestVO.getGoalMsg();
    }
}
