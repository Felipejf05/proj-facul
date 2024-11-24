import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import './styles.css';
import BookCard from './bookCard';

export default function Books() {
  const [books, setBooks] = useState([]);
  const navigate = useNavigate();

  const apiUrl = 'http://localhost:8080/v1/list-books';

  const fetchBooks = async () => {
    try {
      const response = await axios.get(apiUrl);
      console.log(response.data);

      if (response.data && Array.isArray(response.data.books)) {
        setBooks(response.data.books);
      } else {
        console.error('A resposta não contém livros:', response.data);
        alert('Erro ao carregar livros!');
      }
    } catch (error) {
      console.error('Erro ao buscar livros:', error);
      alert('Erro ao carregar a lista de livros!');
    } finally {
      setLoading(false);
    }
  };

  const goToAddBook = () => {
    navigate('/books/add');
  };

  const handleDelete = (id) => {
    axios.delete(`/books/${id}`)
      .then(() => {
        setBooks(books.filter((book) => book.id !== id));
      })
      .catch((error) => {
        console.error('Erro ao excluir livro', error);
      });
  };

  return (
    <div className="books-container">
      <h1>Lista de Livros</h1>
      <button onClick={goToAddBook} className="add-book-button">
        Adicionar Livro
      </button>
      <div className="book-list">
        {books.map((book) => (
          <BookCard key={book.id} book={book} onDelete={handleDelete} />
        ))}
      </div>
    </div>
  );
}
