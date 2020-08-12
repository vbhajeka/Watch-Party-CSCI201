

const chatMessageField = document.querySelector("#chat-message-field");
const chatSendBtn = document.querySelector("#chat-send-btn");


chatSendBtn.addEventListener("click", () => {

    //Get the input field
    let messageText = chatMessageField.value;

    //Create the chatMessage object
    var chatMessage = {
        sender: "a person",
        content: "fuck",
        type: 'CHAT'
    };

    //Send to chatMessage to the stomp endpoint
    stompClient.send(`/app/room/${get_room_id()}/sendmessage`,
        {},
        JSON.stringify(chatMessage)
    );
});
