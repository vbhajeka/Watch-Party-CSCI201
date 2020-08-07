$(document).ready(function() {

    var animating = false;
    var cardsCounter = 0;
    var numOfCards = 5;
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
        } else if (pullDeltaX <= -decisionVal) {
            $card.addClass("to-left");
        }

        if (Math.abs(pullDeltaX) >= decisionVal) {
            $card.addClass("inactive");

            console.log("Chosen");


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

        //If all cards have been removed show the winner
        if(alexRemovedCards === numOfCards) {

            //Reset removed cards counter
            alexRemovedCards = 0;

            show_winner();

            //Delay close for 3 seconds
            setTimeout(() => {
                //Close the popup
                popupCancelVote()
                //Remove the winning message
                let winningVidMessage = document.getElementById("winning-video-message");
                winningVidMessage.parentElement.removeChild(winningVidMessage);
            }, 8000);


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
function show_winner() {
    let cardContainer = document.querySelector(".demo__content");

    let winningVideoMessage = document.createElement("div");
    winningVideoMessage.setAttribute("id", "winning-video-message");
    winningVideoMessage.innerHTML += "<h1>A video title </h1>";

    cardContainer.prepend(winningVideoMessage);
}