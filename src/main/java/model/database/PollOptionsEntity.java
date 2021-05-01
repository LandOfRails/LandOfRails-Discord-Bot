package model.database;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "PollOptions", schema = "lor-discord-bot", catalog = "")
public class PollOptionsEntity {
    private String voteOption;
    private int votes;
    private PollsEntity pollsByFkPollId;

    @Basic
    @Column(name = "VoteOption")
    public String getVoteOption() {
        return voteOption;
    }

    public void setVoteOption(String voteOption) {
        this.voteOption = voteOption;
    }

    @Basic
    @Column(name = "Votes")
    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PollOptionsEntity that = (PollOptionsEntity) o;
        return votes == that.votes && Objects.equals(voteOption, that.voteOption);
    }

    @Override
    public int hashCode() {
        return Objects.hash(voteOption, votes);
    }

    @ManyToOne
    @JoinColumn(name = "FK_Poll_ID", referencedColumnName = "ID", nullable = false)
    public PollsEntity getPollsByFkPollId() {
        return pollsByFkPollId;
    }

    public void setPollsByFkPollId(PollsEntity pollsByFkPollId) {
        this.pollsByFkPollId = pollsByFkPollId;
    }
}
