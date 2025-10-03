package com.example.acpt.demoee.Controller;

import com.example.acpt.demoee.dto.DoctorDto;
import com.example.acpt.demoee.service.DoctorService;
import com.example.acpt.demoee.service.impl.DoctorServiceIMPL;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(urlPatterns = {"/doctor"})
public class DoctorServlet extends HttpServlet {
    
    private final DoctorService service = new DoctorServiceIMPL();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<DoctorDto> allDoctors = service.getAllDoctors();
        String json = gson.toJson(allDoctors);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().print(json);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            DoctorDto doctor = parseRequestBody(req);
            boolean saved = service.saveDoctor(doctor);
            sendResponse(resp, saved, saved ? "Doctor added successfully" : "Failed to add doctor");
        } catch (Exception e) {
            sendError(resp, "Invalid request: " + e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            DoctorDto doctor = parseRequestBody(req);
            boolean updated = service.updateDoctor(doctor);
            sendResponse(resp, updated, updated ? "Doctor updated successfully" : "Failed to update doctor");
        } catch (Exception e) {
            sendError(resp, "Invalid request: " + e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String idParam = req.getParameter("id");
            if (idParam == null) {
                sendError(resp, "Missing 'id' parameter");
                return;
            }

            int id = Integer.parseInt(idParam);
            boolean deleted = service.deleteDoctor(id);
            sendResponse(resp, deleted, deleted ? "Doctor deleted successfully" : "Doctor not found");
        } catch (NumberFormatException e) {
            sendError(resp, "Invalid doctor ID");
        }
    }

    // Utility Methods
    private DoctorDto parseRequestBody(HttpServletRequest req) throws IOException {
        BufferedReader reader = req.getReader();
        return gson.fromJson(reader, DoctorDto.class);
    }

    private void sendResponse(HttpServletResponse resp, boolean success, String message) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        out.print(gson.toJson(new ResponseMessage(success, message)));
        out.flush();
    }

    private void sendError(HttpServletResponse resp, String message) throws IOException {
        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        sendResponse(resp, false, message);
    }

    // Response Message DTO
    private static class ResponseMessage {
        boolean success;
        String message;

        ResponseMessage(boolean success, String message) {
            this.success = success;
            this.message = message;
        }
    }
}
