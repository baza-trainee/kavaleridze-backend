package baza.trainee.controller;

import baza.trainee.domain.model.Admin;
import baza.trainee.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    // Define endpoints for admin operations (login, authorization) here

    @PostMapping("/login")
    public Admin login(@RequestParam String email, @RequestParam String password) {
        // Implement the admin login endpoint using AdminService
        return adminService.authenticate(email, password);
    }

}
