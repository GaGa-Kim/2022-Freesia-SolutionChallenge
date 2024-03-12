package com.freesia.imyourfreesia.dto.user;

import com.freesia.imyourfreesia.domain.user.GoalMsg;
import com.freesia.imyourfreesia.domain.user.User;
import java.time.LocalDate;
import java.time.Period;
import lombok.Getter;

@Getter
public class UserResponseDto {
    private final Long id;
    private final String username;
    private final String loginId;
    private final String email;
    private final String nickName;
    private int days;
    private String goalMsg;
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