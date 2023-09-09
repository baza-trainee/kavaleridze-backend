package baza.trainee.domain.model;

import com.redis.om.spring.annotations.Document;
import com.redis.om.spring.annotations.Indexed;
import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static baza.trainee.constants.EventConstant.MAX_DESCRIPTION_SIZE;
import static baza.trainee.constants.EventConstant.MAX_TITLE_SIZE;
import static baza.trainee.constants.EventConstant.MIN_DESCRIPTION_SIZE;
import static baza.trainee.constants.EventConstant.MIN_TITLE_SIZE;

@Getter
@Setter
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
    private String description;

    private String type;

    @Setter(AccessLevel.PROTECTED)
    private Set<String> tags = new HashSet<>();

    @Setter(AccessLevel.PROTECTED)
    private Set<ContentBlock> content = new HashSet<>();

    private String bannerURI;

    private String bannerPreviewURI;

    @NotNull
    @FutureOrPresent
    private LocalDate begin;

    @NotNull
    @FutureOrPresent
    private LocalDate end;

    @Setter(AccessLevel.PROTECTED)
    @CreatedDate
    private LocalDate created;

    @Setter(AccessLevel.PROTECTED)
    @LastModifiedDate
    private LocalDate updated;

    public Event(
            @NotNull @NotBlank String id,
            @NotNull @NotBlank @Size(min = MIN_TITLE_SIZE, max = MAX_TITLE_SIZE) String title,
            @NotNull @NotBlank @Size(min = MIN_DESCRIPTION_SIZE, max = MAX_DESCRIPTION_SIZE) String description,
            String type,
            String bannerURI,
            String bannerPreviewURI,
            @FutureOrPresent LocalDate begin,
            @FutureOrPresent LocalDate end
    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.type = type;
        this.bannerURI = bannerURI;
        this.bannerPreviewURI = bannerPreviewURI;
        this.begin = begin;
        this.end = end;
    }

    public void addTag(@NotNull @NotBlank String tag) {
        this.tags.add(tag);
    }

    public void addContentBlock(@Valid ContentBlock block) {
        this.content.add(block);
    }
}
