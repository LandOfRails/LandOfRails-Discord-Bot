package model.database;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Users", schema = "lor-discord-bot", catalog = "")
public class UsersEntity {
    private long memberId;
    private String discordName;
    private int messageCount;

    @Id
    @Column(name = "MemberID", nullable = false)
    public long getMemberId() {
        return memberId;
    }

    public void setMemberId(long memberId) {
        this.memberId = memberId;
    }

    @Basic
    @Column(name = "DiscordName", nullable = false, length = -1)
    public String getDiscordName() {
        return discordName;
    }

    public void setDiscordName(String discordName) {
        this.discordName = discordName;
    }

    @Basic
    @Column(name = "MessageCount", nullable = false)
    public int getMessageCount() {
        return messageCount;
    }

    public void setMessageCount(int messageCount) {
        this.messageCount = messageCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsersEntity that = (UsersEntity) o;
        return memberId == that.memberId && messageCount == that.messageCount && Objects.equals(discordName, that.discordName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberId, discordName, messageCount);
    }
}
