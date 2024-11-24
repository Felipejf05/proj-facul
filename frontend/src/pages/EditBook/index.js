import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate, useParams } from 'react-router-dom';

export default function EditBook() {
  const [bookData, setBookData] = useState({
    title: '',
    author: '',
    publicationYear: '',
    genre: '',
    description: '',
    available: false,
  });

  const { id } = useParams();
  const navigate = useNavigate();

  useEffect(() => {
    const fetchBookData = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/v1/books/${id}`);
        setBookData(response.data);
      } catch (error) {
        console.error('Erro ao carregar os dados do livro', error);
        alert('Erro ao carregar os dados do livro');
      }
    };

    fetchBookData();
  }, [id]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setBookData((prevState) => ({
      ...prevState,
      [name]: value,
    }));
  };

  const handleUpdateBook = async () => {
    try {
      const response = await axios.put(`http://localhost:8080/v1/books/${id}`, bookData);
      console.log('Livro atualizado com sucesso', response.data);
      navigate('/books');
    } catch (error) {
      console.error('Erro ao atualizar o livro', error);
      alert('Erro ao atualizar o livro!');
    }
  };

  return (
    <div>
      <h2>Editar Livro</h2>
      <form>
        <div>
          <label>Título:</label>
          <input
            type="text"
            name="title"
            value={bookData.title}
            onChange={handleChange}
          />
        </div>
        <div>
          <label>Autor:</label>
          <input
            type="text"
            name="author"
            value={bookData.author}
            onChange={handleChange}
          />
        </div>
        <div>
          <label>Ano de Publicação:</label>
          <input
            type="text"
            name="publicationYear"
            value={bookData.publicationYear}
            onChange={handleChange}
          />
        </div>
        <div>
          <label>Gênero:</label>
          <input
            type="text"
            name="genre"
            value={bookData.genre}
            onChange={handleChange}
          />
        </div>
        <div>
          <label>Descrição:</label>
          <textarea
            name="description"
            value={bookData.description}
            onChange={handleChange}
          />
        </div>
        <div>
          <label>Disponível:</label>
          <input
            type="checkbox"
            name="available"
            checked={bookData.available}
            onChange={() => setBookData((prevState) => ({
              ...prevState,
              available: !prevState.available,
            }))}
          />
        </div>
        <button type="button" onClick={handleUpdateBook}>
          Salvar Alterações
        </button>
      </form>
    </div>
  );
}
