package com.freesia.imyourfreesia.domain.user;

import com.freesia.imyourfreesia.domain.BaseTimeEntity;
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
    private Long id;

    @Setter
    @OneToOne
    @JoinColumn(name = "userId")
    private User user;

    @Column(length = 100)
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
