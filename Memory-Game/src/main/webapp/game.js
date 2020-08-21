let objects = ['flag', 'glass', 'star', 'coffee', 'circle', 'cloud', 'bug', 'bicycle', 'leaf',
    'cube', 'anchor', 'paper-plane-o', 'bolt', 'bomb', 'diamond'],

    $deck = $('.deck');

async function init() {
    const response = await fetch('/init-game', {
        method: 'POST'
    });
    const game = await response.json();

    let board=game["board"];

    n=board.length();
    m=board[0].length();

    $deck.empty();

    //matchRed = 0;
    //matchBlue=0;
    //turn="Red";
    //$scoreRed.html(matchRed);
    //$scoreBlue.html(matchBlue);
    //$turn.html(turn);

    for (let i = 1; i <= n; i++) {
        for (let j = 1; j <= m; j++) {
            $deck.append($(`<li class="card" data-row=${i} data-col=${j}><i class="fa fa-${objects[board[i][j]]}"></i></li>`))
        }
    }
    addCardListener();
}

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
init();

