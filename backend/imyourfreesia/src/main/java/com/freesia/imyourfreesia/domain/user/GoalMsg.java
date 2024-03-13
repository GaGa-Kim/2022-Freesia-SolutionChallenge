package com.freesia.imyourfreesia.domain.user;

import com.freesia.imyourfreesia.domain.BaseTimeEntity;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
public class GoalMsg extends BaseTimeEntity {
    @Id
    @Column(name = "goalMsgId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "목표 아이디", dataType = "Long", example = "1")
    private Long id;

    @Setter
    @OneToOne
    @JoinColumn(name = "userId")
    @ApiModelProperty(notes = "목표 작성 회원", dataType = "User")
    private User user;

    @Column(length = 100)
    @ApiModelProperty(notes = "회원 목표", dataType = "String", example = "꾸준히 노력하기")
    private String goalMsg;

    @Builder
    public GoalMsg(Long id, String goalMsg) {
        this.id = id;
        this.goalMsg = goalMsg;
    }

    public void update(String goalMsg) {
        this.goalMsg = goalMsg;
    }
}
