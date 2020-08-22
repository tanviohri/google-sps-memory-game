package com.google.sps.servlets;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import org.json.simple.*;
import org.json.simple.parser.*;

import static com.googlecode.objectify.ObjectifyService.ofy;

import com.google.gson.Gson;
import com.google.sps.data.Game;
import static com.google.sps.util.Util.*;

import com.google.appengine.api.users.*;

@WebServlet("/join-room")
public class JoinRoom extends HttpServlet{

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{

        JSONObject obj = getJsonObjectFromRequest(request);
        long inviteCode = (long) obj.get("inviteCode");
        String nickName = (String) obj.get("nickName");

        User user = UserServiceFactory.getUserService().getCurrentUser();

        Game game = ofy().load().type(Game.class).id(inviteCode).now();
        game.addUser(user, nickName);
        ofy().save().entity(game).now();
    }

}