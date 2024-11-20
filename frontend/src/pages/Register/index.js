import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../../services/api';

export default function Register() {
  const [name, setName] = useState('');
  const [email, setEmail] = useState('');
  const [document, setDocument] = useState('');
  const [birthday, setBirthday] = useState('');
  const [phone, setPhone] = useState('');
  const [address, setAddress] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();

  const handleRegister = async (e) => {
    e.preventDefault();

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
      // Fazendo a requisição para o backend
      const response = await api.post('/v1/users', data); // Substitua a URL com a URL da sua API
      console.log("Registro bem-sucedido:", response.data);
      navigate('/'); // Navegar para a página de login após o registro
    } catch (err) {
      console.error('Erro ao registrar:', err);
      alert('Falha no registro! Tente novamente.');
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
          placeholder="Email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
        />
        <input
          type="text"
          placeholder="Documento"
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
        <button className="button" type="submit">
          Registrar
        </button>
      </form>
    </div>
  );
}
