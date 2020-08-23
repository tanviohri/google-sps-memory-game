//console.log('script reloaded');

var board = [],
inviteCode = "*",
nickName = "*";

export function getBoard()
{
    //console.log(board);
    return board;
}

async function storeInfo() {
    inviteCode = document.getElementById("infoForm").elements["inviteCode"].value;
    nickName = document.getElementById("infoForm").elements["nickName"].value;
    var obj = {"inviteCode": inviteCode, "nickName": nickName};
    const response = await fetch('/join-room', {
        method: 'POST',
        body: JSON.stringify(obj)
    });
    var obj = {"inviteCode": inviteCode};
    const response = await fetch('/init-game', {
        method: 'POST',
        body: JSON.stringify(obj)
    });
    const game = await response.json();
    console.log(game);

    board=JSON.parse(game["board"]);
    /*
    console.log(board);
    let n=board.length;
    let m=board[0].length;
    
    for (let i = 0; i < n; i++) {
        for (let j = 0; j < m; j++) {
            console.log(board[i][j]);    
        }
    }    */
    window.location = "gameScreen.html"; 
}

window.storeInfo = storeInfo;
