package com.google.sps.servlets;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import static com.googlecode.objectify.ObjectifyService.ofy;

import com.google.gson.Gson;
import com.google.sps.data.Game;

@WebServlet("create-room")
public class CreateRoom extends HttpServlet{

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{

        Game game = new Game();

        ofy().save().entity(game).now();

        long inviteCode = game.getId();

        Gson gson = new Gson();
        response.setContentType("application/json");
        response.getWriter().println(gson.toJson(inviteCode));

    }

}