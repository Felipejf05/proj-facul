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
  const [file, setFile] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!title || !author || !publicationYear || !genre || !description) {
      alert("Por favor, preencha todos os campos obrigatórios!");
      return;
    }

    const formData = new FormData();
    formData.append('title', title);
    formData.append('author', author);
    formData.append('publicationYear', publicationYear);
    formData.append('genre', genre);
    formData.append('description', description);
    formData.append('available', available);

    if (file) {
      formData.append('file', file);
    }

    setLoading(true);
    setError(null);

    try {
      const response = await axios.post('http://localhost:8080/v1/books', formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
        }
      });

      alert('Livro adicionado com sucesso!');
      navigate('/books');

    } catch (err) {
      console.error('Erro ao adicionar livro:', err);

      if (err.response && err.response.status === 400) {
        setError('Este livro já existe. Tente outro título e autor.');
      } else {
        setError('Erro ao adicionar o livro! Tente novamente.');
      }
    } finally {
      setLoading(false);
    }
  };


  const handleFileChange = (e) => {
    setFile(e.target.files[0]);
  };

  return (
    <div className="add-book-container">
      <h1>Adicionar Livro</h1>

      {}
      {error && <div className="error-message">{error}</div>}

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
        <div>
          <label>
            Upload de Arquivo:
            <input
              type="file"
              onChange={handleFileChange}
            />
          </label>
        </div>

        <button type="submit" disabled={loading}>
          {loading ? 'Enviando...' : 'Salvar'}
        </button>
      </form>
    </div>
  );
}
