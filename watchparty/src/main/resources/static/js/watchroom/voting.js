
//Run
search_and_add_videos()


function search_and_add_videos() {
    document.querySelector("#yt-search-btn")
        .addEventListener("click", async function () {

            let userSearch = document.querySelector("#yt-searchbar").value;
            
            showPopupSearch();

            document.getElementById("pop-up-body-search").innerHTML = "Searching for videos...";

            let response = await fetch(`http://localhost:8081/api/youtubeSearch?query=${userSearch}`);

            let json = await response.json();
            
            let videos = json.videos;

            //Clear the "loading" indicator
            document.getElementById("pop-up-body-search").innerHTML = "";
            
            //Loop thru each video in the response
            let idToVidMap = {} //Map for adding event handlers
            for(let i=0; i < videos.length; i++) {

                //Get the video
            	let video = videos[i];

            	//Add to the map
                idToVidMap[video.videoID] = video;



                //Append each video to the popup with HTML id corresponding to unique
                // video id
                let htmlElement =
	                `<div class="youtube-result-container">
	                    <div>
		                    <img
		                        class ="yt-thumbnail"
		                        src="${video.thumbnail}" alt=""
		                    >
		                </div>
	                    <div>
	                    	<p class="yt-title">${video.videoTitle}</p>
	                    </div>
	                    <div>

	                    	<button type="button" class="btn-lg btn-success" id="${video.videoID}" >Add</button>

	                	</div>
	                </div>`;


                document.getElementById("pop-up-body-search").innerHTML += htmlElement;
            }

            //Add click handlers to the newly created video elements
           for(let video of Object.keys(idToVidMap)) {

                document.getElementById(video).addEventListener("click", () => {
                    //Add the video to queue
                    add_to_queue(idToVidMap[video]);
                });
           }


        });
}

//Allows user to add a video up next
//@params video: video json object
//@return: void: posts to backend
async function add_to_queue(video) {


    //Maps to POJO on backend
    let videoJSON = {
        id: video.videoID,
        thumbnailUrl:video.thumbnail,
        title: video.videoTitle
    };


    //POST to the server
    let response = await fetch(
        `http://localhost:8081/room/${get_room_id()}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
            body: JSON.stringify(videoJSON)
        });

    let result = await response.text();
    console.log(result)
}

