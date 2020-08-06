
main()

async function main() {

}

function main() {
    document.querySelector("#yt-search-btn")
        .addEventListener("click", async function () {

            let userSearch = document.querySelector("#yt-searchbar").value;


            let response = await fetch(`http://localhost:8081/api/youtubeSearch?query=${userSearch}`);

            let json = await response.json();

            //Add videos to the DOM

            //Loop thru each video in the response
            for(let video of json) {
                let videoTitle = video.videoTitle;
                let thumbnail = video.thumbnail;

                //Append to popup
                let html = `<div class="youtube-result-container">
                    <img
                        class ="yt-thumbnail"
                        src="https://static.politico.com/dims4/default/e47f863/2147483647/resize/1160x%3E/quality/90/?url=https%3A%2F%2Fstatic.politico.com%2Fbe%2Ffd%2Ffa12678b4813a0ba11122c146100%2Fgettyimages-1222600015.jpg" alt="">
                        <p class="yt-title">${videoTitle}</p>
                        <button type="button" class="btn-lg btn-success" id="voteButton">Vote
                        </button>
                </div>`
            }



            showPopupSearch();
        });
}