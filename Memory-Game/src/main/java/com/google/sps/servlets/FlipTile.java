package com.google.sps.servlets;

import java.io.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import org.json.simple.*;
import java.util.concurrent.TimeUnit;

import static com.googlecode.objectify.ObjectifyService.ofy;

import com.google.sps.data.*;
import static com.google.sps.util.Util.*;

import com.google.appengine.api.users.*;

@WebServlet("/flip-tile")
public class FlipTile extends HttpServlet{

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{

        JSONObject obj = getJsonObjectFromRequest(request);
        long inviteCode = (long) obj.get("inviteCode");
        int row = (int) obj.get("row");
        int col = (int) obj.get("col");

        Game game = ofy().load().type(Game.class).id(inviteCode).now();
        String email = UserServiceFactory.getUserService().getCurrentUser().getEmail();
        TeamMember teamMember = game.getTeamMemberFromUser(email);

        if((game.getNumberOfMoves() < 2) && ((game.getChance() == Chance.RED && teamMember.getTeamName().equals("Red") && game.getRedTeam().isAdmin(teamMember)) || (game.getChance() == Chance.BLUE && teamMember.getTeamName().equals("Blue") && game.getBlueTeam().isAdmin(teamMember)))){
            game.move(row, col);

            if(game.getNumberOfMoves() == 2){
                if(game.checkForSameTiles()){
                    game.incrementScore();
                    game.clearMoves();
                    game.flipChance();
                }else{
                    ofy().save().entity(game).now();
                    try{
                        TimeUnit.SECONDS.sleep(5);
                    }catch(InterruptedException e){
                        System.err.format("IOException: %s%n", e);
                    }
                    game.undoAllMoves();
                    game.flipChance();
                }
            }
            ofy().save().entity(game).now();
        }
    }
}