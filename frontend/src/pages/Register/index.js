import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../../services/api';
import './styles.css';

export default function Register() {
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
      alert('Erro: As senhas não coincidem. Verifique e tente novamente.');
      return;
    }

    if (document.length !== 11) {
      alert('Erro: O CPF deve conter exatamente 11 dígitos.');
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
      const response = await api.post('/v1/users', data);
      console.log("Registro bem-sucedido:", response.data);
      alert(`Usuário registrado com sucesso! Bem-vindo(a), ${name}!`);
      navigate('/');
    } catch (err) {
      console.error('Erro ao registrar:', err);

      if (err.response) {
        if (err.response.status === 400) {
          alert('Erro: Dados inválidos. Verifique as informações e tente novamente.');
        } else if (err.response.status === 409) {
          alert('Erro: Este e-mail ou CPF já está em uso. Tente usar outros dados.');
        } else {
          alert('Erro: Não foi possível registrar o usuário. Por favor, tente novamente mais tarde.');
        }
      } else {
        alert('Erro: Falha ao conectar ao servidor. Verifique sua conexão com a internet.');
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
          onChange={(e) => setDocument(e.target.value)}
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
