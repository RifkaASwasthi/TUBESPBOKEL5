package com.hospital.controller;

import com.google.gson.Gson;
import com.hospital.dao.PatientDAO;
import com.hospital.model.Patient;
import com.hospital.util.DBUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet("/api/patients")
public class PatientServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Connection conn;
    private PatientDAO dao;
    private final Gson gson = new Gson();

    @Override
    public void init() throws ServletException {
        conn = DBUtil.getConnection(); // Gunakan koneksi dari util DB sendiri
        dao = new PatientDAO(conn);
    }

    // GET /api/patients atau /api/patients?id=5
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");

        try {
            String idParam = req.getParameter("id");

            if (idParam != null) {
                int id = Integer.parseInt(idParam);
                Patient patient = dao.getById(id);
                resp.getWriter().write(gson.toJson(patient));
            } else {
                List<Patient> patients = dao.getAll();
                resp.getWriter().write(gson.toJson(patients));
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    // POST /api/patients (insert)
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        try (BufferedReader reader = req.getReader()) {
            Patient patient = gson.fromJson(reader, Patient.class);

            // Set waktu otomatis
            patient.setCreatedAt(LocalDateTime.now());
            patient.setUpdatedAt(LocalDateTime.now());
            patient.setRegistrationDate(LocalDate.now());

            boolean success = dao.insert(patient);

            resp.setStatus(success ? HttpServletResponse.SC_CREATED : HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"success\": " + success + "}");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    // PUT /api/patients?id=5 (update)
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            try (BufferedReader reader = req.getReader()) {
                Patient patient = gson.fromJson(reader, Patient.class);
                patient.setId(id);
                patient.setUpdatedAt(LocalDateTime.now());

                boolean success = dao.update(patient);

                resp.getWriter().write("{\"success\": " + success + "}");
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    // DELETE /api/patients?id=5
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            boolean success = dao.delete(id);

            resp.getWriter().write("{\"success\": " + success + "}");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    @Override
    public void destroy() {
        try {
            conn.close();
        } catch (Exception ignored) {}
    }
}
