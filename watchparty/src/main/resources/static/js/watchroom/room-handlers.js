"use strict";
//Execute upon page load
document.addEventListener('DOMContentLoaded', main);

function main() {

    document.querySelector('#testing-button').addEventListener('click',
        sync);
}

function sync() {
    let data = {
        'eventType': 'PAUSE',
        'timeStamp': '1000'
    }
    stompClient.send('/app/room/2520/syncevent',
        {},
        JSON.stringify(data)
    );
}