import React from 'react';
import { useNavigate } from 'react-router-dom';

export default function BookCard({ book, onDelete }) {
  const navigate = useNavigate();

  const goToEditBook = () => {
    navigate(`/books/edit/${book.id}`);
  };

  return (
    <div className="book-card">
      <h3>{book.title}</h3>
      <p>Autor: {book.author}</p>
      <p>Ano de Publicação: {book.publicationYear}</p>
      <button onClick={goToEditBook} className="edit-button">
        Editar
      </button>
      <button onClick={() => onDelete(book.id)} className="delete-button">
        Excluir
      </button>
    </div>
  );
}
