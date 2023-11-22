// LoginForm.js
import React, { useState } from 'react';
import { Link } from "react-router-dom";
import '../css/login.css';
import axios from 'axios';


const SignupForm = ({ onToggleForm }) => {

  const signup_endpoint = "http://localhost:8989/auth/register";

  const [signupData, setSignupData] = useState({ username: '', password: '', account: '' });

  const handleSignupSubmit = (e) => {
    e.preventDefault();
    console.log('Signup submitted:', signupData);
    axios.post(signup_endpoint, { account: signupData.account, username: signupData.username, password: signupData.password })
      .then((res)=>alert(res.data))
      .catch((e) => alert(e.response.data))
  };

  return (
    <div className="login-form-container">
      <h2>Signup</h2>
      <form className='card' onSubmit={handleSignupSubmit}>
        <label>Username:</label>
        <input
          type="text"
          value={signupData.username}
          placeholder='Enter Username'
          onChange={(e) => setSignupData({ ...signupData, username: e.target.value })}
        />

        <label>Password:</label>
        <input
          type="password"
          value={signupData.password}
          placeholder='Enter Password'
          onChange={(e) => setSignupData({ ...signupData, password: e.target.value })}
        />
        <label>Account Number:</label>
        <input
          type="text"
          value={signupData.account}
          placeholder='Enter Account Number'
          onChange={(e) => setSignupData({ ...signupData, account: e.target.value })}
        />
        <br></br>
        <button type="submit">Signup</button>
      </form>

      <p>
        <Link to="/" href="#" className='link'><button type="submit" > GoToLogin </button>

        </Link>

      </p>
    </div>
  );
};

export default SignupForm;
