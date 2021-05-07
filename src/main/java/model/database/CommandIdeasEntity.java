package model.database;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "commandIdeas", schema = "lor-discord-bot", catalog = "")
public class CommandIdeasEntity {
    private int id;
    private long memberId;
    private String memberUsername;
    private String command;
    private Timestamp timestamp;
    private String jumpUrl;
    private short count;

    @Id
    @Column(name = "ID", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "MemberID", nullable = false)
    public long getMemberId() {
        return memberId;
    }

    public void setMemberId(long memberId) {
        this.memberId = memberId;
    }

    @Basic
    @Column(name = "MemberUsername", nullable = false, length = -1)
    public String getMemberUsername() {
        return memberUsername;
    }

    public void setMemberUsername(String memberUsername) {
        this.memberUsername = memberUsername;
    }

    @Basic
    @Column(name = "Command", nullable = false, length = -1)
    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    @Basic
    @Column(name = "Timestamp", nullable = true)
    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Basic
    @Column(name = "JumpUrl", nullable = false, length = -1)
    public String getJumpUrl() {
        return jumpUrl;
    }

    public void setJumpUrl(String jumpUrl) {
        this.jumpUrl = jumpUrl;
    }

    @Basic
    @Column(name = "Count", nullable = false)
    public short getCount() {
        return count;
    }

    public void setCount(short count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommandIdeasEntity that = (CommandIdeasEntity) o;
        return id == that.id && memberId == that.memberId && count == that.count && Objects.equals(memberUsername, that.memberUsername) && Objects.equals(command, that.command) && Objects.equals(timestamp, that.timestamp) && Objects.equals(jumpUrl, that.jumpUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, memberId, memberUsername, command, timestamp, jumpUrl, count);
    }
}
