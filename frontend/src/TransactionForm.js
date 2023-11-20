// TransactionForm.js
import React, { useState } from 'react';
import './index.css';
import { useLocation } from 'react-router-dom';
import axios from 'axios';

const TransactionForm = () => {
  const transactionServiceUrl = "http://localhost:8081/transaction";
  const location = useLocation();
  const { senderAccountNo } = location.state || {};
  const [transactionData, setTransactionData] = useState({
    receiverAccountNo: '',
    amount: '',
    summary: ''
  });
  const [transactionHistory, setTransactionHistory] = useState([]);

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
      const newTransaction = {
        receiverAccountNo: transactionData.receiverAccountNo,
        amount: parseFloat(transactionData.amount),
        summary: transactionData.summary,
      };
      await axios.post(transactionServiceUrl + "/add", {
        senderAccountNo: senderAccountNo,
        receiverAccountNo: transactionData.receiverAccountNo,
        amount: transactionData.amount,
        summary: transactionData.summary
      })
        .then(res => alert(res.data))
        .catch((e) => alert(e.message));
      setTransactionHistory([...transactionHistory, newTransaction]);
      handleReset();
    } else {
      alert('Please enter both "Send To" and "Amount"');
    }
  };

  const handleDelete = (id) => {
    const updatedHistory = transactionHistory.filter((transaction) => transaction.id !== id);
    setTransactionHistory(updatedHistory);
  };

  return (
    <div className="transaction-form-container">
      <h2>Make Transaction</h2>
      <form className="transaction-form">
        <label>Account No:</label>
        <input
          type="text"
          name="receiverAccountNo"
          placeholder='Account No'
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

      <div className="transaction-history">
        <h2>Transaction History</h2>
        <ul>
          {transactionHistory.map((transaction) => (
            <li key={transaction.id}>
              <span>From account No: {senderAccountNo}</span>
              <span>Amount: ${transaction.amount}</span>
              <span>Sent To: {transaction.receiverAccountNo}</span>
              <span>Summary: {transaction.summary}</span>
              <button onClick={() => handleDelete(transaction.id)}>Delete</button>
            </li>
          ))}
        </ul>
      </div>
    </div>
  );
};

export default TransactionForm;
