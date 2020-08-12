
$(document).ready(function() {

    var animating = false;
    var cardsCounter = 0;
    var decisionVal = 80;
    var pullDeltaX = 0;
    var deg = 0;
    var $card, $cardReject, $cardLike;

    let alexRemovedCards = 0;

    function pullChange() {
        animating = true;
        deg = pullDeltaX / 10;
        $card.css("transform", "translateX("+ pullDeltaX +"px) rotate("+ deg +"deg)");

        var opacity = pullDeltaX / 100;
        var rejectOpacity = (opacity >= 0) ? 0 : Math.abs(opacity);
        var likeOpacity = (opacity <= 0) ? 0 : opacity;
        $cardReject.css("opacity", rejectOpacity);
        $cardLike.css("opacity", likeOpacity);
    };

    function release() {

        if (pullDeltaX >= decisionVal) {
            $card.addClass("to-right");

            //I THINK THIS IS VOTING FOR
            console.log("Voted for");

            //Add vote
            userVotes.push($card.attr("id"))


        } else if (pullDeltaX <= -decisionVal) {
            $card.addClass("to-left");

            //I THINK THIS IS VOTING AGAINST
            console.log("Voted against");

            //Do nothing
        }

        if (Math.abs(pullDeltaX) >= decisionVal) {
            $card.addClass("inactive");



            setTimeout(function() {
                $card.addClass("below").removeClass("inactive to-left to-right");
                cardsCounter++;
                if (cardsCounter === numOfCards) {
                    cardsCounter = 0;
                    $(".demo__card").removeClass("below");
                }
            }, 300);
        }

        if (Math.abs(pullDeltaX) < decisionVal) {
            $card.addClass("reset");
        }

        setTimeout(function() {
            $card.attr("style", "").removeClass("reset")
                .find(".demo__card__choice").attr("style", "");

            pullDeltaX = 0;
            animating = false;
        }, 300);


        //Remove the HTML for each card after swipe
        $card.remove();
        alexRemovedCards += 1;

        //User has finished voting (all cards have been removed)
        if(alexRemovedCards === numOfCards) {

            //Reset removed cards counter
            alexRemovedCards = 0;


            show_waiting_message();
            post_votes_to_server();


        }

    };

    $(document).on("mousedown touchstart", ".demo__card:not(.inactive)", function(e) {
        if (animating) return;

        $card = $(this);
        $cardReject = $(".demo__card__choice.m--reject", $card);
        $cardLike = $(".demo__card__choice.m--like", $card);
        var startX =  e.pageX || e.originalEvent.touches[0].pageX;

        $(document).on("mousemove touchmove", function(e) {
            var x = e.pageX || e.originalEvent.touches[0].pageX;
            pullDeltaX = (x - startX);
            if (!pullDeltaX) return;
            pullChange();
        });

        $(document).on("mouseup touchend", function() {
            $(document).off("mousemove touchmove mouseup touchend");
            if (!pullDeltaX) return; // prevents from rapid click events
            release();
        });
    });

});

//Runs after all cards have been chosen
//
async function show_waiting_message() {

    add_voting_message("Waiting for end of voting period");


    await sleep(20000);

    clear_voting_message();

    // let cardContainer = document.querySelector(".demo__content");

    // let winningVideoMessage = document.createElement("div");
    // winningVideoMessage.setAttribute("id", "winning-video-message");
    // winningVideoMessage.innerHTML += "<h1>A video title </h1>";

    // cardContainer.prepend(winningVideoMessage);
}

function add_voting_message(message) {

    let cardContainer = document.querySelector(".demo__content");

    let winningVideoMessage = document.createElement("div");
    winningVideoMessage.setAttribute("id", "winning-video-message");
    winningVideoMessage.innerHTML += `<h1>${message}</h1>`;
    cardContainer.prepend(winningVideoMessage);
}

function clear_voting_message() {
    //Remove the winning message
    let winningVidMessage = document.getElementById("winning-video-message");
    winningVidMessage.parentElement.removeChild(winningVidMessage);
}


//Sends userVotes to server after they have finished their voting
async function post_votes_to_server() {

    console.log("Posting votes server");


    let votesObject = {votes: userVotes}
    console.log(votesObject);

    let endpoint = window.location.href + "/submit_vote";
    let response = await fetch(endpoint,{
        method:"POST",
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(votesObject)
    });

    let result = response.text();

    console.log(result);
}
