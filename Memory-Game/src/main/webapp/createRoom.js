async function getCode() {    
    const response = await fetch('/create-room', {
            method: 'GET'
    });
    const game = await response.text();
    document.getElementById("code").innerHTML = game;
}
