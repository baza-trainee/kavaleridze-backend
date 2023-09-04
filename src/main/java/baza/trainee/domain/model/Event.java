package baza.trainee.domain.model;

import com.redis.om.spring.annotations.Document;
import com.redis.om.spring.annotations.Indexed;
import com.redis.om.spring.annotations.Searchable;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@RequiredArgsConstructor
@Document
public class Event {
    @Indexed
    private String id;
    @Searchable
    private String title;
    @Searchable
    private String content;
    private String picture;
    private LocalDate begin;
    private LocalDate end;
    private LocalDate created;
    private LocalDate updated;
}
