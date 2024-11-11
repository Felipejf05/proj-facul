package com.proj.facul.dto.response;

import com.proj.facul.domain.Book;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
@Getter
@AllArgsConstructor
public class BookListResponse {
    private List<Book> data;
}
