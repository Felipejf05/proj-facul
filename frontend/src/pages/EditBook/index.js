import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import axios from 'axios';

export default function EditBook() {
  const [book, setBook] = useState(null);
  const [title, setTitle] = useState('');
  const [author, setAuthor] = useState('');
  const [publicationYear, setPublicationYear] = useState('');
  const [genre, setGenre] = useState('');
  const [description, setDescription] = useState('');
  const [available, setAvailable] = useState(true);

  const { id } = useParams();
  const navigate = useNavigate();

  useEffect(() => {
    axios.get(`/books/${id}`)
      .then((response) => {
        setBook(response.data);
        setTitle(response.data.title);
        setAuthor(response.data.author);
        setPublicationYear(response.data.publicationYear);
        setGenre(response.data.genre);
        setDescription(response.data.description);
        setAvailable(response.data.available);
      })
      .catch((error) => {
        console.error('Erro ao buscar livro', error);
      });
  }, [id]);

  const handleSubmit = (e) => {
    e.preventDefault();

    const updatedBook = {
      title,
      author,
      publicationYear,
      genre,
      description,
      available,
    };

    axios.put(`/books/${id}`, updatedBook)
      .then((response) => {
        console.log('Livro atualizado com sucesso:', response);
        navigate('/books');
      })
      .catch((error) => {
        console.error('Erro ao atualizar livro', error);
        alert('Erro ao atualizar livro! Tente novamente.');
      });
  };

  // Se o livro ainda não foi carregado
  if (!book) return <div>Carregando...</div>;

  return (
    <div className="edit-book-container">
      <h1>Editar Livro</h1>
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
          type="text"
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
        <button type="submit">Salvar</button>
      </form>
    </div>
  );
}
