package baza.trainee.api.impl;

import baza.trainee.api.SearchApiDelegate;
import baza.trainee.dto.SearchResponse;
import baza.trainee.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchApiDelegateImpl implements SearchApiDelegate {

    private final SearchService searchService;

    @Override
    public ResponseEntity<List<SearchResponse>> search(String query) {
        return new ResponseEntity<>(searchService.search(query), HttpStatusCode.valueOf(200));
    }
}
