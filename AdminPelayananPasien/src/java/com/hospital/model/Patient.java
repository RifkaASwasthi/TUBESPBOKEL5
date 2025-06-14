package com.hospital.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Patient {
    private int id;
    private String rmNumber;
    private String name;
    private int age;
    private String address;
    private String phone;
    private String type; // Umum / BPJS
    private String bpjsNumber;
    private String bpjsClass; // 1 / 2 / 3
    private String complaint;
    private LocalDate registrationDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Default Constructor
    public Patient() {}

    // Constructor untuk data baru (tanpa id, rmNumber, timestamp)
    public Patient(String name, int age, String address, String phone, String type,
                   String bpjsNumber, String bpjsClass, String complaint) {
        this.name = name;
        this.age = age;
        this.address = address;
        this.phone = phone;
        this.type = type;
        this.bpjsNumber = bpjsNumber;
        this.bpjsClass = bpjsClass;
        this.complaint = complaint;
        this.registrationDate = LocalDate.now();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Full constructor (biasanya digunakan saat mengambil dari database)
    public Patient(int id, String rmNumber, String name, int age, String address, String phone,
                   String type, String bpjsNumber, String bpjsClass, String complaint,
                   LocalDate registrationDate, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.rmNumber = rmNumber;
        this.name = name;
        this.age = age;
        this.address = address;
        this.phone = phone;
        this.type = type;
        this.bpjsNumber = bpjsNumber;
        this.bpjsClass = bpjsClass;
        this.complaint = complaint;
        this.registrationDate = registrationDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // ====== Getters and Setters ======

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRmNumber() {
        return rmNumber;
    }

    public void setRmNumber(String rmNumber) {
        this.rmNumber = rmNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBpjsNumber() {
        return bpjsNumber;
    }

    public void setBpjsNumber(String bpjsNumber) {
        this.bpjsNumber = bpjsNumber;
    }

    public String getBpjsClass() {
        return bpjsClass;
    }

    public void setBpjsClass(String bpjsClass) {
        this.bpjsClass = bpjsClass;
    }

    public String getComplaint() {
        return complaint;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    // Optional: format tampilan umur
    public String getAgeDisplay() {
        return age + " tahun";
    }

    // Optional: validasi BPJS
    public boolean isBpjsPatient() {
        return "BPJS".equalsIgnoreCase(type);
    }

    public boolean isValidBpjsNumber() {
        return !isBpjsPatient() || (bpjsNumber != null && bpjsNumber.matches("\\d{13}"));
    }

    public boolean isValidPhone() {
        return phone != null && phone.matches("\\d{10,15}");
    }
}
