package baza.trainee.service.impl;

import baza.trainee.domain.model.Admin;
import baza.trainee.repository.AdminRepository;
import baza.trainee.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private PasswordEncoder passwordEncoder;

    public Admin createAdmin(String email, String rawPassword) {
        String encodedPassword = passwordEncoder.encode(rawPassword);

        Admin admin = new Admin();
        admin.setEmail(email);
        admin.setPassword(encodedPassword);

        return adminRepository.save(admin);
    }

    @Autowired
    public AdminServiceImpl(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Removed authenticate method
}