"use strict";
//Execute upon page load
document.addEventListener('DOMContentLoaded', main);

let socket;
let stompClient;

function main() {

    //Make a connection with the server websocket at specified endpoint
    socket = new SockJS('/ws');

    //Instantiate STOMP client which allows connection to STOMP message brokers
    stompClient = Stomp.over(socket)

    //Connect the stomp client
    stompClient.connect({}, connect_callback, error_callback);

}

//Make subscriptions to sync player and chat topics for the specific room
function connect_callback() {



    stompClient.subscribe(`/syncplayer/${get_room_id()}`, on_sync_event);
    stompClient.subscribe(`/chat/${get_room_id()}`, on_message);

    // // Tell your username to the server
    // stompClient.send("/app/chat.addUser",
    //     {},
    //     JSON.stringify({sender: username, type: 'JOIN'})
    // )

}


function error_callback() {
    console.log("Stomp connection error");
}

//Respond to the player sync event
function on_sync_event(payload) {
    let syncEvent = JSON.parse(payload.body);

    if(syncEvent.eventType === "PAUSE") {
        console.log("Received pause");
        player.pauseVideo();
    }
    else if(syncEvent.eventType === "PLAY") {
        console.log("Received play");
        player.playVideo();
    }
    else {
        console.log("Received scrub");
        player.seekTo(syncEvent.timeStamp);
    }
}

function on_message(payload) {

    let chatMessage = JSON.parse(payload.body);
    console.log(chatMessage);

    let chatHtml = `
    <div class="message-person"></div>
    <div class="message-text">${chatMessage.content}</div>
    <div class="message-time"></div>
    `;

    let chatMessageContainer = document.createElement("div");
    chatMessageContainer.setAttribute("class", "other-message");
    chatMessageContainer.innerHTML += chatHtml;



    document.querySelector("#chat-message-list").prepend(chatMessageContainer);

    console.log("here");
}

function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);

    var messageElement = document.createElement('li');

    if(message.type === 'JOIN') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' joined!';
    } else if (message.type === 'LEAVE') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' left!';
    } else {
        messageElement.classList.add('chat-message');

        var avatarElement = document.createElement('i');
        var avatarText = document.createTextNode(message.sender[0]);
        avatarElement.appendChild(avatarText);
        avatarElement.style['background-color'] = getAvatarColor(message.sender);

        messageElement.appendChild(avatarElement);

        var usernameElement = document.createElement('span');
        var usernameText = document.createTextNode(message.sender);
        usernameElement.appendChild(usernameText);
        messageElement.appendChild(usernameElement);
    }

    var textElement = document.createElement('p');
    var messageText = document.createTextNode(message.content);
    textElement.appendChild(messageText);

    messageElement.appendChild(textElement);

    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
}

function sendMessage(event) {
    var messageContent = messageInput.value.trim();
    if(messageContent && stompClient) {
        var chatMessage = {
            sender: username,
            content: messageInput.value,
            type: 'CHAT'
        };
        stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
        messageInput.value = '';
    }
    event.preventDefault();
}

