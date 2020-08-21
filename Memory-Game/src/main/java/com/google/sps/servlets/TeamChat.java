package com.google.sps.servlets;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.lang.*;
import org.json.simple.*;

import static com.googlecode.objectify.ObjectifyService.ofy;

import com.google.sps.data.*;
import static com.google.sps.util.Util.*;

import com.google.appengine.api.users.*;

@WebServlet("/team-chat")
public class TeamChat extends HttpServlet{

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{

        JSONObject obj = getJsonObjectFromRequest(request);
        long inviteCode = (long) obj.get("inviteCode");
        String text = (String) obj.get("message");

        Game game = ofy().load().type(Game.class).id(inviteCode).now();
        User user = UserServiceFactory.getUserService().getCurrentUser();
        TeamMember teamMember = game.getTeamMemberFromUser(user);

        game.addMessageInTeamChat(new Message(teamMember, System.currentTimeMillis(), text));
        ofy().save().entity(game).now();
    }

}