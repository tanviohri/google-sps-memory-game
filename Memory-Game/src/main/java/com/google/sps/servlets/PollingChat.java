package com.google.sps.servlets;

import java.io.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import org.json.simple.*;
import org.json.simple.parser.*;
import com.google.gson.Gson;

import static com.googlecode.objectify.ObjectifyService.ofy;

import com.google.sps.data.*;
import static com.google.sps.util.Util.*;

import com.google.appengine.api.users.*;

@WebServlet("/polling-chat")
public class PollingChat extends HttpServlet{

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{

        JSONObject obj = getJsonObjectFromRequest(request);
        long inviteCode = Long.parseLong((String)obj.get("inviteCode"));

        Gson gson = new Gson();

        Game game = ofy().load().type(Game.class).id(inviteCode).now();
        String email = UserServiceFactory.getUserService().getCurrentUser().getEmail();
        TeamMember teamMember = game.getTeamMemberFromUser(email);

        obj = new JSONObject();
        obj.put("teamChat", gson.toJson(game.getAllTeamMessages(teamMember)));
        obj.put("groupChat", gson.toJson(game.getAllMessages()));

        StringWriter out = new StringWriter();
        obj.writeJSONString(out);

        response.setContentType("application/json");
        response.getWriter().println(out.toString());
        System.out.println("Request sent: " + out.toString());
    }

}