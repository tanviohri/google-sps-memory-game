async function storeInfo() {

    sessionStorage.setItem("inviteCode", document.getElementById("infoForm").elements["inviteCode"].value);
    sessionStorage.setItem("nickName", document.getElementById("infoForm").elements["nickName"].value);
    
    var obj = {"inviteCode": sessionStorage.getItem("inviteCode"), "nickName": sessionStorage.getItem("nickName")};
    
    var response = await fetch('/join-room', {
        method: 'POST',
        body: JSON.stringify(obj)
    });

    obj = {"inviteCode": sessionStorage.getItem("inviteCode")};
    
    response = await fetch('/init-game', {
        method: 'POST',
        body: JSON.stringify(obj)
    });
    const game = await response.json();

    sessionStorage.setItem("board", game["board"]);
    sessionStorage.setItem("yourTeam", game["yourTeam"]);
    sessionStorage.setItem("chance", game["chance"]);
    sessionStorage.setItem("gameOver", "false");

    window.location = "gameScreen.html"; 
}