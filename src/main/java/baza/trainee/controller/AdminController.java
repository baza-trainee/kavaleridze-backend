package baza.trainee.controller;

import baza.trainee.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


        /**
        * AdminController handles all the admin-specific functionalities.
        */

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController {

    private final AdminService adminService;

    /**
     * Constructor-based Dependency Injection.
     *
     * @param adminService the admin service
     */
    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    // The login endpoint has been removed as it is handled by Spring Security.
}

