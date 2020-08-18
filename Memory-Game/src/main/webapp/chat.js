window.customElements.define('speech-bubble', class extends HTMLElement {'p'});

async function getMessagesForTeamChat() {
    //const response = await fetch('/data'); --to implement
    //const messages = await response.json(); --to implement
    container=document.getElementById('team-chat-container');
    messages.map(function(currMessage){
        container.appendChild(
            createElement(currMessage)
        );
    });
}

async function getMessagesForRoomChat() {
    //const response = await fetch('/data'); --to implement
    //const messages = await response.json(); --to implement
    container=document.getElementById('room-chat-container');
    messages.map(function(currMessage){
        container.appendChild(
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