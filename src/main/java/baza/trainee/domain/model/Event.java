package baza.trainee.domain.model;

import com.redis.om.spring.annotations.Document;
import com.redis.om.spring.annotations.Indexed;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.util.Objects;

import static baza.trainee.constants.EventConstant.MAX_DESCRIPTION_SIZE;
import static baza.trainee.constants.EventConstant.MAX_TITLE_SIZE;
import static baza.trainee.constants.EventConstant.MIN_DESCRIPTION_SIZE;
import static baza.trainee.constants.EventConstant.MIN_TITLE_SIZE;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Document
public class Event implements Post {

    @Id
    @Indexed
    private String id;

    @Indexed
    @NotBlank
    @Size(min = MIN_TITLE_SIZE, max = MAX_TITLE_SIZE)
    private String title;

    @Indexed
    @NotNull
    @NotBlank
    @Size(min = MIN_DESCRIPTION_SIZE, max = MAX_DESCRIPTION_SIZE)
    private String summary;

    @Indexed
    @NotBlank
    private String description;

    private String type;

    private String bannerId;

    @NotNull
    @FutureOrPresent
    private LocalDate begin;

    @NotNull
    @FutureOrPresent
    private LocalDate end;

    @CreatedDate
    private LocalDate created;

    @LastModifiedDate
    private LocalDate updated;

    public Event(
            String id,
            @NotBlank @Size(min = 3, max = 300) String title,
            @NotNull @NotBlank @Size(min = 3, max = 300) String summary,
            @NotBlank String description, String type,
            String bannerId,
            @NotNull @FutureOrPresent LocalDate begin,
            @NotNull @FutureOrPresent LocalDate end
    ) {
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.description = description;
        this.type = type;
        this.bannerId = bannerId;
        this.begin = begin;
        this.end = end;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, summary, type, description, bannerId, begin, end);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Event other)) {
            return false;
        }
        return Objects.equals(id, other.id)
                && Objects.equals(title, other.title)
                && Objects.equals(summary, other.summary)
                && Objects.equals(type, other.type)
                && Objects.equals(description, other.description)
                && Objects.equals(bannerId, other.bannerId)
                && Objects.equals(end, other.end);
    }

}
