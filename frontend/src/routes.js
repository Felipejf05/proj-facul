import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom'; // Importando os componentes de roteamento
import Login from './pages/Login';  // Importe seu componente de Login
import Register from './pages/Register'; // Importe seu componente de Registro
import Books from './pages/Books'; // Importe sua página de livros (pode ser qualquer nome para a sua página de conteúdo)

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Login />} />  {/* Página de Login */}
        <Route path="/register" element={<Register />} /> {/* Página de Cadastro */}
        <Route path="/books" element={<Books />} />  {/* Página de Livros, acessível após o login */}
      </Routes>
    </Router>
  );
}

export default App;
