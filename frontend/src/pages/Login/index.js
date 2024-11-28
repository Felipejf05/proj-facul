import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import './styles.css';
import logoImage from '../../assets/fundo_livros.png';

const api = axios.create({
  baseURL: 'http://localhost:8080/v1/users',
  headers: {
    'Content-Type': 'application/json',
  },
});

export default function Login() {
  const [user, setUser] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();

    if (!user || !password) {
      alert('Por favor, preencha todos os campos!');
      return;
    }

    try {
      console.log('Enviando dados:', { user, password });
      const response = await api.post('/login', { user, password });
      console.log('Resposta do backend:', response.data);
      alert(`Bem-vindo, ${response.data.name}!`);
      navigate('/books/add');
    } catch (err) {
      console.error('Erro ao fazer login:', err);
      if (err.response) {
        alert(`Erro: ${err.response.data.message || 'Falha no login.'}`);
      } else {
        alert('Erro ao conectar-se ao servidor. Tente novamente mais tarde.');
      }
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

export function Register() {
  const [name, setName] = useState('');
  const [email, setEmail] = useState('');
  const [document, setDocument] = useState('');
  const [birthday, setBirthday] = useState('');
  const [phone, setPhone] = useState('');
  const [address, setAddress] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const navigate = useNavigate();

  const handleRegister = async (e) => {
    e.preventDefault();

    if (password !== confirmPassword) {
      alert('As senhas não coincidem!');
      return;
    }

    if (document.length !== 11) {
      alert('O CPF deve conter 11 dígitos');
      return;
    }

    const data = {
      name,
      email,
      document,
      birthday,
      phone,
      address,
      password,
    };

    try {
      console.log('Enviando dados de registro:', data);
      const response = await api.post('/', data);
      console.log('Registro bem-sucedido:', response.data);
      alert('Cadastro realizado com sucesso! Faça login para continuar.');
      navigate('/');
    } catch (err) {
      console.error('Erro ao registrar:', err);
      if (err.response) {
        alert(`Erro: ${err.response.data.message || 'Falha no registro!'}`);
      } else {
        alert('Erro ao conectar-se ao servidor. Tente novamente mais tarde.');
      }
    }
  };

  return (
    <div className="register-container">
      <h1>Registro de Usuário</h1>
      <form onSubmit={handleRegister}>
        <input
          type="text"
          placeholder="Nome"
          value={name}
          onChange={(e) => setName(e.target.value)}
        />
        <input
          type="email"
          placeholder="E-mail"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
        />
        <input
          type="text"
          placeholder="CPF (apenas números)"
          value={document}
          onChange={(e) => setDocument(e.target.value.replace(/\D/g, ''))}
        />
        <input
          type="date"
          placeholder="Data de Nascimento"
          value={birthday}
          onChange={(e) => setBirthday(e.target.value)}
        />
        <input
          type="text"
          placeholder="Telefone"
          value={phone}
          onChange={(e) => setPhone(e.target.value)}
        />
        <input
          type="text"
          placeholder="Endereço"
          value={address}
          onChange={(e) => setAddress(e.target.value)}
        />
        <input
          type="password"
          placeholder="Senha"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />
        <input
          type="password"
          placeholder="Confirmar Senha"
          value={confirmPassword}
          onChange={(e) => setConfirmPassword(e.target.value)}
        />
        <button className="button" type="submit">
          Registrar
        </button>
      </form>
    </div>
  );
}
