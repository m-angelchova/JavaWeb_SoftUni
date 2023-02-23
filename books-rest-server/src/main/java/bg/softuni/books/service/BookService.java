package bg.softuni.books.service;

import bg.softuni.books.model.dto.AuthorDTO;
import bg.softuni.books.model.dto.BookDTO;
import bg.softuni.books.model.entity.Author;
import bg.softuni.books.model.entity.Book;
import bg.softuni.books.repository.AuthorRepository;
import bg.softuni.books.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Autowired
    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public List<BookDTO> getAllBooks(){
        return bookRepository.findAll().stream().map(this::map).toList();
    }

    private BookDTO map(Book book){
        BookDTO bookDTO = new BookDTO();

        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setName(book.getAuthor().getName());

        bookDTO.setId(book.getId());
        bookDTO.setTitle(book.getTitle());
        bookDTO.setIsbn(book.getIsbn());
        bookDTO.setAuthor(authorDTO);

        return bookDTO;
    }

    public Optional<BookDTO> findBookById(Long bookId){
        return bookRepository.findById(bookId).map(this::map);
    }

    public void deleteBookById(Long bookId){
        bookRepository.deleteById(bookId);
    }

    public long createBook(BookDTO newBook) {
        String authorName = newBook.getAuthor().getName();
        Optional<Author> authorOpt = this.authorRepository.findAuthorByName(authorName);

        Book book = new Book();

        book.setTitle(newBook.getTitle());
        book.setIsbn(newBook.getIsbn());
        book.setAuthor(authorOpt.orElseGet(() -> createAuthor(authorName)));

        return this.bookRepository.save(book).getId();
    }

    private Author createAuthor(String authorName){
        Author author = new Author();
        author.setName(authorName);


        this.authorRepository.save(author);
        return author;
    }
}
