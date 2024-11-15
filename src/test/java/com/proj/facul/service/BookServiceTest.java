package com.proj.facul.service;

import com.proj.facul.domain.Book;
import com.proj.facul.dto.request.BookUpdateRequest;
import com.proj.facul.exception.DuplicateBookException;
import com.proj.facul.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceTest {

    @Mock
    private BookRepository bookRepository;
    @InjectMocks
    private BookService bookService;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void whenAddBookSuccess() {
        Book book = new Book();
        book.setTitle("Test Title");
        book.setAuthor("Test Author");

        when(bookRepository.existsByTitleAndAuthor(book.getTitle(), book.getAuthor())).thenReturn(false);
        when(bookRepository.save(book)).thenReturn(book);

        Book result = bookService.addBook(book);

        assertNotNull(result);
        verify(bookRepository, times(1)).save(book);
    }
    @Test
    void whenAddBookDuplicateBookException() {
        Book book = new Book();
        book.setTitle("Test Title");
        book.setAuthor("Test Author");

        when(bookRepository.existsByTitleAndAuthor(book.getTitle(), book.getAuthor())).thenReturn(true);

        assertThrows(DuplicateBookException.class, () -> bookService.addBook(book));
        verify(bookRepository, never()).save(book);
    }
    @Test
    void whenGetBookByIdSuccess() {
        Long id = 1L;
        Book book = new Book();
        book.setId(id);

        when(bookRepository.findById(id)).thenReturn(Optional.of(book));

        Book result = bookService.getBookById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
    }
    @Test
    void whenGetBookByIdNotFound() {
        Long id = 1L;
        when(bookRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> bookService.getBookById(id));
    }
    @Test
    void whenUpdateBookSuccess() throws Exception {
        Long id = 1L;
        Book existingBook = new Book();
        existingBook.setId(id);
        existingBook.setTitle("Título antigo");

        BookUpdateRequest updateRequest = new BookUpdateRequest();
        updateRequest.setTitle("Novo título");

        when(bookRepository.findById(id)).thenReturn(Optional.of(existingBook));
        when(bookRepository.existsByTitleAndAuthorAndIdNot("Novo título", null, id)).thenReturn(false);
        when(bookRepository.save(existingBook)).thenReturn(existingBook);

        Book result = bookService.updateBook(id, updateRequest);

        assertEquals("Novo título", result.getTitle());
        verify(bookRepository, times(1)).save(existingBook);
    }
    @Test
    void whenUpdateBookUpdateAllFields() throws Exception {
        Long id = 1L;
        Book existingBook = new Book();
        existingBook.setId(id);
        existingBook.setTitle("Título antigo");
        existingBook.setAuthor("Autor antigo");
        existingBook.setPublicationYear("2024");
        existingBook.setGenre("Gênero antigo");
        existingBook.setDescription("Descrição antiga");
        existingBook.setAvailable(false);

        BookUpdateRequest updateRequest = new BookUpdateRequest();
        updateRequest.setTitle("Novo título");
        updateRequest.setAuthor("Novo autor");
        updateRequest.setPublicationYear("2025");
        updateRequest.setGenre("Novo gênero");
        updateRequest.setDescription("Nova descrição");
        updateRequest.setAvailable(true);

        when(bookRepository.findById(id)).thenReturn(Optional.of(existingBook));
        when(bookRepository.existsByTitleAndAuthorAndIdNot("Novo título", "Novo autor", id)).thenReturn(false);
        when(bookRepository.save(existingBook)).thenReturn(existingBook);

        Book result = bookService.updateBook(id, updateRequest);


        assertEquals("Novo título", result.getTitle());
        assertEquals("Novo autor", result.getAuthor());
        assertEquals("2025", result.getPublicationYear());
        assertEquals("Novo gênero", result.getGenre());
        assertEquals("Nova descrição", result.getDescription());
        assertTrue(result.getAvailable());

        verify(bookRepository, times(1)).save(existingBook);
    }
    @Test
    void whenUpdateBookDuplicateBookException() {
        Long id = 1L;
        Book existingBook = new Book();
        existingBook.setId(id);
        existingBook.setTitle("Título antigo");

        BookUpdateRequest updateRequest = new BookUpdateRequest();
        updateRequest.setTitle("Título duplicado");

        when(bookRepository.findById(id)).thenReturn(Optional.of(existingBook));
        when(bookRepository.existsByTitleAndAuthorAndIdNot("Título duplicado", null, id)).thenReturn(true);

        assertThrows(DuplicateBookException.class, () -> bookService.updateBook(id, updateRequest));
        verify(bookRepository, never()).save(existingBook);
    }
    @Test
    void whenDeleteBookSuccess() {
        Long id = 1L;

        doNothing().when(bookRepository).deleteById(id);

        assertDoesNotThrow(() -> bookService.deleteBook(id));
        verify(bookRepository, times(1)).deleteById(id);
    }
    @Test
    void whenDeleteBookNotFound() {
        Long id = 1L;

        doThrow(new RuntimeException("Id do livro não encontrado")).when(bookRepository).deleteById(id);

        assertThrows(RuntimeException.class, () -> bookService.deleteBook(id));
    }
    @Test
    void whenGetBooksSuccess() {
        Book book1 = new Book();
        Book book2 = new Book();

        when(bookRepository.findAll()).thenReturn(Arrays.asList(book1, book2));

        var result = bookService.getBooks();

        assertNotNull(result);
        assertEquals(2, result.size());
    }
}
