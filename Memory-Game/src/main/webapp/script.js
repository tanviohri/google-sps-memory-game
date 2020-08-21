// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

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

