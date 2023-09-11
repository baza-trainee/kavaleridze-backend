package baza.trainee.controller;

import baza.trainee.domain.model.Article;
import baza.trainee.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Spring MVC REST controller serving article operations for non-admin users.
 *
 * @author Olha Ryzhkova
 * @version 1.0
 */
@RestController
@RequestMapping("/api/article")
@RequiredArgsConstructor
public class ArticleController {

    /**
     * The service responsible for performing article operations.
     */
    private final ArticleService articleService;

    /**
     * Finds an existing article by given title.
     *
     * @param title title to get an existing article.
     * @return {@link Article} object containing an existing article with its full content.
     */
    @GetMapping(value = "/{title}", produces = "application/json")
    public Article findByTitle(@PathVariable(name = "title") final String title) {
        return articleService.findByTitle(title);
    }
}
