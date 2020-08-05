package vip.watchparty.controllers;


import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class APIController {

    @GetMapping("/api/youtubeSearch")
    @ResponseBody
	public String youtubeSearch(@RequestParam String query) {
    	return search(query);
	}

	public String search(String searchParam) {
		// move to env somehow
		String apiKey = "AIzaSyDkWux99JIVs3zexdZGFk-pkYz3iahQEis";
		String httpLink = "https://www.googleapis.com/youtube/v3/search?part=snippet&maxResults=5&q=" + 
				searchParam + "&key=" + apiKey;


		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		// HTTP Get code borrowed from Stack Overflow
		// https://stackoverflow.com/questions/27200634/authorization-header-in-http-request-in-linkedin/27200786
		try {
			HttpClient httpClient = HttpClientBuilder.create().build();

			HttpGet httpGet = new HttpGet(httpLink);

			HttpResponse httpResponse = httpClient.execute(httpGet);
			HttpEntity responseEntity = httpResponse.getEntity();
			int responseCode = httpResponse.getStatusLine().getStatusCode();
			switch (responseCode) {
			case 200: {
				String stringResponse = EntityUtils.toString(responseEntity); 
				
				// turn http response into java object
				YoutubeResponse json = gson.fromJson(stringResponse, YoutubeResponse.class);
				JsonReturnObject returnObject = json.getVideoDetails();
				
				
				// turn trimmed http response back into json to send back to front end
				String returnObjectJson = gson.toJson(returnObject);
				return returnObjectJson;
				
			}
			case 500: {
				// server problems ?
				break;
			}
			case 403: {
				// you have no authorization to access that resource
				break;
			}
			}
		} catch (IOException ioe) {
			System.out.println("ioe exception: " + ioe);
		} catch (Exception e) {
			System.out.println("Generic exception, help!!! " + e.getMessage());
		}
		
		return null;
	}
}



// classes to generate responses
class YoutubeResponse {
	private Item [] items;
	
	public JsonReturnObject getVideoDetails() {
		JsonReturnObject o = new JsonReturnObject(items.length);
		for (int i = 0; i < items.length; i++) {
			Item curr = items[i];
			o.addVid(new VideoDetails(curr.getVideoID(), curr.getTitle(), curr.getDes(), curr.getThumbnail()), i);
		}
		return o;
	}
	
}

class Item {
	private ItemID id;
	private Snippet snippet;
	
	public String getVideoID() {
		return id.getVideoID();
	}
	
	public String getTitle() {
		return snippet.getTitle();
	}
	
	public String getDes() {
		return snippet.getDes();
	}
	
	public String getThumbnail() {
		return snippet.getURL();
	}
}

class ItemID {
	private String videoId;
	
	public String getVideoID() {
		return videoId;
	}
}

class Snippet {
	private String title;
	private String description;
	private Thumbnails thumbnails;
	
	public String getTitle() {
		return title;
	}
	
	public String getDes() {
		return description;
	}
	
	public String getURL() {
		return thumbnails.getURL();
	}
}

class Thumbnails {
	private Thumbnail medium;
	
	public String getURL() {
		return medium.getURL();
	}
}

class Thumbnail {
	private String url;
	
	public String getURL() {
		return url;
	}
}

@SuppressWarnings("unused")
class VideoDetails {
	private String videoID;
	private String videoTitle;
	private String thumbnail;
	private String videoDescription;
	
	public VideoDetails(String videoID, String videoTitle, String videoDescription, String thumbnail) {
		this.videoID = videoID;
		this.videoTitle = videoTitle;
		this.thumbnail = thumbnail;
		this.videoDescription = videoDescription;
	}
}

class JsonReturnObject {
	private VideoDetails [] videos;
	
	public JsonReturnObject(int numVids) {
		videos = new VideoDetails[numVids];
	}
	
	public void addVid(VideoDetails v, int index) {
		videos[index] = v;
	}
}