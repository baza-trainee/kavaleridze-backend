package baza.trainee;

import baza.trainee.integration.MockConfiguration;
import baza.trainee.integration.RedisTestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import({ RedisTestConfig.class, MockConfiguration.class })
class KavaleridzeBackendApplicationTests {

    @Test
    void contextLoads() {
    }

}
