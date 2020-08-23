let objects = ['flag', 'glass', 'star', 'coffee', 'circle', 'cloud', 'bug', 'bicycle', 'leaf',
    'cube', 'anchor', 'paper-plane-o', 'bolt', 'bomb', 'diamond'];
    
var inviteCode = "*",
nickName = "*",
$deck = $('.deck');

function init() {
    let board = JSON.parse(sessionStorage.getItem("board"));
    console.log(board);
    $deck.empty();
    let n=board.length;
    let m=board[0].length;

    //matchRed = 0;
    //matchBlue=0;
    //turn="Red";
    //$scoreRed.html(matchRed);
    //$scoreBlue.html(matchBlue);
    //$turn.html(turn);
    
    for (let i = 0; i < n; i++) {
        for (let j = 0; j < m; j++) {
            console.log(board[i][j]);    
        }
    }    
    for (let i = 0; i < n; i++) {
        for (let j = 0; j < m; j++) {
            $deck.append($(`<li class="card" data-row=${i} data-col=${j}><i class="fa fa-${objects[board[i][j]]}"></i></li>`))
        }
    }
    addCardListener();
}

window.init = init;

let addCardListener = function() {
    $deck.find('.card').bind('click', function() {
        let $this = $(this);
        if ($this.hasClass('open')) { return true; }
        //send {$this.attr("data-row"),$this.attr("data-col")} to datastore
    });
}

function flipCard(row, col) {
    let card = $deck.find('[data-row=' + row + '][data-col=' + col + ']');
    if(card.hasClass('open')) {card.removeClass('open');}
    else card.addClass('open');
}

