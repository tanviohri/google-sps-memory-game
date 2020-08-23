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
    console.log(game);

    sessionStorage.setItem("board", game["board"]);
    window.location = "gameScreen.html"; 
}

window.storeInfo = storeInfo;