package com.example.gutendexcatalog.console;

import com.example.gutendexcatalog.model.Author;
import com.example.gutendexcatalog.model.Book;
import com.example.gutendexcatalog.service.CatalogService;
import com.example.gutendexcatalog.service.GutendexService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@Component
public class ConsoleApp implements CommandLineRunner {

    private final GutendexService gutendexService;
    private final CatalogService catalogService;

    public ConsoleApp(GutendexService gutendexService, CatalogService catalogService) {
        this.gutendexService = gutendexService;
        this.catalogService = catalogService;
    }

    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);
        int option = -1;
        while (option != 0) {
            System.out.println("--------------------");
            System.out.println("Elija la opcion a traves de su numero:");
            System.out.println("1- Buscar libro por titulo.");
            System.out.println("2- Listar libros registrados.");
            System.out.println("3- Listar autores registrados.");
            System.out.println("4- Listar autores vivos en un determinado año.");
            System.out.println("5- Listar libros por idioma.");
            System.out.println("0- Salir.");
            try {
                option = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                option = -1;
            }

            switch (option) {
                case 1 -> searchAndRegister(scanner);
                case 2 -> listBooks();
                case 3 -> listAuthors();
                case 4 -> listAuthorsAlive(scanner);
                case 5 -> listBooksByLanguage(scanner);
                case 0 -> System.out.println("Chao pescao!");
                default -> System.out.println("Opcion no valida. Intente nuevamente.");
            }
        }
    }

    private void searchAndRegister(Scanner scanner) {
        System.out.println("Ingrese el nombre del libro que desea buscar:");
        String query = scanner.nextLine().trim();
        GutendexService.GutendexResponse response = gutendexService.searchByTitle(query);
        if (response == null || response.results == null || response.results.isEmpty()) {
            System.out.println("No se encontraron resultados para: " + query);
            return;
        }
        GutendexService.Book gBook = response.results.get(0);
        Book saved = catalogService.saveFromGutendex(gBook);

        String authors = saved.getAuthors().stream().map(Author::getName).collect(Collectors.joining(", "));
        System.out.println("----- LIBRO -----");
        System.out.println("Titulo: " + saved.getTitle());
        System.out.println("Autor: " + (authors.isBlank() ? "Desconocido" : authors));
        System.out.println("Idioma: " + (saved.getLanguage() == null ? "N/A" : saved.getLanguage()));
        System.out.println("Numero de descargas: " + (saved.getDownloadCount() == null ? 0 : saved.getDownloadCount()));
        System.out.println("------------------");
    }

    private void listBooks() {
        List<Book> books = catalogService.listAllBooksWithAuthors();
        if (books.isEmpty()) {
            System.out.println("No hay libros registrados.");
            return;
        }
        books.forEach(b -> {
            String authors = b.getAuthors().stream().map(Author::getName).collect(Collectors.joining(", "));
            System.out.println(b.getId() + " - " + b.getTitle() + " | " + authors + " | " + b.getLanguage() + " | descargas: " + b.getDownloadCount());
        });
    }

    private void listAuthors() {
        List<Author> authors = catalogService.listAllAuthors();
        if (authors.isEmpty()) {
            System.out.println("No hay autores registrados.");
            return;
        }
        authors.forEach(a ->
                System.out.println(a.getId() + " - " + a.getName() + " (" + (a.getBirthYear()==null?"?":a.getBirthYear()) + " - " + (a.getDeathYear()==null?"?":a.getDeathYear()) + ")")
        );
    }

    private void listAuthorsAlive(Scanner scanner) {
        System.out.println("Ingrese el año:");
        String input = scanner.nextLine().trim();
        int year;
        try {
            year = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Año inválido.");
            return;
        }
        List<Author> alive = catalogService.listAuthorsAliveIn(year);
        if (alive.isEmpty()) {
            System.out.println("No hay autores vivos en " + year + " registrados en la base.");
            return;
        }
        alive.forEach(a -> System.out.println(a.getName()));
    }

    private void listBooksByLanguage(Scanner scanner) {
        System.out.println("Ingrese el codigo de idioma (ej: en, es, fr):");
        String lang = scanner.nextLine().trim();
        List<Book> books = catalogService.listBooksByLanguage(lang);
        if (books.isEmpty()) {
            System.out.println("No hay libros en idioma '" + lang + "' registrados.");
            return;
        }
        books.forEach(b -> System.out.println(b.getTitle()));
    }
}
