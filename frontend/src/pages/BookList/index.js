import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';
import './styles.css';

export default function BookList() {
  const [books, setBooks] = useState([]);
  const [loading, setLoading] = useState(true);

  const fetchBooks = async () => {
    try {
      const response = await axios.get('http://localhost:8080/v1/list-books');
      console.log('API:', response);


      if (response && response.data && Array.isArray(response.data.data)) {
        console.log('Livros recebidos:', response.data.data);
        setBooks(response.data.data);
      } else {
        console.error('Estrutura inesperada na resposata:', response.data);
        alert('Erro ao carregar livros! Estrutura inesperada da resposta');
      }
    } catch (error) {
      console.error('Erro ao buscar livros:', error);
      alert('Erro ao carregar a lista de livros!');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchBooks();
  }, []);

  if (loading) {
    return <div>Carregando livros...</div>;
  }

  return (
    <div className="book-list-container">
      <h1>Lista de Livros</h1>
      {}
      <Link to="/books/add" className="add-book-button">Adicionar Novo Livro</Link> {}

      <table className="book-list-table">
        <thead>
          <tr>
            <th>Título</th>
            <th>Autor</th>
            <th>Ano de Publicação</th>
            <th>Gênero</th>
            <th>Disponível</th>
          </tr>
        </thead>
        <tbody>
          {Array.isArray(books) && books.length === 0 ? (
            <tr>
              <td colSpan="5">Nenhum livro encontrado.</td>
            </tr>
          ) : (
            books.map((book) => (
              <tr key={book.id}>
                <td>{book.title}</td>
                <td>{book.author}</td>
                <td>{book.publicationYear}</td>
                <td>{book.genre}</td>
                <td>{book.available ? 'Sim' : 'Não'}</td>
              </tr>
            ))
          )}
        </tbody>
      </table>
    </div>
  );
}
