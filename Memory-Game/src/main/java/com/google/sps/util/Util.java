package com.google.sps.util;

import javax.servlet.http.*;
import java.io.*;
import org.json.simple.*;
import org.json.simple.parser.*;


public class Util{
    
    public static String getBodyFromRequest(HttpServletRequest request) throws IOException {

        String body = null;
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;

        try {
            InputStream inputStream = request.getInputStream();
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int bytesRead = -1;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            } else {
                stringBuilder.append("");
            }
        } catch (IOException ex) {
            throw ex;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    throw ex;
                }
            }
        }

        body = stringBuilder.toString();
        return body;
    }

    public static JSONObject getJsonObjectFromRequest(HttpServletRequest request) throws IOException{
        String data = getBodyFromRequest(request);
        JSONParser parser = new JSONParser();
        Object obj = null;

        try{
            obj = parser.parse(data);
        }catch(ParseException pe){
            System.out.println("position: " + pe.getPosition());
            System.out.println(pe);
        }

        return (JSONObject) obj;
    }
}