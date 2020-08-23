let objects = ['flag', 'glass', 'star', 'coffee', 'circle', 'cloud', 'bug', 'bicycle', 'leaf',
    'cube', 'anchor', 'paper-plane-o', 'bolt', 'bomb', 'diamond'],

$scoreRed = $('.score-red'),
$turn = $('.turn'),
$scoreBlue = $('.score-blue'),
$deck = $('.deck');
var n, m;

function init() {
    let board = JSON.parse(sessionStorage.getItem("board"));
    console.log(board);
    $deck.empty();
    n=board.length;
    m=board[0].length;

    document.getElementById("your-team").innerHTML = sessionStorage.getItem("yourTeam");
    $turn.html(sessionStorage.getItem("chance"));

    console.log("n: " + n + " m: " + m);

    /*for (let i = 0; i < n; i++) {
        for (let j = 0; j < m; j++) {
            console.log(board[i][j]);    
        }
    }  */

    for (let i = 0; i < n; i++) {
        for (let j = 0; j < m; j++) {
            $deck.append($(`<li class="card" data-row=${i} data-col=${j}><i class="fa fa-${objects[board[i][j]]}"></i></li>`))
        }
    }
    addCardListener();
    setInterval(updateBoard, 1000);
}

async function addCardListener () {
    $deck.find('.card').bind('click', async function() {
        let $this = $(this);
        if ($this.hasClass('open')) { return true; }
        console.log("row: " + $this.attr("data-row") + "  col: " + $this.attr("data-col"));

        var obj = {"inviteCode": sessionStorage.getItem("inviteCode"), "row": $this.attr("data-row"),
        "col" : $this.attr("data-col")};
        
        var response = await fetch('/flip-tile', {
            method: 'POST',
            body: JSON.stringify(obj)
        });
    });
}

async function updateBoard()
{
    var obj = {"inviteCode": sessionStorage.getItem("inviteCode")};

    var response = await fetch('/polling-game', {
        method: 'POST',
        body: JSON.stringify(obj)
    });

    const game = await response.json();
    
    sessionStorage.setItem("currentBoard", game["currentBoard"]);  
    let board = JSON.parse(sessionStorage.getItem("currentBoard"));

    for (let i = 0; i < n; i++) {
        for (let j = 0; j < m; j++) {
            let card = $deck.find('[data-row=' + i + '][data-col=' + j + ']');
            if(board[i][j]==false)
            {
                if(card.hasClass('open')) card.removeClass('open');
            }
            else
            {
                if(!card.hasClass('open')) card.addClass('open');
            }
        }
    }

    $scoreRed.html(game["redTeamScore"]);
    $scoreBlue.html(game["blueTeamScore"]);
    $turn.html(game["chance"]);
}