import React, { useState} from 'react';
import { Link, useNavigate} from "react-router-dom";
import './index.css';
import  axios  from 'axios';


const LoginForm = ({ onToggleForm }) => {
   
  const postTokenEndpoint= "http://localhost:8989/auth/token";

  const navigate = useNavigate();

  const [loginData, setLoginData] = useState({ username: '', password: '' });
  const [errorMessage, setErrorMessage] = useState('');

  const handleLoginSubmit = (e) => {
    e.preventDefault();
    login(loginData);
  };
  const login = async (loginData) => {
    try {
      const response = await axios.post(postTokenEndpoint, {
        username: loginData.username,
        password: loginData.password,
      });
  
      navigate('/account', {
        state: { username: loginData.username, token: response.data },
      });
    } catch (error) {
      setErrorMessage('Invalid credentials. Please try again.');
    }
  };
  
  return (
    <div className="login-form-container">
    <h2>Login</h2>
    <form onSubmit={handleLoginSubmit}>
      <label>Username:</label>
      <input
        type="text"
        value={loginData.username}
        onChange={(e) => setLoginData({ ...loginData, username: e.target.value })}
      />

      <label>Password:</label>
      <input
        type="password"
        value={loginData.password}
        onChange={(e) => setLoginData({ ...loginData, password: e.target.value })}
      />

      <button type="submit">Login</button>
    </form>
    {errorMessage && <p className="error-message">{errorMessage}</p>}
    <p>
      Don't have an account?{' '}
      <Link to="/signup" href="#">
        Signup
      </Link>
    </p>
  </div>
  );
};

export default LoginForm;
