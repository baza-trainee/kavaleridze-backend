package baza.trainee;

import baza.trainee.config.StorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class KavaleridzeBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(KavaleridzeBackendApplication.class, args);
    }

}
