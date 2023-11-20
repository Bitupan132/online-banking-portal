// LoanApplication.js
import React, { useState } from 'react';
import './index.css';

const LoanApplication = () => {
  const [loanData, setLoanData] = useState({
    applicantName: '',
    loanAmount: '',
    purpose: '',
  });
  const [viewedLoans, setViewedLoans] = useState([]);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setLoanData({ ...loanData, [name]: value });
  };

  const handleReset = () => {
    setLoanData({
      applicantName: '',
      loanAmount: '',
      purpose: '',
    });
  };

  const handleSend = () => {
    if (loanData.applicantName && loanData.loanAmount && loanData.purpose) {
      const newLoan = {
        id: Date.now(),
        applicantName: loanData.applicantName,
        loanAmount: parseFloat(loanData.loanAmount),
        purpose: loanData.purpose,
      };

      setViewedLoans([...viewedLoans, newLoan]);
      handleReset();
    } else {
      alert('Please enter all required information.');
    }
  };

  const handleDelete = (id) => {
    const updatedLoans = viewedLoans.filter((loan) => loan.id !== id);
    setViewedLoans(updatedLoans);
  };

  return (
    <div className="loan-application-container">
      <h2>Apply for a Loan</h2>
      <form className="loan-application-form">
        <label>Applicant Name:</label>
        <input
          type="text"
          name="applicantName"
          value={loanData.applicantName}
          onChange={handleInputChange}
        />

        <label>Loan Amount:</label>
        <input
          type="number"
          name="loanAmount"
          value={loanData.loanAmount}
          onChange={handleInputChange}
        />

        <label>Purpose:</label>
        <input
          type="text"
          name="purpose"
          value={loanData.purpose}
          onChange={handleInputChange}
        />

        <div className="button-container">
          <button type="button" onClick={handleReset}>
            Reset
          </button>
          <button type="button" onClick={handleSend}>
            Send
          </button>
        </div>
      </form>

      <div className="viewed-loans">
        <h2>Viewed Loans</h2>
        <ul>
          {viewedLoans.map((loan) => (
            <li key={loan.id}>
              <span>Applicant Name: {loan.applicantName}</span>
              <span>Loan Amount: ${loan.loanAmount}</span>
              <span>Purpose: {loan.purpose}</span>
              <button onClick={() => handleDelete(loan.id)}>Delete</button>
            </li>
          ))}
        </ul>
      </div>
    </div>
  );
};

export default LoanApplication;
