package model.database;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "Polls", schema = "lor-discord-bot", catalog = "")
public class PollsEntity {
    private int id;
    private Long memberId;
    private String question;
    private Timestamp startDatetime;
    private Timestamp endDatetime;
    private byte teamVoting;
    private Collection<PollOptionsEntity> pollOptionsById;
    private UsersEntity usersByMemberId;

    @Id
    @Column(name = "ID", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "Question", nullable = false, length = -1)
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    @Basic
    @Column(name = "Start_datetime", nullable = false)
    public Timestamp getStartDatetime() {
        return startDatetime;
    }

    public void setStartDatetime(Timestamp startDatetime) {
        this.startDatetime = startDatetime;
    }

    @Basic
    @Column(name = "End_Datetime", nullable = false)
    public Timestamp getEndDatetime() {
        return endDatetime;
    }

    public void setEndDatetime(Timestamp endDatetime) {
        this.endDatetime = endDatetime;
    }

    @Basic
    @Column(name = "TeamVoting", nullable = false)
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
        return id == that.id && teamVoting == that.teamVoting && Objects.equals(memberId, that.memberId) && Objects.equals(question, that.question) && Objects.equals(startDatetime, that.startDatetime) && Objects.equals(endDatetime, that.endDatetime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, memberId, question, startDatetime, endDatetime, teamVoting);
    }

    @OneToMany(mappedBy = "pollsByFkPollId")
    public Collection<PollOptionsEntity> getPollOptionsById() {
        return pollOptionsById;
    }

    public void setPollOptionsById(Collection<PollOptionsEntity> pollOptionsById) {
        this.pollOptionsById = pollOptionsById;
    }

    @ManyToOne
    @JoinColumn(name = "MemberID", referencedColumnName = "MemberID")
    public UsersEntity getUsersByMemberId() {
        return usersByMemberId;
    }

    public void setUsersByMemberId(UsersEntity usersByMemberId) {
        this.usersByMemberId = usersByMemberId;
    }
}
