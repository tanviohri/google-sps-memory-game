window.customElements.define('speech-bubble', class extends HTMLElement {'p'});

async function getMessages() {
    if(sessionStorage.getItem("gameOver")=="true") return;
    var obj = {"inviteCode": sessionStorage.getItem("inviteCode")};

    var response = await fetch('/polling-chat', {
        method: 'POST',
        body: JSON.stringify(obj)
    });

    const jsonObj = await response.json();

    teamMessages = jsonObj["teamChat"];
    teamContainer=document.getElementById('team-chat-container');
    teamMessages.map(function(currMessage){
        teamContainer.appendChild(
            createElement(currMessage)
        );
    });

    roomMessages = jsonObj["groupChat"];
    roomContainer=document.getElementById('room-chat-container');
    roomMessages.map(function(currMessage){
        roomContainer.appendChild(
            createElement(currMessage)
        );
    });
}

function createElement(currMessage) {
  const element = document.createElement('speech-bubble');
  element.innerHTML = "<b>" + currMessage.teamMember.nickname + "</b> <br>" + currMessage.text;

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