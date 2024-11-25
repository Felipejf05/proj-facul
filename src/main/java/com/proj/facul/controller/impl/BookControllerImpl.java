package com.proj.facul.controller.impl;

import com.proj.facul.controller.BookController;
import com.proj.facul.domain.Book;
import com.proj.facul.dto.request.BookRequest;
import com.proj.facul.dto.request.BookUpdateRequest;
import com.proj.facul.dto.response.BookListResponse;
import com.proj.facul.dto.response.BookResponseDTO;
import com.proj.facul.service.BookService;
import com.proj.facul.service.FileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;

@RestController
@RequiredArgsConstructor
public class BookControllerImpl implements BookController {

    private final BookService bookService;
    private final FileService fileService;

    @Override
    public ResponseEntity<BookListResponse>
    getBooks() {
        final var books = bookService.getBooks();
        final var responseList = new BookListResponse(books);
        return ResponseEntity.ok(responseList);
    }

    @Override
    public ResponseEntity<BookResponseDTO> findById(Long id) {
        Book book = bookService.getBookById(id);
        BookResponseDTO responseDTO = new BookResponseDTO();
        responseDTO.setId(String.valueOf(book.getId()));
        responseDTO.setTitle(book.getTitle());
        responseDTO.setAuthor(book.getAuthor());
        responseDTO.setPublicationYear(String.valueOf((book.getPublicationYear())));
        responseDTO.setGenre(book.getGenre());
        responseDTO.setDescription(book.getDescription());
        responseDTO.setAvailable(book.getAvailable());

        return ResponseEntity.ok(responseDTO);
    }

    @Override
    public ResponseEntity<BookResponseDTO> addBook(@Valid @RequestBody BookRequest bookRequest) throws ParseException {
        Book book = new Book();
        book.setTitle(bookRequest.getTitle());
        book.setAuthor(bookRequest.getAuthor());
        book.setPublicationYear((bookRequest.getPublicationYear()));
        book.setGenre(bookRequest.getGenre());
        book.setDescription(bookRequest.getDescription());
        book.setAvailable(bookRequest.getAvailable());

        Book saveBook = bookService.addBook(book);

        BookResponseDTO responseDTO = new BookResponseDTO();
        responseDTO.setId(String.valueOf(saveBook.getId()));
        responseDTO.setTitle(saveBook.getTitle());
        responseDTO.setAuthor(saveBook.getAuthor());
        responseDTO.setPublicationYear(saveBook.getPublicationYear());
        responseDTO.setGenre(saveBook.getGenre());
        responseDTO.setDescription(saveBook.getDescription());
        responseDTO.setAvailable(saveBook.getAvailable());

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @Override
    public ResponseEntity<BookResponseDTO> updateBook(Long id, @Valid @RequestBody BookUpdateRequest bookUpdateRequest) throws ParseException {

        Book updateBook = bookService.updateBook(id, bookUpdateRequest);

        BookResponseDTO responseDTO = new BookResponseDTO();
        responseDTO.setId(String.valueOf(updateBook.getId()));
        responseDTO.setTitle(updateBook.getTitle());
        responseDTO.setAuthor(updateBook.getAuthor());
        responseDTO.setPublicationYear(updateBook.getPublicationYear());
        responseDTO.setGenre(updateBook.getGenre());
        responseDTO.setDescription(updateBook.getDescription());
        responseDTO.setAvailable(updateBook.getAvailable());

        return ResponseEntity.ok(responseDTO);
    }

    @Override
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        try {
            bookService.deleteBook(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();

        }
    }
    @Override
    public ResponseEntity<String> uploadFile(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            String filePath = fileService.uploadFile(id, file);
            return ResponseEntity.ok("Arquivo enviado com sucesso: " + filePath);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao fazer o upload: " + e.getMessage());
        }
    }


    @Override
    public ResponseEntity<byte[]> downloadFile(@PathVariable Long id) {
        try {
            byte[] fileContent = fileService.downloadFile(id);
            return ResponseEntity.ok(fileContent);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @Override
    public ResponseEntity<Void> deleteFile(@PathVariable Long id) {
        try {
            fileService.deleteFile(id);
            return ResponseEntity.noContent().build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}