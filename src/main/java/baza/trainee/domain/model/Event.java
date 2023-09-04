package baza.trainee.domain.model;

import com.redis.om.spring.annotations.Document;
import com.redis.om.spring.annotations.Indexed;
import com.redis.om.spring.annotations.Searchable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDate;

@Data
@RequiredArgsConstructor
@Document
public class Event {
    @Indexed
    @NotNull
    private String id;

    @Searchable
    @NotNull
    private String title;

    @Searchable
    @NotNull
    private String content;

    @URL
    private String picture;

    @NotNull
    private LocalDate begin;

    @NotNull
    private LocalDate end;

    private LocalDate created;

    @PastOrPresent
    private LocalDate updated;

    /**
     * Create a new Event object with the specified attributes.
     *
     * @param id      The unique identifier of the event.
     * @param title   The title of the event.
     * @param content The content or description of the event.
     * @param picture The picture associated with the event.
     * @param begin   The start date of the event.
     * @param end     The end date of the event.
     */
    public Event(final String id, final String title, final String content, final String picture,
                 final LocalDate begin, final LocalDate end) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.picture = picture;
        this.begin = begin;
        this.end = end;
        this.created = LocalDate.now();
    }
}
