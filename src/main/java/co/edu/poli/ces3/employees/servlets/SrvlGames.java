package co.edu.poli.ces3.employees.servlets;

import co.edu.poli.ces3.employees.entities.Employee;
import co.edu.poli.ces3.employees.entities.Game;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

@WebServlet(name = "SrvlGames", value = "/SrvlGames")
public class SrvlGames extends HttpServlet {

    public static ArrayList<Game> GAMES = new ArrayList<>(Arrays.asList(
            new Game("101", "https://i.imgur.com/GKWg57q.png", "GTA V",
            "Action-adventure game developed by Rockstar North and published by Rockstar Games",
                "Rockstar Games", "5"),
            new Game("102", "https://i.imgur.com/aIVD1E9.png", "God of War",
                    "Action-adventure game franchise created by David Jaffe at Sony's Santa Monica Studio",
                    "Sony's Santa Monica Studio", "4.5")
    ));

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletOutputStream out = response.getOutputStream();
        response.setContentType("application/json");
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        out.flush();
        if(request.getParameter("gameId") == null){
            out.print(gson.toJson(this.GAMES));
        }else{
            Game game = this.searchGame(request.getParameter("gameId"));
            out.print(gson.toJson(game));
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        GsonBuilder gsonBuilder = new GsonBuilder();
        ServletOutputStream out = response.getOutputStream();
        Gson gson = gsonBuilder.create();
        response.setContentType("application/json");

        JsonObject body = JsonParser.parseString(this.getParamsFromPost(request)).getAsJsonObject();

        int min = 103, max =1000;
        Random rd = new Random();

        Game game = new Game(
                String.valueOf(rd.nextInt(max - min) + min),
                body.get("image").getAsString(),
                body.get("name").getAsString(),
                body.get("description").getAsString(),
                body.get("author").getAsString(),
                body.get("calification").getAsString()
        );

        this.GAMES.add(game);
        out.print(gson.toJson(game));
        out.flush();
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        GsonBuilder gsonBuilder = new GsonBuilder();
        response.setContentType("application/json");
        ServletOutputStream out = response.getOutputStream();
        Gson gson = gsonBuilder.create();

        out.flush();
        if(request.getParameter("gameId") == null){
            out.print(gson.toJson(this.GAMES));
        }else{
            deleteGame(request.getParameter("gameId"));
            out.print(gson.toJson(this.GAMES));
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        GsonBuilder gsonBuilder = new GsonBuilder();
        ServletOutputStream out = response.getOutputStream();
        Gson gson = gsonBuilder.create();
        response.setContentType("application/json");
        JsonObject body = JsonParser.parseString(this.getParamsFromPost(request)).getAsJsonObject();

        out.flush();
        if(request.getParameter("gameId") == null){
            out.print(gson.toJson(this.GAMES));
        }else{
            updateGame(request.getParameter("gameId"), body);
            Game game = this.searchGame(request.getParameter("gameId"));
            out.print(gson.toJson(game));
        }
    }
    private void updateGame(String gameId, JsonObject body) {
        for (Game x: this.GAMES) {
            if(x.getId().equals(gameId)){
                x.setName(body.get("name").getAsString());
                x.setImage(body.get("image").getAsString());
                x.setDescription(body.get("description").getAsString());
                x.setAuthor(body.get("author").getAsString());
                x.setCalification(body.get("calification").getAsString());
                break;
            }
        }
    }

    private Game searchGame(String gameId) {
        for (Game x: this.GAMES) {
            if(x.getId().equals(gameId)){
                return x;
            }
        }
        return null;
    }


    private void deleteGame(String gameId) {
        for (int i = 0; i < this.GAMES.size(); i++) {
            if(this.GAMES.get(i).getId().equals(gameId)){
                this.GAMES.remove(i);
                break;
            }
        }
    }
    private String getParamsFromPost(HttpServletRequest request) throws IOException {
        BufferedReader reader = request.getReader();
        StringBuilder sb = new StringBuilder();
        String line = reader.readLine();
        while (line != null) {
            sb.append(line + "\n");
            line = reader.readLine();
        }
        reader.close();
        String params = sb.toString();

        return params;
    }

}
