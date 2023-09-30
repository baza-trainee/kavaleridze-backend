package baza.trainee.integration;

import baza.trainee.config.SecurityConfig;
import baza.trainee.security.RootUserInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

/**
 * Abstract Test class that start Redis-stack container for integration tests.
 *
 * @author Evhen Malysh
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Testcontainers
abstract class AbstractIntegrationTest {

    private static final String REDIS_STACK_IMAGE = "redis/redis-stack:7.2.0-v0";
    private static final int REDIS_PORT = 6379;

    private static GenericContainer<?> redis;

    static {
        redis = new GenericContainer<>(DockerImageName.parse(REDIS_STACK_IMAGE))
                .withExposedPorts(REDIS_PORT);
        redis.start();
        System.setProperty("spring.data.redis.host", redis.getHost());
        System.setProperty("spring.data.redis.port", redis.getMappedPort(REDIS_PORT).toString());
    }

    @MockBean
    private RootUserInitializer rootUserInitializer;

}
