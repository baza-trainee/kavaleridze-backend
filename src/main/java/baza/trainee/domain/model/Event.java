package baza.trainee.domain.model;

import baza.trainee.constants.EventConstant;
import baza.trainee.domain.enums.ContentType;
import com.redis.om.spring.annotations.Document;
import com.redis.om.spring.annotations.Indexed;
import com.redis.om.spring.annotations.Searchable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDate;

@Data
@Document
public class Event {
    @Indexed
    @NotNull
    private String id;

    @Searchable
    @NotNull
    private ContentType contentType;

    @Searchable
    @NotNull
    @NotBlank
    @Size(max = EventConstant.MAX_EVENT_TITLE_SIZE)
    private String title;

    @Searchable
    @NotNull
    @Size(max = EventConstant.MAX_EVENT_CONTENT_SIZE)
    private String content;

    @NotNull
    @Searchable
    @NotBlank
    @Size(max = EventConstant.MAX_EVENT_SHORT_DESCRIPTION_SIZE)
    private String shortDescription;

    @URL
    private String picture;

    @URL
    private String picturePreview;

    @NotNull
    private LocalDate begin;

    @NotNull
    private LocalDate end;

    @PastOrPresent
    private LocalDate created;

    @PastOrPresent
    private LocalDate updated;

    /**
     * An empty constructor (with no arguments).
     */
    public Event() {
        this.contentType = ContentType.EVENT;
        this.created = LocalDate.now();
    }

}
