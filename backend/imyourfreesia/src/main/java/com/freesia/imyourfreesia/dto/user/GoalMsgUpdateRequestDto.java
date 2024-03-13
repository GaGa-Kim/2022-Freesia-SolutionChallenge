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
public class GoalMsgUpdateRequestDto {
    @ApiModelProperty(notes = "회원 목표", dataType = "String", example = "꾸준히 노력하기")
    @NotEmpty
    private String goalMsg;

    @Builder
    public GoalMsgUpdateRequestDto(UserRequestVO userRequestVO) {
        this.goalMsg = userRequestVO.getGoalMsg();
    }
}
