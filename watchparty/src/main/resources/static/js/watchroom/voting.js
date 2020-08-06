
voting()

function voting() {
    document.querySelector("#yt-search-btn")
        .addEventListener("click", async function () {

            let userSearch = document.querySelector("#yt-searchbar").value;


            let response = await fetch(`http://localhost:8081/api/youtubeSearch?query=${userSearch}`);

            let json = await response.json();
            
            let videos = json.videos;
            
            console.log(json);
            
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
	                    	<button type="button" class="btn-lg btn-success" id="voteButton" onClick="vote('${video.videoID}')" >Vote</button>
	                	</div>
	                </div>`;
                
                document.getElementById("pop-up-body-search").innerHTML += html;
                
            }



            showPopupSearch();
        });
}

function vote(videoID) {
	console.log(videoID);
}