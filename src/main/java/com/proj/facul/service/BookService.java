package com.proj.facul.service;

import com.proj.facul.domain.Book;
import com.proj.facul.dto.request.BookUpdateRequest;
import com.proj.facul.exception.DuplicateBookException;
import com.proj.facul.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public Book addBook(Book book) {
        validateDuplicates(book, null);
        return bookRepository.save(book);
    }

    public Book getBookById(Long id) {
        Optional<Book> book = bookRepository.findById(id);
        return book.orElseThrow(() -> new RuntimeException("Livro não encontrado"));
    }

    public Book updateBook(Long id, BookUpdateRequest bookUpdateRequest) throws ParseException {
        Book existingBook = getBookById(id);

        if (bookUpdateRequest.getTitle() != null) {
            existingBook.setTitle(bookUpdateRequest.getTitle());
        }
        if (bookUpdateRequest.getAuthor() != null) {
            existingBook.setAuthor(bookUpdateRequest.getAuthor());
        }
        if (bookUpdateRequest.getPublicationYear() != null) {
            existingBook.setPublicationYear(bookUpdateRequest.getPublicationYear());
        }
        if (bookUpdateRequest.getGenre() != null) {
            existingBook.setGenre(bookUpdateRequest.getGenre());
        }
        if (bookUpdateRequest.getDescription() != null) {
            existingBook.setDescription(bookUpdateRequest.getDescription());
        }
        if (bookUpdateRequest.getAvailable() != null) {
            existingBook.setAvailable(bookUpdateRequest.getAvailable());
        }
        validateDuplicates(existingBook, id);

        return bookRepository.save(existingBook);
    }

    private void validateDuplicates(Book book, Long existingBookId) {
        if (existingBookId == null) {
            if (bookRepository.existsByTitleAndAuthor(book.getTitle(), book.getAuthor())) {
                throw new DuplicateBookException("O livro com este título e autor já existe.");
            }
        } else {
            if (bookRepository.existsByTitleAndAuthorAndIdNot(book.getTitle(), book.getAuthor(), existingBookId)) {
                throw new DuplicateBookException("O livro com este título e autor já existe.");
            }
        }
    }

    public void deleteBook(Long id) {
        try {
            bookRepository.deleteById(id);
        } catch (RuntimeException e) {
            throw new RuntimeException("Id do livro não encontrado");
        }
    }

    public List<Book> getBooks() {
        return bookRepository.findAll();
    }
}
