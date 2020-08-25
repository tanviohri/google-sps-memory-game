async function getCode() {    
    const response = await fetch('/create-room', {
            method: 'GET'
    });
    const game = await response.json();
    document.getElementById("code").innerHTML = game["inviteCode"];
    console.log(game);
}