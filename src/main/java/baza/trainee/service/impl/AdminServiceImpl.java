package baza.trainee.service.impl;

import baza.trainee.domain.model.Admin;
import baza.trainee.repository.AdminRepository;
import baza.trainee.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;

    @Autowired
    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public Admin authenticate(String email, String password) {
        Admin admin = adminRepository.findByEmailAndPassword(email, password);
        if (admin == null || admin.getPassword() == null) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        if (!admin.getPassword().equals(password)) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        return admin;
    }
}
