package com.google.sps.servlets;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.google.gson.Gson;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import java.io.PrintWriter;


@WebServlet("/login")
public class Login extends HttpServlet{

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        UserService userService = UserServiceFactory.getUserService();
    
        if (!userService.isUserLoggedIn()) {
        
            String urlToRedirectToAfterUserLogsIn = "/";
            String loginUrl = userService.createLoginURL(urlToRedirectToAfterUserLogsIn);

            out.println("<p>Login <a href=\"" + loginUrl + "\">here</a> before you proceed further...</p>");
        }

    }
}