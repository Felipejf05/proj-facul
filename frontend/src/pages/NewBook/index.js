import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import api from '../../services/api';
import './styles.css';

export default function NewBook() {
    const [book, setBook] = useState({
        title: '',
        author: '',
        genre: '',
        description: '',
        available: true,
    });

    const { bookId } = useParams();

   const navigate = useNavigate();
   navigate(`/book/new/${id}`);

    useEffect(() => {
        if (bookId !== '0') {
            async function fetchBookData() {
                try {
                    const response = await api.get(`/api/book/v1/${bookId}`);
                    setBook(response.data);
                } catch (err) {
                    alert('Failed to load book data.');
                }
            }
            fetchBookData();
        }
    }, [bookId]);

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setBook({ ...book, [name]: value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            if (bookId === '0') {
                await api.post('/api/book/v1', book);
                alert('Book added successfully!');
            } else {
                await api.put(`/api/book/v1/${bookId}`, book);
                alert('Book updated successfully!');
            }
            navigate.push('/books');
        } catch (err) {
            alert('Error saving book! Try again.');
        }
    };

    return (
        <div className="book-form-container">
            <h1>{bookId === '0' ? 'Add New Book' : 'Edit Book'}</h1>
            <form onSubmit={handleSubmit}>
                <input
                    type="text"
                    name="title"
                    placeholder="Title"
                    value={book.title}
                    onChange={handleInputChange}
                    required
                />
                <input
                    type="text"
                    name="author"
                    placeholder="Author"
                    value={book.author}
                    onChange={handleInputChange}
                    required
                />
                <input
                    type="text"
                    name="genre"
                    placeholder="Genre"
                    value={book.genre}
                    onChange={handleInputChange}
                    required
                />
                <textarea
                    name="description"
                    placeholder="Description"
                    value={book.description}
                    onChange={handleInputChange}
                    required
                ></textarea>
                <div>
                    <label>
                        <input
                            type="checkbox"
                            name="available"
                            checked={book.available}
                            onChange={() => setBook({ ...book, available: !book.available })}
                        />
                        Available
                    </label>
                </div>
                <button type="submit" className="button">{bookId === '0' ? 'Add Book' : 'Update Book'}</button>
            </form>
        </div>
    );
}
