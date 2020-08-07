/* popupcreate*/
function showPopupCreate(){
	var popc=document.getElementById("pop-up-box-container-create");
	var pop=document.getElementById("pop-up-box-create");
	popc.style.display="block";
	pop.style.display="block";

	var winWidth=window.innerWidth;
			
	pop.style.left=(winWidth/2)-150+"px";
	pop.style.top="300px";
}

function popupOkCreate(){
	popupHideCreate();
}

function popupCancelCreate(){
	popupHideCreate();
}

function popupHideCreate(){
	var popc=document.getElementById("pop-up-box-container-create");
	var pop=document.getElementById("pop-up-box-create");
	popc.style.display="none";
	pop.style.display="none";
}

/* popupjoin*/
function showPopupJoin(){
	var popc=document.getElementById("pop-up-box-container-join");
	var pop=document.getElementById("pop-up-box-join");
	popc.style.display="block";
	pop.style.display="block";

	var winWidth=window.innerWidth;
			
	pop.style.left=(winWidth/2)-150+"px";
	pop.style.top="300px";
}

function popupOkJoin(){
	popupHideJoin();
}

function popupCancelJoin(){
	popupHideJoin();
}

function popupHideJoin(){
	var popc=document.getElementById("pop-up-box-container-join");
	var pop=document.getElementById("pop-up-box-join");
	popc.style.display="none";
	pop.style.display="none";
}

/*popupleave*/
function showPopupLeave(){
	var popc=document.getElementById("pop-up-box-container-leave");
	var pop=document.getElementById("pop-up-box-leave");
	popc.style.display="block";
	pop.style.display="block";

	var winWidth=window.innerWidth;
			
	pop.style.left=(winWidth/2)-150+"px";
	pop.style.top="300px";
}

function popupOkLeave(){
	popupHideLeave();
}

function popupCancelLeave(){
	popupHideLeave();
}

function popupHideLeave(){
	var popc=document.getElementById("pop-up-box-container-leave");
	var pop=document.getElementById("pop-up-box-leave");
	popc.style.display="none";
	pop.style.display="none";
}

/*popuplogout*/
function showPopupLogout(){
	var popc=document.getElementById("pop-up-box-container-logout");
	var pop=document.getElementById("pop-up-box-logout");
	popc.style.display="block";
	pop.style.display="block";

	var winWidth=window.innerWidth;
			
	pop.style.left=(winWidth/2)-150+"px";
	pop.style.top="300px";
}

function popupOkLogout(){
	popupHideLogout();
}

function popupCancelLogout(){
	popupHideLogout();
}

function popupHideLogout(){
	var popc=document.getElementById("pop-up-box-container-logout");
	var pop=document.getElementById("pop-up-box-logout");
	popc.style.display="none";
	pop.style.display="none";
}



/*popupsearch*/
function showPopupSearch(){
	var popc=document.getElementById("pop-up-box-container-search");
	var pop=document.getElementById("pop-up-box-search");
	popc.style.display="block";
	pop.style.display="block";

	var winWidth=window.innerWidth;
			
	pop.style.left=(winWidth/2)-250+"px";
	pop.style.top="150px";
}

function popupCancelSearch(){

	popupHideSearch();

	//Clear youtube video results
	let youtubeVids = document.querySelectorAll(".youtube-result-container");
	for (let video of youtubeVids) {
		video.remove();
	}
}

function popupCancelVote(){

	popupHideVote();
}

function popupHideSearch(){
	var popc=document.getElementById("pop-up-box-container-search");
	var pop=document.getElementById("pop-up-box-search");
	popc.style.display="none";
	pop.style.display="none";
}

function popupHideVote(){
	var popc=document.getElementById("pop-up-box-container-vote");
	var pop=document.getElementById("pop-up-box-vote");
	popc.style.display="none";
	pop.style.display="none";
}

