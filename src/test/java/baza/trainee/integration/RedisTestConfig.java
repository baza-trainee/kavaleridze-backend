package baza.trainee.integration;

import baza.trainee.domain.model.Article;
import baza.trainee.domain.model.Event;
import com.redis.om.spring.annotations.EnableRedisDocumentRepositories;
import org.springframework.boot.test.context.TestConfiguration;

/**
 * Jedis test configurations.
 *
 * @author Evhen Malysh
 */
@TestConfiguration
@EnableRedisDocumentRepositories(
        basePackageClasses = {
                TestEventRepository.class,
                TestArticleRepository.class,
                Event.class,
                Article.class,
        })
public class RedisTestConfig { }
