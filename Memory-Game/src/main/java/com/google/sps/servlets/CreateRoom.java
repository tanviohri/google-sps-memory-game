package com.google.sps.servlets;

import java.io.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import org.json.simple.*;

import static com.googlecode.objectify.ObjectifyService.ofy;

import com.google.sps.data.Game;

@WebServlet("/create-room")
public class CreateRoom extends HttpServlet{

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{

        Game game = new Game();

        ofy().save().entity(game).now();

        assert game.getId() != null;

        JSONObject obj = new JSONObject();
        obj.put("inviteCode", game.getId());

        StringWriter out = new StringWriter();
        obj.writeJSONString(out);

        response.setContentType("application/json");
        response.getWriter().println(out.toString());
    }
}