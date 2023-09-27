package baza.trainee.service;

import baza.trainee.domain.model.Admin;

public interface AdminService {


    Admin createAdmin(String email, String rawPassword);
    // Removed authenticate method
}