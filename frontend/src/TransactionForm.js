// TransactionForm.js
import React, { useState } from 'react';
import './index.css';
import { useLocation, useNavigate } from 'react-router-dom';
import axios from 'axios';

const TransactionForm = () => {
  const transactionServiceUrl = "http://localhost:8081/transaction";
  const navigate = useNavigate();
  const location = useLocation();
  const { senderAccountNo, token, username } = location.state || {};
  const [transactionData, setTransactionData] = useState({
    receiverAccountNo: '',
    amount: '',
    summary: ''
  });

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setTransactionData({ ...transactionData, [name]: value });
  };

  const handleReset = () => {
    setTransactionData({
      receiverAccountNo: '',
      amount: '',
      summary: ''
    });
  };

  const handleSend = async () => {
    if (transactionData.receiverAccountNo && transactionData.amount) {
      await axios.post(transactionServiceUrl + "/add", {
        senderAccountNo: senderAccountNo,
        receiverAccountNo: transactionData.receiverAccountNo,
        amount: transactionData.amount,
        summary: transactionData.summary
      }, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
        .then(res => {
          if (res.data === "INVALID_TOKEN") {
            alert("Session Expired. Please Login again.");
            navigate('/');
          }
          else {
            alert(res.data)
            navigate('/account', {
              state: { username, token },
            });
          }
        })
        .catch((error) => {console.log("error");alert(error.response.data)});
      handleReset();
    } else {
      alert('Please enter both all required information.');
    }
  };

  return (
    <div className="transaction-form-container">
      <h2>Make Transaction</h2>
      <form className="transaction-form">
        <label>Account No:</label>
        <input
          type="text"
          name="receiverAccountNo"
          placeholder='Enter 12 digit account no.'
          value={transactionData.receiverAccountNo}
          onChange={handleInputChange}
        />

        <label>Amount:</label>
        <input
          type="number"
          name="amount"
          value={transactionData.amount}
          onChange={handleInputChange}
        />

        <label>Summary:</label>
        <input
          type="text"
          name="summary"
          value={transactionData.summary}
          onChange={handleInputChange}
        />

        <div className="button-container">
          <button type="button" onClick={handleReset}>
            Reset Details
          </button>
          <button type="button" onClick={handleSend}>
            Send Money
          </button>
        </div>
      </form>
    </div>
  );
};

export default TransactionForm;
