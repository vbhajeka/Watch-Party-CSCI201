
main()

async function main() {

}

function main() {
    document.querySelector("#yt-search-btn")
        .addEventListener("click", async function () {

            let userSearch = document.querySelector("#yt-searchbar").value;


            let response = await fetch(`http://localhost:8081/api/youtubeSearch?query=${userSearch}`);

            if(response.ok) {
                let json = await response.json();
            }
            else {
                console.log("Error");
            }

            console.log(json);

            //Apppend videos to DOM

            showPopupSearch();
        });
}