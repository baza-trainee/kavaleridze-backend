package baza.trainee.api.impl;

import baza.trainee.api.SearchApiDelegate;
import baza.trainee.dto.SearchResponse;
import baza.trainee.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The {@code SearchController} class is a Spring MVC REST controller
 * responsible for handling search-related requests and returning
 * result of searching (list of {@link SearchResponse}).
 * It exposes endpoints under the "/api/search" base path.
 * <p>
 * This controller ensures that queries meet certain validation criteria and
 * delegates the actual search functionality to the {@link SearchService}.
 *
 * @author Dmytro Teliukov
 * @version 1.0
 * @since 2023-09-03
 */
@Service
@RequiredArgsConstructor
public class SearchApiDelegateImpl implements SearchApiDelegate {
    /**
     * The service responsible for performing search operations.
     */
    private final SearchService searchService;

    /**
     * Performs a search operation based on the provided query string.
     *
     * @param query The search query string to be validated and processed.
     * @return A list of {@link SearchResponse} objects representing
     * the search results.
     */
    @Override
    public ResponseEntity<List<SearchResponse>> search(String query) {
        return new ResponseEntity<>(searchService.search(query), HttpStatusCode.valueOf(200));
    }
}
