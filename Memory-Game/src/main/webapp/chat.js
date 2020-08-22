window.customElements.define('speech-bubble', class extends HTMLElement {'p'});

async function getMessages() {
    const response = await fetch('/polling-chat'); 
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

function openForm(chatType) {
    document.getElementById(chatType + "Chat").style.display = "block";
}

function closeForm(chatType) {
    document.getElementById(chatType + "Chat").style.display = "none";
}

//var myVar = setInterval(getMessages, 1000);