package baza.trainee.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties("storage")
public class StorageProperties {
    
    @Value("${resources.files.upload-dir}")
    private String location = "upload-dir";

}
