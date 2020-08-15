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

/**
 * Adds a random greeting to the page.
 */

 // All usefull objects and shortcuts to simplify coding
let objects = ['bug', 'bug', 'bicycle', 'bicycle', 'leaf', 'leaf', 'cube', 'cube', 'anchor', 'anchor', 'paper-plane-o', 'paper-plane-o', 'bolt', 'bolt', 'bomb', 'bomb', 'diamond', 'diamond'],

    // Useful selectors shortened
    $container = $('.container'),
    $scorePanel = $('.score-panel'),
    $scoreRed = $('.score-red'),
    $turn = $('.turn'),
    $scoreBlue = $('.score-blue'),
    $deck = $('.deck'),

    // Set variables to shorten code
    allOpen = [],
    matchRed = 0,
    matchBlue=0;
    wait = 420,
    totalCard = objects.length / 2;

// Shuffling function: enables that no two games have the same card arrangement 
function shuffle(array) {
    let currentIndex = array.length, temporaryValue, randomIndex;

    while (currentIndex !== 0) {
        randomIndex = Math.floor(Math.random() * currentIndex);
        currentIndex -= 1;
        temporaryValue = array[currentIndex];
        array[currentIndex] = array[randomIndex];
        array[randomIndex] = temporaryValue;
    }
    return array;
}

// The function init() enables the game to begin
function init() {

    // The shuffle function shuffles the objects array
    let allCards = shuffle(objects);
    $deck.empty();

    // The game starts with no matching cards
    matchRed = 0;
    matchBlue=0;
    turn="Red";
    $scoreRed.html(matchRed);
    $scoreBlue.html(matchBlue);
    $turn.html(turn);

    // A for loop creates <li> tags with the class of card for every <i> tag
    // A class of fa fa- and a name of each object from the objects=[] array
    for (let i = 0; i < allCards.length; i++) {
        $deck.append($('<li class="card" data-symbol='+ allCards[i] + '><i class="fa fa-' + allCards[i] + '"></i></li>'))
    }
    //$deck.find('[data-symbol="bug"]').addClass('match');
    addCardListener();
}

// Add boostrap modal alert window showing time, moves, score it took to finish the game, toggles when all pairs are matched.
function gameOver(team) {
    $('#winnerText').text(`${team} Team wins! Well done!`);
    $('#winnerModal').modal('toggle');
}

// This function allows each card to be validated that is an equal match to another card that is clicked on to stay open.
// If cards do not match, both cards are flipped back over.
let addCardListener = function () {

    // With the following, the card that is clicked on is flipped
    $deck.find('.card').bind('click', function () {
        let $this = $(this);
        if ($this.hasClass('open') || $this.hasClass('match')) { return true; }
        //let card = $this.context.innerHTML;
        let symbol=$this.attr("data-symbol");
        //$deck.find('[data-symbol='+symbol+']').addClass('match');
        $this.addClass('open');
        allOpen.push(symbol);

        // Compares cards if they matched
        if (allOpen.length > 1) {
            if (symbol === allOpen[0]) {
                $deck.find('.open').addClass('match');
                setTimeout(function () {
                    $deck.find('open').removeClass('open');
                }, wait);
                if(turn=="Red")
                {
                    matchRed++;                    
                    $scoreRed.html(matchRed);
                }
                else if(turn=="Blue") 
                {
                    matchBlue++;                    
                    $scoreBlue.html(matchBlue);
                }
                // If cards are not matched, there is a delay of 630ms, and the cards will turn back cover up.
            } else {
                setTimeout(function () {
                    $deck.find('.open').removeClass('open');
                }, wait / 1.5);
            }

            // The allOpen array specifies all added cards facing up
            allOpen = [];
            if(turn=="Red") turn="Blue";
            else if(turn=="Blue") turn="Red";
            $turn.html(turn);
        }

        // The game is finished once all cards have been matched, with a short delay
        if (matchRed > totalCard/2) {
            setTimeout(function () {
                gameOver("Red");
            }, 500);
        }
        else if(matchBlue > totalCard/2){
            setTimeout(function () {
                gameOver("Blue");
            }, 500);
        }
    });
}

init();
 
