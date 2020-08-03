package vip.watchparty.persistence.models;

//Model for player events relayed for video sync
public class PlayerEvent {

    private String timeStamp;

    public enum EventType {
        PAUSE,
        PLAY,
        SCRUB
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
