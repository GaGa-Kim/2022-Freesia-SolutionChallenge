package com.freesia.imyourfreesia.dto.user;

import com.freesia.imyourfreesia.domain.user.GoalMsg;
import com.freesia.imyourfreesia.domain.user.User;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import java.time.Period;
import lombok.Getter;
import net.minidev.json.annotate.JsonIgnore;

@Getter
public class UserResponseDto {
    @ApiModelProperty(notes = "회원 아이디")
    private final Long id;

    @ApiModelProperty(notes = "회원 이름")
    private final String username;

    @ApiModelProperty(notes = "회원 아이디")
    private final String loginId;

    @ApiModelProperty(notes = "회원 이메일")
    private final String email;

    @ApiModelProperty(notes = "회원 닉네임")
    private final String nickName;

    @ApiModelProperty(notes = "회원 목표 유지 일자")
    private int days;

    @ApiModelProperty(notes = "회원 목표")
    private String goalMsg;

    @JsonIgnore
    @ApiModelProperty(notes = "회원 목표 수정 시간")
    private LocalDate goalMsgModifiedDate;

    public UserResponseDto(User user, GoalMsg goalMsg) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.loginId = user.getLoginId();
        this.email = user.getEmail();
        this.nickName = user.getNickName();
        getGoalMsg(goalMsg);
    }

    private void getGoalMsg(GoalMsg goalMsg) {
        if (goalMsg != null) {
            this.goalMsg = goalMsg.getGoalMsg();
            this.goalMsgModifiedDate = goalMsg.getModifiedDate();
            this.days = calculateDays(this.goalMsgModifiedDate);
        } else {
            this.days = 0;
        }
    }

    private int calculateDays(LocalDate goalMsgModifiedDate) {
        LocalDate startDatetime = LocalDate.now();
        Period period = Period.between(goalMsgModifiedDate, startDatetime);
        return period.getDays() + 1;
    }
}