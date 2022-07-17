package co.edu.poli.ces3.employees.servlets;

import co.edu.poli.ces3.employees.entities.Games;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

@WebServlet(name = "SrvlGames", value = "/SrvlGames")
public class SrvlGames extends HttpServlet {

    public static ArrayList<Games> GAMES = new ArrayList<>(Arrays.asList(
            new Games("101", "https://i.imgur.com/GKWg57q.png", "GTA V",
            "action-adventure game developed by Rockstar North and published by Rockstar Games",
                "Rockstar Games", "5")
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
            Games game = this.searchGame(request.getParameter("gameId"));
            out.print(gson.toJson(game));
        }

    }

    private Games searchGame(String gameId) {
        for (Games x: this.GAMES) {
            if(x.getId().equals(gameId)){
                return x;
            }
        }
        return null;
    }

}
