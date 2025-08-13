package com.example.gutendexcatalog.service;

import com.example.gutendexcatalog.model.Author;
import com.example.gutendexcatalog.model.Book;
import com.example.gutendexcatalog.repository.AuthorRepository;
import com.example.gutendexcatalog.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CatalogService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public CatalogService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Transactional
    public Book saveFromGutendex(GutendexService.Book gBook) {
        Optional<Book> existing = bookRepository.findByGutendexId(gBook.id);
        if (existing.isPresent()) {
            return existing.get();
        }

        String language = (gBook.languages != null && !gBook.languages.isEmpty()) ? gBook.languages.get(0) : null;
        Book book = new Book(gBook.title, language, gBook.downloadCount, gBook.id);

        Set<Author> authors = new HashSet<>();
        if (gBook.authors != null) {
            for (GutendexService.Author ga : gBook.authors) {
                Author author = authorRepository.findByName(ga.name)
                        .orElseGet(() -> authorRepository.save(new Author(ga.name, ga.birthYear, ga.deathYear)));
                authors.add(author);
            }
        }
        book.setAuthors(authors);
        return bookRepository.save(book);
    }

    public List<Book> listAllBooks() {
        return bookRepository.findAll();
    }

    public List<Author> listAllAuthors() {
        return authorRepository.findAll();
    }

    public List<Author> listAuthorsAliveIn(int year) {
        List<Author> aliveWithDeath = authorRepository.findByBirthYearLessThanEqualAndDeathYearGreaterThan(year, year);
        List<Author> aliveNoDeath = authorRepository.findByBirthYearLessThanEqualAndDeathYearIsNull(year);
        return java.util.stream.Stream.concat(aliveWithDeath.stream(), aliveNoDeath.stream())
                .distinct()
                .collect(Collectors.toList());
    }

    public List<Book> listBooksByLanguage(String language) {
        return bookRepository.findByLanguageIgnoreCase(language);
    }

    public List<Book> listAllBooksWithAuthors() {
        return bookRepository.findAllWithAuthors();
    }
}
