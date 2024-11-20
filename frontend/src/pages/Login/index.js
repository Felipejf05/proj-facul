import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './styles.css';
import logoImage from '../../assets/fundo_livros.png';

export default function Login() {
  const [user, setUser] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();

    const data = {
      user,
      password,
    };

    try {
      console.log("Login data:", data);
      navigate('/books');
    } catch (err) {
      alert('Login failed! Try again!');
    }
  };

  const goToRegister = () => {
    navigate('/register');
  };

  return (
    <div className="login-container" style={{ backgroundImage: `url(${logoImage})` }}>
      <h1>Conecte-se para publicar o seu livro 📚</h1>
      <form onSubmit={handleLogin}>
        <input
          type="text"
          placeholder="CPF ou E-mail"
          value={user}
          onChange={(e) => setUser(e.target.value)}
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
