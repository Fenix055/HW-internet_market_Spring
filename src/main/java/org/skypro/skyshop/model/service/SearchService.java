package org.skypro.skyshop.model.service;

import org.skypro.skyshop.model.search.SearchResult;
import org.skypro.skyshop.model.search.Searchable;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class SearchService {
    private final StorageService storageService;

    public SearchService(StorageService storageService) {
        this.storageService = storageService;
    }

    public Collection<SearchResult> search(String pattern) {
        return storageService.getAllSearchable().stream()
                .filter(searchable -> searchable.getSearchableName().toLowerCase().contains(pattern.toLowerCase()))
                .map(SearchResult::fromSearchable)
                .toList();
    }
}
