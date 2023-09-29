package baza.trainee.api.impl;

import baza.trainee.api.ArticlesApiDelegate;
import baza.trainee.dto.ArticleResponse;
import baza.trainee.service.ArticleService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Spring MVC REST controller serving article operations for non-admin users.
 *
 * @author Olha Ryzhkova
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class ArticlesApiDelegateImpl implements ArticlesApiDelegate {

    /**
     * The service responsible for performing article operations.
     */
    private final ArticleService articleService;

    /**
     * Finds an existing article by given title.
     *
     * @param title title to get an existing article.
     * @return {@link ArticleResponse} object containing an existing article with its full content.
     */
    @Override
    public ResponseEntity<ArticleResponse> findByTitle(
            @Parameter(description = "Title of the article")
            @PathVariable(name = "title")
            @NotBlank final String title
    ) {
        return new ResponseEntity<>(articleService.findByTitle(title), HttpStatusCode.valueOf(200));
    }
}
