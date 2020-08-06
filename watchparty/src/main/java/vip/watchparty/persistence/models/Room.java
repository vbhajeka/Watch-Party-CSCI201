package vip.watchparty.persistence.models;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

public class Room {

    private String id;
    private List<User> users = new ArrayList<>();
    private List<Video> videoQueue = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<User> getUsers() {
        return users;
    }

    public void addUser(User user) {
        this.users.add(user);
    }

    public List<Video> getVideoQueue() {
        return videoQueue;
    }

    public void addToQueue(Video video) {
        this.videoQueue.add(video);}
}
