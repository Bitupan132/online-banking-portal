// LoginForm.js
import React, { useState } from 'react';
import { Link } from "react-router-dom";
import './index.css'; // Import the stylesheet
import axios from 'axios';


const SignupForm = ({ onToggleForm }) => {

  const signup_endpoint = "http://localhost:8989/auth/register";

  const [signupData, setSignupData] = useState({ username: '', password: '', account: '' });

  const handleSignupSubmit = (e) => {
    e.preventDefault();
    console.log('Signup submitted:', signupData);
    // Add authentication logic here (connect to backend)
    axios.post(signup_endpoint, { account: signupData.account, username: signupData.username, password: signupData.password })
      .then((res)=>console.log(res.data))
      .catch((e) => console.error(e));
  };

  return (
    <div className="login-form-container">
      <h2>Signup</h2>
      <form onSubmit={handleSignupSubmit}>
        <label>Username:</label>
        <input
          type="text"
          value={signupData.username}
          onChange={(e) => setSignupData({ ...signupData, username: e.target.value })}
        />

        <label>Password:</label>
        <input
          type="password"
          value={signupData.password}
          onChange={(e) => setSignupData({ ...signupData, password: e.target.value })}
        />
        <label>Account Number:</label>
        <input
          type="text"
          value={signupData.account}
          onChange={(e) => setSignupData({ ...signupData, account: e.target.value })}
        />

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
