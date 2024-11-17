import React, { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { FiPower, FiEdit, FiTrash2 } from 'react-icons/fi';
import api from '../../services/api';
import './styles.css';
import logoImage from '../../assets/logo.svg';

export default function Books() {
  const [books, setBooks] = useState([]);
  const [page, setPage] = useState(1);

  const name = localStorage.getItem('name');
  const accessToken = localStorage.getItem('accessToken');
  const navigate = useNavigate();


  async function logout() {
    localStorage.clear();
    navigate('/');
  }


  async function editBook(id) {
    try {
      navigate(`/book/new/${id}`);
    } catch (error) {
      alert('Falha ao editar o livro! Tente novamente.');
    }
  }

  async function deleteBook(id) {
    try {
      await api.delete(`/api/book/v1/${id}`, {
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
      });

      setBooks(books.filter(book => book.id !== id));
    } catch (err) {
      alert('Falha ao excluir o livro! Tente novamente.');
    }
  }

  async function fetchMoreBooks() {
    const response = await api.get('/api/book/v1', {
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
      params: {
        page: page,
        limit: 4,
        direction: 'asc',
      },
    });

    setBooks([...books, ...response.data._embedded.bookVoes]);
    setPage(page + 1);
  }

  useEffect(() => {
    fetchMoreBooks();
  }, []);

  return (
    <div className="book-container">
      <header>
        <img src={logoImage} alt="Jf" />
        <span>Bem-vindo, <strong>{username.toUpperCase()}</strong>!</span>
        <Link className="button" to="/book/new/0">Adicionar Novo Livro</Link>
        <button onClick={logout} type="button">
          <FiPower size={18} color="#251FC5" />
        </button>
      </header>

      <h1>Livros Cadastrados</h1>
      <ul>
        {books.map(book => (
          <li key={book.id}>
            <strong>Título:</strong>
            <p>{book.title}</p>
            <strong>Autor:</strong>
            <p>{book.author}</p>
            <strong>Gênero:</strong>
            <p>{book.genre}</p>
            <strong>Descrição:</strong>
            <p>{book.description}</p>
            <strong>Disponibilidade:</strong>
            <p>{book.available ? 'Disponível' : 'Indisponível'}</p>

            <button onClick={() => editBook(book.id)} type="button">
              <FiEdit size={20} color="#251FC5" />
            </button>

            <button onClick={() => deleteBook(book.id)} type="button">
              <FiTrash2 size={20} color="#251FC5" />
            </button>
          </li>
        ))}
      </ul>

      <button className="button" onClick={fetchMoreBooks} type="button">Carregar Mais</button>
    </div>
  );
}
