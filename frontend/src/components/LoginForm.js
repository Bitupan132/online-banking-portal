import React, { useState} from 'react';
import { Link, useNavigate} from "react-router-dom";
import  axios  from 'axios';
import '../css/login.css'
import { useToken } from './TokenProvider';

const LoginForm = ({ onToggleForm }) => {
   
  const postTokenEndpoint= "http://localhost:8989/auth/token";

  const navigate = useNavigate();
  const { token, updateToken } = useToken();
  const [loginData, setLoginData] = useState({ username: '', password: '' });
  const [errorMessage, setErrorMessage] = useState('');

  const handleLoginSubmit = (e) => {
    e.preventDefault();
    login(loginData);
  };
  const login = async (loginData) => {
    try {
      if(loginData.username!=='' && loginData.password!==''){
        const response = await axios.post(postTokenEndpoint, {
          username: loginData.username,
          password: loginData.password,
      });
        await updateToken(response.data);
      navigate('/account', {
        state: { username: loginData.username},
      });
    }else{
      alert("Please enter the details");
    }
    } catch (error) {
      setErrorMessage('Invalid credentials. Please try again.');
    }
  };
  
  return (
    <div className="login-form-container">
    <h2>Login</h2>
    <form className='card' onSubmit={handleLoginSubmit}>
      <label>Username:</label>
      <input
        type="text"
        value={loginData.username}
        placeholder='Username'
        onChange={(e) => setLoginData({ ...loginData, username: e.target.value })}
      />

      <label>Password:</label>
      <input
        type="password"
        value={loginData.password}
        placeholder='Password'
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
