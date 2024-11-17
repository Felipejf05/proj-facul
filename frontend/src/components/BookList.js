import React, { useEffect, useState } from "react";
import BookService from "../services/BookService";

const BookList = () => {
  const [books, setBooks] = useState([]);

  useEffect(() => {
    BookService.getAllBooks()
      .then((response) => setBooks(response.data))
      .catch((error) => console.error("Erro ao buscar livros:", error));
  }, []);

  return (
    <div>
      <h2>Livros</h2>
      <ul>
        {books.map((book) => (
          <li key={book.id}>
            {book.title} - {book.author}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default BookList;
