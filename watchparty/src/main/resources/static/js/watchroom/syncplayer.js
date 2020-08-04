"use strict";
//Execute upon page load
document.addEventListener('DOMContentLoaded', main);

//Player object is declared globally

function main() {

    load_yt_api();

    //Register pause/play handlers
    document.querySelector("#playButton").addEventListener('click', () => {

        if(player === undefined) {
            return
        }

        //Play the video
        player.playVideo();

        //Send event via STOMP

        //Create the event
        let data = {
            'eventType': 'PLAY',
            'timeStamp': player.getCurrentTime()
        }

        //Send to the message endpoint
        stompClient.send('/app/room/2520/syncevent',
            {},
            JSON.stringify(data)
        );


    });

    //Register pause/play handlers
    document.querySelector("#pauseButton").addEventListener('click', () => {
        if(player === undefined) {
            return
        }
        //Pause the video
        player.pauseVideo();

        //Send event via STOMP

        //Create the event
        let data = {
            'eventType': 'PAUSE',
            'timeStamp': player.getCurrentTime()
        }

        //Send to the message endpoint
        stompClient.send('/app/room/2520/syncevent',
            {},
            JSON.stringify(data)
        );


    });

    progress_bar_sync();



    document.querySelector('#testing-button').addEventListener('click',
        sync);
}

//Gives current time of video every 100 milliseconds
function progress_bar_sync() {
    let progressBar = document.querySelector("#progressBar");
    setInterval(() => {
        // console.log(player.getCurrentTime())
    }, 100);
}



//Load in IFrame Player API code asynchronously.
function load_yt_api() {
    let tag = document.createElement('script');
    tag.src = "https://www.youtube.com/iframe_api";
    let firstScriptTag = document.getElementsByTagName('script')[0];
    firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);
}



// 3. This function creates an <iframe> (and YouTube player)
//    after the API code downloads. Execute as soon as load_yt_api completes
function onYouTubeIframeAPIReady() {
    player = new YT.Player('player', {
        height: '600',
        width: '900',
        videoId: 'M7lc1UVf-VE',
        playerVars: {'controls': 0},
        events: {
            'onReady': onPlayerReady,
            'onStateChange': onPlayerStateChange
        }
    });
}

// 4. The API will call this function when the video player is ready.
function onPlayerReady(event) {
    event.target.playVideo();
}

// 5. The API calls this function when the player's state changes.
//    The function indicates that when playing a video (state=1),
//    the player should play for six seconds and then stop.

function onPlayerStateChange(event) {
    console.log("State change:", event.data);
}

