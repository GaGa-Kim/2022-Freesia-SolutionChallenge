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
    @ApiModelProperty(notes = "회원 아이디", dataType = "Long", example = "1")
    private final Long userId;

    @ApiModelProperty(notes = "회원 이름", dataType = "String", example = "freesia")
    private final String username;

    @ApiModelProperty(notes = "회원 로그인 아이디", dataType = "String", example = "freesia123")
    private final String loginId;

    @ApiModelProperty(notes = "회원 이메일", dataType = "String", example = "freesia@gmail.com")
    private final String email;

    @ApiModelProperty(notes = "회원 닉네임", dataType = "String", example = "freesia")
    private final String nickName;

    @ApiModelProperty(notes = "회원 목표 유지 일자", dataType = "int", example = "1")
    private int days;

    @ApiModelProperty(notes = "회원 목표", dataType = "String", example = "꾸준히 노력하기")
    private String goalMsg;

    @JsonIgnore
    @ApiModelProperty(notes = "회원 목표 수정 시간", dataType = "LocalDate", example = "20XX.XX.XX")
    private LocalDate goalMsgModifiedDate;

    public UserResponseDto(User user, GoalMsg goalMsg) {
        this.userId = user.getId();
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