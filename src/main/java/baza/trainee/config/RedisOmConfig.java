package baza.trainee.config;

import baza.trainee.domain.model.Image;
import baza.trainee.repository.ImageRepository;
import com.redis.om.spring.annotations.EnableRedisDocumentRepositories;
import com.redis.om.spring.annotations.EnableRedisEnhancedRepositories;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRedisEnhancedRepositories(basePackageClasses = { ImageRepository.class, Image.class })
@EnableRedisDocumentRepositories
public class RedisOmConfig {
}
