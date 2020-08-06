
voting()

function voting() {
    document.querySelector("#yt-search-btn")
        .addEventListener("click", async function () {

            let userSearch = document.querySelector("#yt-searchbar").value;


            let response = await fetch(`http://localhost:8081/api/youtubeSearch?query=${userSearch}`);

            let json = await response.json();
            
            let videos = json.videos;

            
            //Loop thru each video in the response
            for(let i=0; i < videos.length; i++) {
            	let video = videos[i];
            
                let videoTitle = video.videoTitle;
                let thumbnail = video.thumbnail;

                //Append to popup
                let html = 
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
	                    	<button type="button" class="btn-lg btn-success" id="voteButton" onClick="add_to_queue(${video})" >Add</button>
	                	</div>
	                </div>`;
                
                document.getElementById("pop-up-body-search").innerHTML += html;
                
            }

            //Show the popup
            showPopupSearch();










        });
}

//Allows user to add a video up next
async function add_to_queue(video) {

    console.log(video);
    //Maps to POJO on backend
    let videoJSON = {
        id: video.videoID,
        thumbnailUrl: video.thumbnail,
        title: video.videoTitle
    };

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

