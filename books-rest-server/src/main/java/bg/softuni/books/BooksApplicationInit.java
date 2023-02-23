package bg.softuni.books;

import bg.softuni.books.model.entity.Author;
import bg.softuni.books.model.entity.Book;
import bg.softuni.books.repository.AuthorRepository;
import bg.softuni.books.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class BooksApplicationInit implements CommandLineRunner {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;


    BooksApplicationInit(AuthorRepository authorRepository,
                         BookRepository bookRepository) {

        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        if (bookRepository.count() == 0 && authorRepository.count() == 0) {
            initJovkov();
            initNikolaiHaitov();
            initDimitarTalev();
            initElinPelin();
            initVazov();
        }
    }

    private void initNikolaiHaitov() {
        initAuthor("Николай Хайтов",
                "Диви Разкази"
        );
    }

    private void initDimitarTalev() {
        initAuthor("Димитър Димов",
                "Тютюн"
        );
    }

    private void initElinPelin() {
        initAuthor("Елин Пелин",
                "Пижо и Пендо",
                "Ян Бибиян на луната",
                "Под манастирската лоза"
        );
    }

    private void initVazov() {
        initAuthor("Иван Вазов",
                "Пряпорец и Гусла",
                "Под Игото",
                "Тъгите на България"
        );
    }

    private void initJovkov() {

        initAuthor("Йордан Йовков",
                "Старопланински легенди",
                "Чифликът край границата");
    }

    private void initAuthor(String authorName, String... books) {
        Author author = new Author();
        author.setName(authorName);
        author = authorRepository.save(author);

        List<Book> allBooks = new ArrayList<>();

        for (String book: books) {
            Book aBook = new Book();
            aBook.setAuthor(author);
            aBook.setTitle(book);
            aBook.setIsbn(UUID.randomUUID().toString());//random string, not real, dummy
            allBooks.add(aBook);
        }

        author.setBooks(allBooks);
        authorRepository.save(author);

        bookRepository.saveAll(allBooks);
    }
}