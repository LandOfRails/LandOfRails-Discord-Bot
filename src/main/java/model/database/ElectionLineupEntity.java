package model.database;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "ElectionLineup", schema = "lor-discord-bot", catalog = "")
public class ElectionLineupEntity {
    private int id;
    private long memberId;
    private long byMemberId;
    private UsersEntity usersByMemberId;
    private UsersEntity usersByByMemberId;

    @Id
    @Column(name = "ID", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ElectionLineupEntity that = (ElectionLineupEntity) o;
        return id == that.id && memberId == that.memberId && byMemberId == that.byMemberId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, memberId, byMemberId);
    }

    @ManyToOne
    @JoinColumn(name = "MemberID", referencedColumnName = "MemberID", nullable = false)
    public UsersEntity getUsersByMemberId() {
        return usersByMemberId;
    }

    public void setUsersByMemberId(UsersEntity usersByMemberId) {
        this.usersByMemberId = usersByMemberId;
    }

    @ManyToOne
    @JoinColumn(name = "ByMemberID", referencedColumnName = "MemberID", nullable = false)
    public UsersEntity getUsersByByMemberId() {
        return usersByByMemberId;
    }

    public void setUsersByByMemberId(UsersEntity usersByByMemberId) {
        this.usersByByMemberId = usersByByMemberId;
    }
}
