"use strict";
//Execute upon page load
document.addEventListener('DOMContentLoaded', main);

//Player object is declared globally

function main() {

    setup_firebase();

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
        stompClient.send(`/app/room/${get_room_id()}/syncevent`,
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
        stompClient.send(`/app/room/${get_room_id()}/syncevent`,
            {},
            JSON.stringify(data)
        );


    });

    emit_sync_time();


}

//Gives current time of video every 100 milliseconds
function emit_sync_time() {

    // setInterval(() => {
    //
    //     //Only run  if player has been loaded
    //     if(player !== null) {
    //
    //         //Update firestore every 500 ms
    //         syncDb.collection("rooms").doc(get_room_id()).set({
    //             syncedTime: player.getCurrentTime()
    //         })
    //             .then(() => {
    //                 console.log("Document successfully written!");
    //             })
    //             .catch(() => {
    //                 console.error("Error writing document: ", error);
    //             });
    //     }
    //
    // }, 500);
}

function run_progress_bar() {

    //Register click handler for the progress bar
    let progressBar = document.querySelector("#progressBar");
    let barLength = 800;
    progressBar.addEventListener("click", (event) => {
        let offSet = event.offsetX;
        let videoFraction = offSet/barLength;

        //Seek to the selected fraction of the video
        let videoSecond = videoFraction * player.getDuration();
        player.seekTo(videoSecond);


        //Move the scrubber
        let percent = videoFraction * 100;
        scrubber.style.left = percent + "%";
    });


    let scrubber = document.querySelector("#scrubber");
    setInterval(() => {
        
        if (player!== null) {
            let percentPlayed = 100*player.getCurrentTime()/player.getDuration();
            scrubber.style.left = percentPlayed + "%";
        }


    }, 200);
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
        playerVars: {'controls': 1},
        events: {
            'onReady': onPlayerReady,
            'onStateChange': onPlayerStateChange
        }
    });
}

// 4. The API will call this function when the video player is ready.
function onPlayerReady(event) {

    get_syncTime()
        .then((time) => {
            console.log("Got the sync time", time);
            event.target.playVideo();
            event.target.seekTo(time);

            run_progress_bar();
        });

}

// 5. The API calls this function when the player's state changes.
//    The function indicates that when playing a video (state=1),
//    the player should play for six seconds and then stop.

function onPlayerStateChange(event) {

    //Sync player based on default player pause/play
    if(event.data === 1) {
        //Send event via STOMP

        //Create the event
        let data = {
            'eventType': 'PLAY',
            'timeStamp': player.getCurrentTime()
        }

        //Send to the message endpoint
        stompClient.send(`/app/room/${get_room_id()}/syncevent`,
            {},
            JSON.stringify(data)
        );
    }
    else if(event.data === 2) {
        //Send event via STOMP

        //Create the event
        let data = {
            'eventType': 'PAUSE',
            'timeStamp': player.getCurrentTime()
        }

        //Send to the message endpoint
        stompClient.send(`/app/room/${get_room_id()}/syncevent`,
            {},
            JSON.stringify(data)
        );
    }
    else {
        console.log(event.data);
    }
}


//-----Firestore Functions---------
//---------------------------------
function setup_firebase() {
    // Your web app's Firebase configuration
    var firebaseConfig = {
        apiKey: "AIzaSyB_owDWLO74lnkrCTLuNtpUmSNhLovGoxA",
        authDomain: "watchparty-1d5e1.firebaseapp.com",
        databaseURL: "https://watchparty-1d5e1.firebaseio.com",
        projectId: "watchparty-1d5e1",
        storageBucket: "watchparty-1d5e1.appspot.com",
        messagingSenderId: "128881912298",
        appId: "1:128881912298:web:dea8fe7b09b99dcbc2aa93"
    };
    // Initialize Firebase
    firebase.initializeApp(firebaseConfig);

    //Create firestore database object
    syncDb = firebase.firestore();
    console.log("Created firestore db", syncDb);
}

//Function to get the synchronized time of an open room
//or return 0 if the room is brand new
async function get_syncTime() {
    let roomRef = syncDb.collection("rooms").doc(get_room_id());
    let room = await roomRef.get();

    if(room.exists) {
        console.log("Room exists");
        let syncTime = room.data().syncedTime;
        return syncTime.toFixed(0);
    }
    else {
        console.log("Room does not exist");
        return 0;
    }
}
