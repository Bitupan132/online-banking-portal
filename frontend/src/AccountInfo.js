// AccountInfo.js
import React, { useEffect, useState } from 'react';
import { Link, useLocation, useNavigate } from "react-router-dom";
import './index.css';
import axios from 'axios';

const AccountInfo = () => {
  const getUserDetailsUrl = "http://localhost:8686/account/getAccountForUsername/";
  const location = useLocation();
  const navigate = useNavigate();
  const { username, token } = location.state || {};
  const [accountData, setAccountData] = useState({
    name: '',
    accountNo: '',
    mobile: '',
    balance: '',
  });

  useEffect(() => {
    const fetchUserDetails = async () => {
      let response = {};
      try {
        const response = await axios.get(getUserDetailsUrl + username, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        setAccountData(prevState => ({
          ...prevState,
          name: response.data.fullName,
          accountNo: response.data.accountNo,
          mobile: response.data.phoneNo,
          balance: response.data.balance
        }));
      } catch (error) {
        alert(error.response.data)
        console.log(error.message)
        // error.response.data==="INVALID_TOKEN"
        // Handle the error (e.g., show a message to the user)
      }

    };
    fetchUserDetails();
  }, [username, token]);

  const goToMakeTranasaction = ()=>{
    navigate('/make_transaction', {
      state: {
        senderAccountNo: accountData.accountNo, 
        token: token,
        username:username,
      },
    });
  }

  const applyLoan = ()=>{
    navigate('/apply_loan',{
      state:{
        bankAccountNo: accountData.accountNo,
        token:token
      },
    });
  }

  const viewTransaction = ()=>{
    navigate('/view_transaction',{
      state:{
        currentAccountNo: accountData.accountNo,
        token:token
      },
    });
  }

  const viewLoan = ()=>{
    navigate('/view_loan',{
      state:{
        bankAccountNo: accountData.accountNo,
        token:token
      },
    });
  }

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setAccountData({ ...accountData, [name]: value });
  };

  const handleReset = () => {
    setAccountData({
      name: '',
      accountNo: '',
      mobile: '',
      balance: '',
    });
  };

  const handleSave = () => {
    console.log('Account Data:', accountData);
    // Add save logic here (connect to backend, update state, etc.)
  };

  const handleDeposit = () => {
    // Add deposit logic here
    console.log('Deposit button clicked');
  };

  const handleWithdraw = () => {
    // Add withdraw logic here
    console.log('Withdraw button clicked');
  };

  return (
    <div className="account-info-container">
      <h2>Account Information</h2>
      <form className="account-form">
      <div>
          <strong><label>Name: </label></strong>
          <span>{accountData.name}</span>
        </div>
        <br />

        <div>
          <strong>  <label>Account Number: </label></strong>
          <span>{accountData.accountNo}</span>
        </div>
        <br />

        <div>
          <strong><label>Mobile: </label> </strong>
          <span>{accountData.mobile}</span>
        </div>
        <br />

        <div>
          <strong><label>Balance: </label> </strong>
          <span>{accountData.balance}</span>
        </div>
        <br />

        

        <div className="button-container">

          <button type="button" onClick={goToMakeTranasaction} key="make-transaction">Make Transaction</button>

          <button type="button" onClick={viewTransaction} key="view-transaction">View Transaction</button>

          <button type="button" onClick={applyLoan} key="apply-Loan">Apply Loan</button>

         <button type="button" onClick={viewLoan} key="view-loan">View Loan</button>

        </div>
      </form>
    </div>
  );
};

export default AccountInfo;
