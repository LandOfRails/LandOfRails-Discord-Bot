package model;

public class ServerState {

    public String uuid;
    public String state;

    public ServerState(String uuid, String state) {
        this.uuid = uuid;
        this.state = state;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
