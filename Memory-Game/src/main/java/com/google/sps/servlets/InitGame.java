package com.google.sps.servlets;

import java.io.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import org.json.simple.*;
import org.json.simple.parser.*;
import java.util.*; 
import com.google.gson.Gson;

import static com.googlecode.objectify.ObjectifyService.ofy;

import com.google.sps.data.*;
import static com.google.sps.util.Util.*;

import com.google.appengine.api.users.*;

@WebServlet("/init-game")
public class InitGame extends HttpServlet{

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{

        JSONObject obj = getJsonObjectFromRequest(request);
        long inviteCode = Long.parseLong((String) obj.get("inviteCode"));
        Game game = ofy().load().type(Game.class).id(inviteCode).now();

        String email = UserServiceFactory.getUserService().getCurrentUser().getEmail();
        TeamMember teamMember = game.getTeamMemberFromUser(email);

        Gson gson = new Gson();

        obj = new JSONObject();
        obj.put("board", gson.toJson(game.getBoard()));
        obj.put("yourTeam", teamMember.getTeamName());
        obj.put("chance", game.getChanceAsString());

        StringWriter out = new StringWriter();
        obj.writeJSONString(out);

        response.setContentType("application/json");
        response.getWriter().println(out.toString());
    }
}