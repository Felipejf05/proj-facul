package com.proj.facul.service;

import com.proj.facul.domain.Book;
import com.proj.facul.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FileServiceTest {
    @Mock
    private BookRepository bookRepository;
    @Mock
    private MultipartFile file;
    @InjectMocks
    private FileService fileService;

    private Book book;
    @BeforeEach
    public void setup() {
        book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");
    }
    @Test
    public void testUploadFile_Success() throws IOException {
        when(bookRepository.findById(any(Long.class))).thenReturn(Optional.of(book));
        when(file.getOriginalFilename()).thenReturn("testfile.txt");
        when(file.getInputStream()).thenReturn(new ByteArrayInputStream("content".getBytes()));

        try (MockedStatic<Files> mockedFiles = mockStatic(Files.class)) {
            Path targetLocation = Paths.get(System.getProperty("user.dir") + "/uploaded_files/" + book.getId());
            mockedFiles.when(() -> Files.exists(targetLocation)).thenReturn(false);
            mockedFiles.when(() -> Files.createDirectories(targetLocation)).thenReturn(null);
            mockedFiles.when(() -> Files.copy((Path) any(), eq(targetLocation.resolve("testfile.txt")))).thenReturn(null);

            String result = fileService.uploadFile(book.getId(), file);

            assertNotNull(result);
            assertEquals(targetLocation.resolve("testfile.txt").toString(), result);
            verify(bookRepository, times(1)).save(book);
        }
    }

    @Test
    public void testUploadFile_BookNotFound() throws IOException {
        when(bookRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        try {
            fileService.uploadFile(book.getId(), file);
        } catch (RuntimeException e) {
            assertEquals("Livro não encontrado", e.getMessage());
        }
    }

    @Test
    public void testDownloadFile_Success() throws IOException {
        book.setFilePath("/path/to/file");
        when(bookRepository.findById(any(Long.class))).thenReturn(Optional.of(book));

        try (MockedStatic<Files> mockedFiles = mockStatic(Files.class)) {
            Path filePath = Paths.get(book.getFilePath());
            mockedFiles.when(() -> Files.exists(filePath)).thenReturn(true);
            mockedFiles.when(() -> Files.readAllBytes(filePath)).thenReturn("file content".getBytes());

            byte[] result = fileService.downloadFile(book.getId());

            assertNotNull(result);
            assertArrayEquals("file content".getBytes(), result);
        }
    }

    @Test
    public void testDownloadFile_BookNotFound() throws IOException {
        when(bookRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        try {
            fileService.downloadFile(book.getId());
        } catch (RuntimeException e) {
            assertEquals("Arquivo não encontrado", e.getMessage());
        }
    }

    @Test
    public void testDeleteFile_Success() throws IOException {
        book.setFilePath("/path/to/file");
        when(bookRepository.findById(any(Long.class))).thenReturn(Optional.of(book));

        try (MockedStatic<Files> mockedFiles = mockStatic(Files.class)) {
            Path filePath = Paths.get(book.getFilePath());
            mockedFiles.when(() -> Files.exists(filePath)).thenReturn(true);
            mockedFiles.when(() -> Files.delete(filePath)).thenAnswer(invocation -> null);

            fileService.deleteFile(book.getId());

            verify(bookRepository, times(1)).save(book);
            assertNull(book.getFilePath());
        }
    }


    @Test
    public void testDeleteFile_FileNotFound() throws IOException {
        book.setFilePath(null);
        when(bookRepository.findById(any(Long.class))).thenReturn(Optional.of(book));

        try {
            fileService.deleteFile(book.getId());
        } catch (RuntimeException e) {
            assertEquals("Arquivo não encontrado", e.getMessage());
        }
    }

    @Test
    public void testDeleteFile_FileDoesNotExist() throws IOException {
        book.setFilePath("/path/to/file");
        when(bookRepository.findById(any(Long.class))).thenReturn(Optional.of(book));

        try (MockedStatic<Files> mockedFiles = mockStatic(Files.class)) {
            Path filePath = Paths.get(book.getFilePath());
            mockedFiles.when(() -> Files.exists(filePath)).thenReturn(false);

            try {
                fileService.deleteFile(book.getId());
            } catch (RuntimeException e) {
                assertEquals("Arquivo não encontrado no sistema", e.getMessage());
            }
        }
    }
}
