package model.database;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "Polls", schema = "lor-discord-bot", catalog = "")
public class PollsEntity {
    private int id;
    private Date startDatetime;
    private Date endDatetime;
    private byte teamVoting;

    @Id
    @Column(name = "ID")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "Start_datetime")
    public Date getStartDatetime() {
        return startDatetime;
    }

    public void setStartDatetime(Date startDatetime) {
        this.startDatetime = startDatetime;
    }

    @Basic
    @Column(name = "End_Datetime")
    public Date getEndDatetime() {
        return endDatetime;
    }

    public void setEndDatetime(Date endDatetime) {
        this.endDatetime = endDatetime;
    }

    @Basic
    @Column(name = "TeamVoting")
    public byte getTeamVoting() {
        return teamVoting;
    }

    public void setTeamVoting(byte teamVoting) {
        this.teamVoting = teamVoting;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PollsEntity that = (PollsEntity) o;
        return id == that.id && teamVoting == that.teamVoting && Objects.equals(startDatetime, that.startDatetime) && Objects.equals(endDatetime, that.endDatetime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startDatetime, endDatetime, teamVoting);
    }
}
