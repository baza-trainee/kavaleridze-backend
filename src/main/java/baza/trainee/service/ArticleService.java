package baza.trainee.service;

import baza.trainee.domain.model.Article;
import baza.trainee.dto.ArticleResponse;

public interface ArticleService {

    /**
     * Finds an existing article by given title.
     *
     * @param title title to get an existing article.
     * @return {@link Article} object containing an existing article with its full content.
     */
    ArticleResponse findByTitle(String title);
}
