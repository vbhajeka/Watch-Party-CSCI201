
voting()

function voting() {
    document.querySelector("#yt-search-btn")
        .addEventListener("click", async function () {

            let userSearch = document.querySelector("#yt-searchbar").value;
            
            showPopupSearch();

            document.getElementById("pop-up-body-search").innerHTML = "Searching for videos...";

            let response = await fetch(`http://localhost:8081/api/youtubeSearch?query=${userSearch}`);

            let json = await response.json();
            
            let videos = json.videos;

            
            document.getElementById("pop-up-body-search").innerHTML = "";
            
            //Loop thru each video in the response
            for(let i=0; i < videos.length; i++) {
            	let video = videos[i];
            
                let videoTitle = video.videoTitle;
                let thumbnail = video.thumbnail;


                let elementId = "addButton" + i;
                //Append to popup
                let htmlElement =
	                `<div class="youtube-result-container">
	                    <div>
		                    <img
		                        class ="yt-thumbnail"
		                        src="${thumbnail}" alt=""
		                    >
		                </div>
	                    <div>
	                    	<p class="yt-title">${videoTitle}</p>
	                    </div>
	                    <div>
	                    	<button type="button" class="btn-lg btn-success voteButton" id=addButton">Add</button>
	                	</div>
	                </div>`;


                document.getElementById("pop-up-body-search").innerHTML += htmlElement;
            }

            let buttons = document.querySelectorAll("#addButton");
            console.log(buttons);

        });
}

//Allows user to add a video up next
async function add_to_queue(video, thumb) {

    console.log("adding to queue");
    console.log(video, thumb);
    //Maps to POJO on backend
    let videoJSON = {
        id: videoId,
        thumbnailUrl:thumbnail,
        title: title
    };

    console.log(videoJSON);

    //POST to the server
    let response = await fetch(
        window.location.href, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
            body: JSON.stringify(videoJSON)
        });

    let result = await response.text();
    console.log(result)
}

