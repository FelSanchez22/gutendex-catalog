package com.example.gutendexcatalog.repository;

import com.example.gutendexcatalog.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByGutendexId(Long gutendexId);
    List<Book> findByLanguageIgnoreCase(String language);

    @Query("SELECT DISTINCT b FROM Book b LEFT JOIN FETCH b.authors")
    List<Book> findAllWithAuthors();
}
