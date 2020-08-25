package com.google.sps.servlets;

import java.io.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import org.json.simple.*;
import org.json.simple.parser.*;
import com.google.gson.Gson;

import static com.googlecode.objectify.ObjectifyService.ofy;

import com.google.sps.data.Game;
import static com.google.sps.util.Util.*;

import com.google.appengine.api.users.*;

@WebServlet("/polling-game")
public class PollingGame extends HttpServlet{

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{

        JSONObject obj = getJsonObjectFromRequest(request);
        long inviteCode = Long.parseLong((String)obj.get("inviteCode"));
        Game game = ofy().load().type(Game.class).id(inviteCode).now();

        Gson gson = new Gson();

        obj = new JSONObject();
        obj.put("currentBoard", gson.toJson(game.getCurrentBoard()));
        obj.put("redTeamScore", game.getRedTeam().getScore());
        obj.put("blueTeamScore", game.getBlueTeam().getScore());
        obj.put("chance", game.getChanceAsString());

        StringWriter out = new StringWriter();
        obj.writeJSONString(out);

        response.setContentType("application/json");
        response.getWriter().println(out.toString());
    }
}