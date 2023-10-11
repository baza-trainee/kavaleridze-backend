package baza.trainee.controller.admin;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import baza.trainee.security.RootUserInitializer;
import baza.trainee.security.TokenService;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class AuthenticationTest {

    private static final String TOKEN = "SOME_TOKEN";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TokenService tokenService;

    @MockBean
    private RootUserInitializer rootUserInitializer;

    @Test
    @WithMockUser(roles = { "ADMIN" })
    void userShouldGetResponseWithToken_whenHasRoleAdmin() throws Exception {
        // when:
        when(tokenService.generateToken(any(Authentication.class))).thenReturn(TOKEN);

        // then:
        mockMvc.perform(post("/api/admin/login"))
                .andExpect(jsonPath("$.token").value(TOKEN))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    void anonymousUserCantLoginAndGetToken() throws Exception {
        // when:
        when(tokenService.generateToken(any(Authentication.class))).thenReturn(TOKEN);

        // then:
        mockMvc.perform(post("/api/admin/login"))
                .andExpect(jsonPath("$.token").doesNotExist())
                .andExpect(status().isUnauthorized());
    }
}