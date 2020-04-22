package io.snow.model.vo;

import java.time.Instant;

/**
 * 成员战斗力变化Vo
 */
public class FightChangeVo extends BaseVo {
    private Long id;
    private Long memberId;
    private Instant changeDate;
    private Long fightVal;

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Instant getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(Instant changeDate) {
        this.changeDate = changeDate;
    }

    public Long getFightVal() {
        return fightVal;
    }

    public void setFightVal(Long fightVal) {
        this.fightVal = fightVal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
