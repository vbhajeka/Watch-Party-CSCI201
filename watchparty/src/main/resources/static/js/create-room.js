const createRoomBtn = document.querySelector("#create-room-btn");

createRoomBtn.addEventListener("click", async () => {

    console.log("running");

    let endpoint = window.location.origin + "/create_private_room"
    let response = await fetch(endpoint, {
        method: "POST",
        credentials: "same-origin",
        redirect: "follow"
    })

    let result = await response.text();

    window.location.href = response.url;
});


