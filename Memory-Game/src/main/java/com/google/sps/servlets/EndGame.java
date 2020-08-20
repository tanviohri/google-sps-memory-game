package com.google.sps.servlets;

import java.io.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import org.json.simple.*;

import static com.googlecode.objectify.ObjectifyService.ofy;

import com.google.sps.data.Game;

@WebServlet("end-game")
public class EndGame extends HttpServlet{

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{

        JSONObject obj = getJsonObjectFromRequest(request);
        long inviteCode = (long) obj.get("inviteCode");
        
        ofy().delete().entity(inviteCode);
    }
}