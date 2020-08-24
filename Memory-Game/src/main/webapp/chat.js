window.customElements.define('speech-bubble', class extends HTMLElement {'p'});

async function getMessages() {
    if(sessionStorage.getItem("gameOver")=="true") return;
    var obj = {"inviteCode": sessionStorage.getItem("inviteCode")};

    var response = await fetch('/polling-chat', {
        method: 'POST',
        body: JSON.stringify(obj)
    });

    const jsonObj = await response.json();

    teamMessages = JSON.parse(jsonObj["teamChat"]);
    document.getElementById('team-chat-container').innerHTML = ""; 
    teamContainer=document.getElementById('team-chat-container');
    teamMessages.forEach((message) => {
        teamContainer.appendChild(createElement(message));
    });

    roomMessages = JSON.parse(jsonObj["groupChat"]);
    document.getElementById('room-chat-container').innerHTML = ""; 
    roomContainer=document.getElementById('room-chat-container');
    roomMessages.forEach((message) => {
        console.log(message);
        roomContainer.appendChild(createElement(message));
    }); 
}

function createElement(currMessage) {
  const element = document.createElement('speech-bubble');
  element.innerHTML = "<br><br> <b>" + currMessage.teamMember.nickname + ": </b>" + currMessage.text;

  return element;
}

async function sendRoomChatToServer()
{
    var obj = {"inviteCode": sessionStorage.getItem("inviteCode"), 
    "message": document.getElementById("roomChatForm").elements["roomMsg"].value};

    var response = await fetch('/group-chat', {
        method: 'POST',
        body: JSON.stringify(obj)
    });
}

async function sendTeamChatToServer()
{
    var obj = {"inviteCode": sessionStorage.getItem("inviteCode"), 
    "message": document.getElementById("teamChatForm").elements["teamMsg"].value};

    var response = await fetch('/team-chat', {
        method: 'POST',
        body: JSON.stringify(obj)
    });
}

function openForm(chatType) {
    document.getElementById(chatType + "Chat").style.display = "block";
}

function closeForm(chatType) {
    document.getElementById(chatType + "Chat").style.display = "none";
}

var myVar = setInterval(getMessages, 1000);