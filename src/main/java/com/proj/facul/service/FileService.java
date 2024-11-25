package com.proj.facul.service;

import com.proj.facul.domain.Book;
import com.proj.facul.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FileService {

    private final BookRepository bookRepository;

    private final String fileStorageLocation = System.getProperty("user.dir") + "/uploaded_files";

    public String uploadFile(Long bookId, MultipartFile file) throws IOException {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if (bookOptional.isEmpty()) {
            throw new RuntimeException("Livro não encontrado");
        }

        Book book = bookOptional.get();

        Path targetLocation = Paths.get(fileStorageLocation + "/" + book.getId());
        if (!Files.exists(targetLocation)) {
            Files.createDirectories(targetLocation);
        }

        Path filePath = targetLocation.resolve(file.getOriginalFilename());
        Files.copy(file.getInputStream(), filePath);

        book.setFilePath(filePath.toString());
        bookRepository.save(book);

        return filePath.toString();
    }

    public byte[] downloadFile(Long bookId) throws IOException {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if (bookOptional.isEmpty() || bookOptional.get().getFilePath() == null) {
            throw new RuntimeException("Arquivo não encontrado");
        }

        Book book = bookOptional.get();
        Path filePath = Paths.get(book.getFilePath());

        if (!Files.exists(filePath)) {
            throw new RuntimeException("Arquivo não encontrado no sistema");
        }

        return Files.readAllBytes(filePath);
    }

    public void deleteFile(Long bookId) throws IOException {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if (bookOptional.isEmpty() || bookOptional.get().getFilePath() == null) {
            throw new RuntimeException("Arquivo não encontrado");
        }

        Book book = bookOptional.get();
        Path filePath = Paths.get(book.getFilePath());

        if (Files.exists(filePath)) {
            Files.delete(filePath);
            book.setFilePath(null);
            bookRepository.save(book);
        } else {
            throw new RuntimeException("Arquivo não encontrado no sistema");
        }
    }
}
