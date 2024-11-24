import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import './styles.css';

export default function AddBook() {
  const [title, setTitle] = useState('');
  const [author, setAuthor] = useState('');
  const [publicationYear, setPublicationYear] = useState('');
  const [genre, setGenre] = useState('');
  const [description, setDescription] = useState('');
  const [available, setAvailable] = useState(true);
  const navigate = useNavigate();

  const handleSubmit = (e) => {
    e.preventDefault();

    const bookData = {
      title,
      author,
      publicationYear,
      genre,
      description,
      available,
    };

    axios.post('http://localhost:8080/v1/books', bookData)
      .then((response) => {
        console.log('Livro adicionado:', response.data);
        navigate('/books');
      })
      .catch((error) => {
        console.error('Erro ao adicionar livro:', error);
        alert('Erro ao adicionar o livro! Tente novamente.');
      });
  };

  return (
    <div className="add-book-container">
      <h1>Adicionar Livro</h1>
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          placeholder="Título"
          value={title}
          onChange={(e) => setTitle(e.target.value)}
        />
        <input
          type="text"
          placeholder="Autor"
          value={author}
          onChange={(e) => setAuthor(e.target.value)}
        />
        <input
          type="number"
          placeholder="Ano de Publicação"
          value={publicationYear}
          onChange={(e) => setPublicationYear(e.target.value)}
        />
        <input
          type="text"
          placeholder="Gênero"
          value={genre}
          onChange={(e) => setGenre(e.target.value)}
        />
        <textarea
          placeholder="Descrição"
          value={description}
          onChange={(e) => setDescription(e.target.value)}
        ></textarea>
        <div>
          <label>
            Disponível:
            <input
              type="checkbox"
              checked={available}
              onChange={(e) => setAvailable(e.target.checked)}
            />
          </label>
        </div>
        <button type="submit">Salvar</button>
      </form>
    </div>
  );
}
