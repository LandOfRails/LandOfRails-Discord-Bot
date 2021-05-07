package model.database;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "launcherAccess", schema = "lor-discord-bot", catalog = "")
public class LauncherAccessEntity {
    private int id;
    private long memberId;
    private String modpackShortcut;

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
    @Column(name = "ModpackShortcut", nullable = false, length = 64)
    public String getModpackShortcut() {
        return modpackShortcut;
    }

    public void setModpackShortcut(String modpackShortcut) {
        this.modpackShortcut = modpackShortcut;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LauncherAccessEntity that = (LauncherAccessEntity) o;
        return id == that.id && memberId == that.memberId && Objects.equals(modpackShortcut, that.modpackShortcut);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, memberId, modpackShortcut);
    }
}
