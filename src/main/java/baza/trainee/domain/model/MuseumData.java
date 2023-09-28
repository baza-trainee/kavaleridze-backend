package baza.trainee.domain.model;

import com.redis.om.spring.annotations.Document;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@NoArgsConstructor
@Document
public class MuseumData {

    @Id
    private String id;
    private String phoneNumber;
    private String email;
    private String subwayRoute;
    private String busRoute;
    private String funicularRoute;
}
