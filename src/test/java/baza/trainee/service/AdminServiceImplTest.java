package baza.trainee.service;

import baza.trainee.domain.model.Admin;
import baza.trainee.service.impl.AdminServiceImpl;
import baza.trainee.repository.AdminRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AdminServiceImplTest {

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AdminServiceImpl adminService;

    @Test
    public void testCreateAdmin() {
        // Given
        String email = "admin@example.com";
        String rawPassword = "password";
        String encodedPassword = "encodedPassword";

        Admin admin = new Admin();
        admin.setEmail(email);
        admin.setPassword(encodedPassword);

        when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);
        when(adminRepository.save(any(Admin.class))).thenReturn(admin);

        // When
        Admin result = adminService.createAdmin(email, rawPassword);

        // Then
        verify(passwordEncoder).encode(rawPassword);
        verify(adminRepository).save(any(Admin.class));
    }
}
