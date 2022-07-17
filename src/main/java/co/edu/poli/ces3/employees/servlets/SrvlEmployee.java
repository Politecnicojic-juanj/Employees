package co.edu.poli.ces3.employees.servlets;

import co.edu.poli.ces3.employees.entities.Employee;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;

@WebServlet(name = "SrvlEmployee", value = "/SrvlEmployee")
public class SrvlEmployee extends HttpServlet {

    public static ArrayList<Employee> EMPLOYEES = new ArrayList<>(Arrays.asList(
            new Employee("1111", "Carlos", "Perez", 80),
            new Employee("823773", "Diana", "Cardenas", 20),
            new Employee("58585485", "Juan", "Jose", 19),
            new Employee("4444", "Felipe", "Monsalve", 30)
    ));

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletOutputStream out = response.getOutputStream();
        response.setContentType("application/json");
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        out.flush();
        if(request.getParameter("employeeId") == null){
            out.print(gson.toJson(this.EMPLOYEES));
        }else{
            Employee empl = this.searchEmployee(request.getParameter("employeeId"));
            out.print(gson.toJson(empl));
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        GsonBuilder gsonBuilder = new GsonBuilder();
        ServletOutputStream out = response.getOutputStream();
        Gson gson = gsonBuilder.create();
        JsonObject body = JsonParser.parseString(this.getParamsFromPost(request)).getAsJsonObject();
        int min = 0, max=10000;
        Random rd = new Random();
        Employee employee = new Employee(
                String.valueOf(rd.nextInt(max - min) + min),
                body.get("name").getAsString(),
                body.get("lastName").getAsString(),
                body.get("age").getAsInt()
        );
        response.setContentType("application/json");
        this.EMPLOYEES.add(employee);
        out.print(gson.toJson(employee));
        out.flush();
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        GsonBuilder gsonBuilder = new GsonBuilder();
        ServletOutputStream out = response.getOutputStream();
        Gson gson = gsonBuilder.create();
        JsonObject body = JsonParser.parseString(this.getParamsFromPost(request)).getAsJsonObject();

        out.flush();
        if(request.getParameter("employeeId") == null){
            out.print(gson.toJson(this.EMPLOYEES));
        }else{
            updateEmployee(request.getParameter("employeeId"), body);
            Employee empl = this.searchEmployee(request.getParameter("employeeId"));
            out.print(gson.toJson(empl));
        }

    }
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        GsonBuilder gsonBuilder = new GsonBuilder();
        ServletOutputStream out = response.getOutputStream();
        Gson gson = gsonBuilder.create();
        JsonObject body = JsonParser.parseString(this.getParamsFromPost(request)).getAsJsonObject();

        out.flush();
        if(request.getParameter("employeeId") == null){
            out.print(gson.toJson(this.EMPLOYEES));
        }else{
            deleteEmployee(request.getParameter("employeeId"));
            out.print(gson.toJson(this.EMPLOYEES));
        }
    }


    //Metodo privado solo puede llamarse en esa clase
    private Employee searchEmployee(String employeeId) {
        for (Employee x: this.EMPLOYEES) {
            if(x.getId().equals(employeeId)){
                return x;
            }
        }
        return null;
    }

    private void updateEmployee(String employeeId, JsonObject body) {
        for (Employee x: this.EMPLOYEES) {
            if(x.getId().equals(employeeId)){
                x.setAge(body.get("age").getAsInt());
                x.setName(body.get("name").getAsString());
                x.setLastName(body.get("lastName").getAsString());
                break;
            }
        }
    }

    private void deleteEmployee(String employeeId) {
        for (int i = 0; i < this.EMPLOYEES.size(); i++) {
            if(this.EMPLOYEES.get(i).getId().equals(employeeId)){
                this.EMPLOYEES.remove(i);
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
