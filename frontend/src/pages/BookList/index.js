import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';
import { FaSignOutAlt } from 'react-icons/fa';
import logoImage from '../../assets/por-do-sol.png';
import './styles.css';

export default function BookList() {
  const [books, setBooks] = useState([]);
  const [loading, setLoading] = useState(true);
  const [search, setSearch] = useState('');

  const fetchBooks = async () => {
    try {
      const response = await axios.get('http://localhost:8080/v1/list-books');
      if (response && response.data && Array.isArray(response.data.data)) {
        setBooks(response.data.data);
      } else {
        console.error('Erro ao carregar livros! Estrutura inesperada');
        alert('Erro ao carregar livros!');
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

  const handleSearchChange = (e) => {
    setSearch(e.target.value);
  };

  const filteredBooks = books.filter((book) => {
    const lowerSearch = search.toLowerCase();
    return (
      book.title.toLowerCase().includes(lowerSearch) ||
      book.author.toLowerCase().includes(lowerSearch)
    );
  });

  const handleDownload = (id) => {
    axios.get(`http://localhost:8080/v1/books/${id}/download`, { responseType: 'blob' })
      .then((response) => {
        const url = window.URL.createObjectURL(new Blob([response.data]));
        const a = document.createElement('a');
        a.href = url;
        a.download = `book_${id}.pdf`;
        document.body.appendChild(a);
        a.click();
      })
      .catch((error) => {
        console.error('Erro ao baixar o arquivo:', error);
        alert('Erro ao baixar o arquivo!');
      });
  };

  const handleDelete = (id) => {
    axios.delete(`http://localhost:8080/v1/books/${id}`)
      .then(() => {
        setBooks(books.filter((book) => book.id !== id));
        alert('Livro excluído com sucesso!');
      })
      .catch((error) => {
        console.error('Erro ao excluir livro', error);
        alert('Erro ao excluir livro');
      });
  };

  if (loading) {
    return <div>Carregando livros...</div>;
  }

  return (
    <div className="book-list-container">
      <h1>Lista de Livros</h1>

      <input
        type="text"
        placeholder="Pesquise por título ou autor..."
        value={search}
        onChange={handleSearchChange}
        className="search-input"
      />

      <Link to="/books/add" className="add-book-button">Adicionar Novo Livro</Link>

      {}
      <div className="back-to-login-container">
        <Link to="/" className="back-to-login-button">
          <FaSignOutAlt size={24} /> {}
        </Link>
      </div>

      <table className="book-list-table">
        <thead>
          <tr>
            <th>Título</th>
            <th>Autor</th>
            <th>Ano de Publicação</th>
            <th>Gênero</th>
            <th>Disponível</th>
            <th>Ações</th>
          </tr>
        </thead>
        <tbody>
          {filteredBooks.length === 0 ? (
            <tr>
              <td colSpan="6">Nenhum livro encontrado.</td>
            </tr>
          ) : (
            filteredBooks.map((book) => (
              <tr key={book.id}>
                <td>{book.title}</td>
                <td>{book.author}</td>
                <td>{book.publicationYear}</td>
                <td>{book.genre}</td>
                <td>{book.available ? 'Sim' : 'Não'}</td>
                <td>
                  <div className="actions-container">
                    <Link to={`/books/edit/${book.id}`} className="edit-button">
                      Editar
                    </Link>
                    <button onClick={() => handleDelete(book.id)} className="delete-button">
                      <i className="fas fa-trash"></i>
                    </button>
                    {book.filePath && (
                      <button onClick={() => handleDownload(book.id)} className="download-button">
                        Baixar Arquivo
                      </button>
                    )}
                  </div>
                </td>
              </tr>
            ))
          )}
        </tbody>
      </table>
    </div>
  );
}
