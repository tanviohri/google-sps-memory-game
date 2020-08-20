package com.google.sps.servlets;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.google.gson.Gson;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@WebServlet("check-login")
public class CheckLogin extends HttpServlet{

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{

        boolean loggedIn = UserServiceFactory.getUserService().isUserLoggedIn();

        Gson gson = new Gson();
        response.setContentType("application/json");
        response.getWriter().println(gson.toJson(loggedIn));

    }
}