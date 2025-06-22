package com.example.acpt.demoee.Controller;

import com.example.acpt.demoee.dto.DoctorDto;
import com.example.acpt.demoee.service.DoctorService;
import com.example.acpt.demoee.service.impl.DoctorServiceIMPL;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(urlPatterns = {"/doctor"})
public class DoctorServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        DoctorService service = new DoctorServiceIMPL();
        List<DoctorDto> alldoctors = service.getAllDoctors();

        // Convert to JSON
        Gson gson = new Gson();
        String json = gson.toJson(alldoctors);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        //write the JSON response
        PrintWriter out  = resp.getWriter();
        out.print(json);
        out.flush();

    }



    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)  {

    }


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)  {

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)  {

    }
}