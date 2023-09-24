package baza.trainee.service;

import baza.trainee.domain.model.Admin;
import baza.trainee.repository.AdminRepository;
import baza.trainee.service.impl.AdminServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.testcontainers.shaded.org.bouncycastle.cms.RecipientId.password;

@RunWith(MockitoJUnitRunner.class)
public class AdminServiceImplTest {
    @InjectMocks
    private AdminServiceImpl adminService;

    @Mock
    private AdminRepository adminRepository;

    @Test
    public void testAuthenticate_ValidAdmin() {
        // Create a test admin user with known email and password values
        String testEmail = "testadmin@example.com";
        String testPassword = "testpassword";
        Admin testAdmin = new Admin();
        testAdmin.setEmail(testEmail);
        testAdmin.setPassword(testPassword);

        // Set up your repository mock to return this test admin when findByEmailAndPassword is called
        when(adminRepository.findByEmailAndPassword(testEmail, testPassword)).thenReturn(testAdmin);

        // Perform the authentication
        Admin authenticatedAdmin = adminService.authenticate(testEmail, testPassword);

        // Assert that the authentication was successful
        assertNotNull(authenticatedAdmin);
        assertEquals(testAdmin.getEmail(), authenticatedAdmin.getEmail());
        assertEquals(testAdmin.getPassword(), authenticatedAdmin.getPassword());
    }

    @Test
    public void testAuthenticate_InvalidAdmin() {
        // Define your mock behavior for an invalid admin
        String email = "admin@example.com";
        String password = "password";

        when(adminRepository.findByEmailAndPassword(email, password)).thenReturn(null);

        // Call the method to be tested and assert that it throws the expected exception
        assertThrows(IllegalArgumentException.class, () -> adminService.authenticate(email, "password"));
    }

    @Test
    public void testAuthenticate_NullEmail() {
        // Test when a null email is provided, it should throw an exception
        assertThrows(IllegalArgumentException.class, () -> adminService.authenticate(null, "password"));
    }

    @Test
    public void testAuthenticate_NullPassword() {
        // Test when a null password is provided, it should throw an exception
        assertThrows(IllegalArgumentException.class, () -> adminService.authenticate("admin@example.com", null));
    }
}
