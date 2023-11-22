// App.js
import React from 'react';
import { Routes, Route } from 'react-router-dom';
import LoginForm from './components/LoginForm';
import SignupForm from './components/SignUpForm';
import AccountInfo from './components/AccountInfo';

import TransactionForm from './components/TransactionForm';
import ViewTransaction from './components/ViewTransaction';
import LoanApplication from './components/LoanApplication';
import ViewLoan from './components/ViewLoan';

const App = () => {
  return (
  
      <div >
      
      <Routes>
        <Route path="/" element={<LoginForm />}></Route>
        <Route path="/signup" element={<SignupForm />}></Route>
        <Route path="/account" element={<AccountInfo />}></Route>
        <Route path="/make_transaction" element={<TransactionForm />}></Route>
        <Route path="/view_transaction" element={<ViewTransaction />}></Route>
        <Route path="/apply_loan" element={<LoanApplication />}></Route>
        <Route path="/view_loan" element={<ViewLoan />}></Route>
      </Routes>
    
    </div>
  );
};

export default App;
