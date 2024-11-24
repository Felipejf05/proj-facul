import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Login from './pages/Login';
import Register from './pages/Register';
import Books from './pages/Books';
import AddBook from './pages/AddBook';
import EditBook from './pages/EditBook';
import BookList from './pages/BookList';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/books" element={<BookList />} /> {}
        <Route path="/books/add" element={<AddBook />} /> {}
        <Route path="/books/edit/:id" element={<EditBook />} /> {}
      </Routes>
    </Router>
  );
}

export default App;
