import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './styles.css';

export default function Login() {
  const [name, setName] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();

    const data = {
      name,
      password,
    };

    try {
      console.log("Login data:", data);
      // Aqui seria ideal validar a autenticação no backend
      // Se a autenticação for bem-sucedida:
      navigate('/books'); // Navegar para a página de livros após login bem-sucedido
    } catch (err) {
      alert('Login failed! Try again!');
    }
  };

  const goToRegister = () => {
    navigate('/register');
  };

  return (
    <div className="login-container">
      <h1>Conecte-se para publicar o seu livro 📚</h1>
      <form onSubmit={handleLogin}>
        <input
          type="text"
          placeholder="Nome de usuário"
          value={name}
          onChange={(e) => setName(e.target.value)}
        />
        <input
          type="password"
          placeholder="Senha"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />
        <button className="button" type="submit">
          Conecte-se
        </button>
        <button
          className="secondary-button"
          onClick={goToRegister}
        >
          Não tem uma conta? Cadastre-se
        </button>
      </form>
    </div>
  );
}
