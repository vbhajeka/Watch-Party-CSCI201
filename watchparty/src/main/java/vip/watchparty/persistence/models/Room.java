package vip.watchparty.persistence.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class Room {

    private String id;
    //Default video id hello by Adelle
    private String currentVideoId = "YQHsXMglC9A";
    private List<User> users = Collections.synchronizedList(new ArrayList<>());

    private List<Video> videoQueue = Collections.synchronizedList(new ArrayList<>());

    //Thread safe map for tallying votes
    private Map<String,Integer> upNextVotes = Collections.synchronizedMap(new HashMap<String, Integer>());

    //Flag for first queue event - used to handle clearing of
    // upNextVotes and videoQueue objects
    public AtomicBoolean firstQueue = new AtomicBoolean(true);
    //Flag for first queue event
    public AtomicBoolean firstVote = new AtomicBoolean(true);


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public void setVideoId(String videoId) {
        this.currentVideoId = id;
    }

    public String getVideoId() {
        return this.currentVideoId;
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


    //Add a vote for a given videoId
    public void addVote(String videoId) {
        //Increment vote count
        if(upNextVotes.containsKey(videoId)) {
            Integer currVotes = upNextVotes.get(videoId);
            currVotes += 1;
        }
        else { //Add new vote count
            upNextVotes.put(videoId, 1);
        }

    }

    //Synchronized bc iteration occurs
    public String get_winning_vote() {

        //Default value just in case
        String topVideo = "YQHsXMglC9A";
        Integer maxVotes = -1;

        //Iterate and find top video
        synchronized(upNextVotes) {
            for(String video: upNextVotes.keySet()) {
                Integer votes = upNextVotes.get(video);

                if(votes > maxVotes) {
                    topVideo = video;
                    maxVotes = votes;
                }
            }
        }

        return topVideo;
    }


    public void clear_votes() {
        this.upNextVotes.clear();
    }

    public void clear_queue() {
        this.videoQueue.clear();
    }

}
