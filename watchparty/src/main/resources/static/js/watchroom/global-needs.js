let player;
let syncDb;
//Returns the unique identifier of the room from endpoint
function get_room_id() {
    let url = window.location.href.split("/");
    let roomId = url[url.length-1];
    return roomId;
}


