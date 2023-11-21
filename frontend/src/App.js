// App.js
import React from 'react';
// import { BrowserRouter as Router, Route, Link } from 'react-router-dom';
// import SignupForm from './Sign';
import { Routes, Route } from 'react-router-dom';
import LoginForm from './LoginForm';
import SignupForm from './SignUpForm';
import AccountInfo from './AccountInfo';

import TransactionForm from './TransactionForm';
import ViewTransaction from './ViewTransaction';
import LoanApplication from './LoanApplication';

const App = () => {
  return (
  
  <div >
  
  <Routes>
    <Route path="/" element={<LoginForm />}></Route>
    <Route path="/signup" element={<SignupForm />}></Route>
    <Route path="/account" element={<AccountInfo />}></Route>
    <Route path="/make_transaction" element={<TransactionForm />}></Route>
    <Route path="/view_transaction" element={<ViewTransaction />}></Route>
    <Route path="/applyloan" element={<LoanApplication />}></Route>
    

    
  </Routes>
  {/* <ViewTransaction/> */}
  {/* <AccountInfo/> */}
  {/* <LoginForm/> */}
  {/* <TransactionForm/> */}
  {/* <LoanApplication/> */}
</div>
  );
};

export default App;
