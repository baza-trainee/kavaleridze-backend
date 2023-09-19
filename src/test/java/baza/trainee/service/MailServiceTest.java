package baza.trainee.service;

import baza.trainee.service.impl.MailServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
<<<<<<< HEAD
import org.springframework.boot.test.mock.mockito.MockBean;
=======
import org.springframework.context.annotation.Import;
>>>>>>> dev

import static org.assertj.core.api.Assertions.assertThat;

<<<<<<< HEAD
@SpringBootTest
class MailServiceTest {
=======
<<<<<<< HEAD
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Import({ RedisTestConfig.class })
public class MailServiceTest {
=======
@SpringBootTest
class MailServiceTest {
>>>>>>> 7041faccf1f57ef5be98448514cd4ec6d5aa9a38
>>>>>>> dev

    @Autowired
    private MailServiceImpl mailService;

    @Test
    void testBuildMsgForUser() {
        String result = mailService.buildMsgForUser();
        assertThat(result).contains("Ваш запит відправлено. Очікуйте відповідь");
    }

    @Test
    void testBuildMsgForMuseum() {
        String result = mailService.buildMsgForMuseum("John", "Doe",
                "test@example.com", "Message");
        assertThat(result).contains("John", "Doe", "test@example.com", "Message");
    }
}
