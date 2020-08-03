package vip.watchparty.persistence.models;

//Model for player events relayed for video sync
public class PlayerEvent {

    private String timeStamp;
    private EventType eventType;

    public enum EventType {
        PAUSE,
        PLAY,
        SCRUB
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
