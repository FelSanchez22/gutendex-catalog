package com.example.gutendexcatalog.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;

@Service
public class GutendexService {
    private static final String SEARCH_URL = "https://gutendex.com/books/?search={query}";
    private final RestTemplate restTemplate = new RestTemplate();

    public GutendexResponse searchByTitle(String title) {
        return restTemplate.getForObject(SEARCH_URL, GutendexResponse.class, title);
        // Alternativa con WebClient si prefieres reactivo
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class GutendexResponse {
        public int count;
        public String next;
        public String previous;
        public List<Book> results;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Book {
        public long id;
        public String title;
        public List<String> languages;
        @JsonProperty("download_count")
        public Integer downloadCount;
        public List<Author> authors;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Author {
        public String name;
        @JsonProperty("birth_year")
        public Integer birthYear;
        @JsonProperty("death_year")
        public Integer deathYear;
    }
}
