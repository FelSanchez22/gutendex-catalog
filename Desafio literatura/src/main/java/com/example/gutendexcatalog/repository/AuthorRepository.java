package com.example.gutendexcatalog.repository;

import com.example.gutendexcatalog.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findByName(String name);
    List<Author> findByBirthYearLessThanEqualAndDeathYearGreaterThan(Integer year, Integer year2);
    List<Author> findByBirthYearLessThanEqualAndDeathYearIsNull(Integer year);
}
