
document.addEventListener('DOMContentLoaded', main);

function main() {

    //Make a connection with the server websocket at specified endpoint
    const ws = new SockJS('/ws');

    //Instantiate STOMP client which allows connection to STOMP message brokers
    const stompClient = Stomp.over(ws)

    stompClient.connect({}, onConnected, onError);


}
