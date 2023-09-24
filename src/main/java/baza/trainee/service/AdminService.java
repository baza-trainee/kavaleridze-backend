package baza.trainee.service;

import baza.trainee.domain.model.Admin;

public interface AdminService {

    /**
     * Authenticate an admin user by email (login) and password.
     *
     * @param email    The email address of the admin user.
     * @param password The password of the admin user.
     * @return The authenticated admin user.
     */
    Admin authenticate(String email, String password);

    // Define other admin-related methods here if needed
}
