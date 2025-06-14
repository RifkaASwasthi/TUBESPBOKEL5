package com.hospital.dao;

import com.hospital.model.Patient;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO {
    private final Connection conn;

    public PatientDAO(Connection conn) {
        this.conn = conn;
    }

    // CREATE - Insert pasien baru
    public boolean insert(Patient patient) throws SQLException {
        String sql = "INSERT INTO patients (rm_number, name, age, address, phone, type, bpjs_number, bpjs_class, complaint, registration_date, created_at, updated_at) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, patient.getRmNumber());
            stmt.setString(2, patient.getName());
            stmt.setInt(3, patient.getAge());
            stmt.setString(4, patient.getAddress());
            stmt.setString(5, patient.getPhone());
            stmt.setString(6, patient.getType());
            stmt.setString(7, patient.getBpjsNumber());
            stmt.setString(8, patient.getBpjsClass());
            stmt.setString(9, patient.getComplaint());
            stmt.setDate(10, Date.valueOf(patient.getRegistrationDate()));
            stmt.setTimestamp(11, Timestamp.valueOf(patient.getCreatedAt()));
            stmt.setTimestamp(12, Timestamp.valueOf(patient.getUpdatedAt()));
            int affected = stmt.executeUpdate();

            if (affected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        patient.setId(generatedKeys.getInt(1));
                    }
                }
            }

            return affected > 0;
        }
    }

    // READ - Ambil semua pasien
    public List<Patient> getAll() throws SQLException {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM patients ORDER BY id DESC";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                patients.add(mapResultSetToPatient(rs));
            }
        }
        return patients;
    }

    // READ - Ambil pasien berdasarkan ID
    public Patient getById(int id) throws SQLException {
        String sql = "SELECT * FROM patients WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToPatient(rs);
                }
            }
        }
        return null;
    }

    // UPDATE - Update data pasien
    public boolean update(Patient patient) throws SQLException {
        String sql = "UPDATE patients SET name=?, age=?, address=?, phone=?, type=?, bpjs_number=?, bpjs_class=?, complaint=?, updated_at=? WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, patient.getName());
            stmt.setInt(2, patient.getAge());
            stmt.setString(3, patient.getAddress());
            stmt.setString(4, patient.getPhone());
            stmt.setString(5, patient.getType());
            stmt.setString(6, patient.getBpjsNumber());
            stmt.setString(7, patient.getBpjsClass());
            stmt.setString(8, patient.getComplaint());
            stmt.setTimestamp(9, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setInt(10, patient.getId());
            return stmt.executeUpdate() > 0;
        }
    }

    // DELETE - Hapus pasien berdasarkan ID
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM patients WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    // Mapping data dari ResultSet ke objek Patient
    private Patient mapResultSetToPatient(ResultSet rs) throws SQLException {
        return new Patient(
            rs.getInt("id"),
            rs.getString("rm_number"),
            rs.getString("name"),
            rs.getInt("age"),
            rs.getString("address"),
            rs.getString("phone"),
            rs.getString("type"),
            rs.getString("bpjs_number"),
            rs.getString("bpjs_class"),
            rs.getString("complaint"),
            rs.getDate("registration_date").toLocalDate(),
            rs.getTimestamp("created_at").toLocalDateTime(),
            rs.getTimestamp("updated_at").toLocalDateTime()
        );
    }
}
