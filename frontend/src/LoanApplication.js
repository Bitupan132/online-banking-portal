import React, { useState } from 'react';
import './index.css';
import { useLocation } from 'react-router-dom';
import axios from 'axios';


const LoanApplication = () => {
  const loanServiceUrl = "http://localhost:8012/loan";
  const location = useLocation();
  const { bankAccountNo, token } = location.state || {};
  const [selectedLoan, setSelectedLoan] = useState('');
  let loanType = 0;
  const loanOptions = ['Personal Loan', 'Vehicle Loan', 'Home Loan'];
  const [loanData, setLoanData] = useState({
    loanAmount: '',
    purpose: '',
  });

  const handleLoanChange = (event) => {
    setSelectedLoan(event.target.value);
    switch (event.target.value) {
      case 'Personal Loan':
        loanType = 0;
        break;
      case 'Home Loan':
        loanType = 2;
        break;
      case 'Vehicle Loan':
        loanType = 1;
        break;
      default:
        loanType = 4;
        break;
    }

    console.log(loanType);
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setLoanData({ ...loanData, [name]: value });
  };

  const handleReset = () => {
    setLoanData({
      loanAmount: '',
      purpose: '',
    });
  };

  const handleSend = async () => {
    if (loanData.loanAmount && loanData.purpose) {
      await axios.post(loanServiceUrl + "/applyForLoan", {
        accountNo: bankAccountNo,
        loanAmount: loanData.loanAmount,
        loanType: loanType,
      }, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
        .then(res => alert("Loan Applied Successfuilly. Loan Application id:"+ res.data.loanId))
        .catch((e) => alert(e.message));
      handleReset();
    } else {
      alert('Please enter all required information');
    }
  };

  return (
    <div className="loan-application-container">
      <h2>Apply for a Loan</h2>
      <form className="loan-application-form">
        <label>Required Loan Amount:</label>
        <input
          type="number"
          name="loanAmount"
          value={loanData.loanAmount}
          onChange={handleInputChange}
          placeholder='1000000'
        />

        <label>Select Loan Type:</label>
        <select value={selectedLoan} onChange={handleLoanChange}>
          <option value="">Select...</option>
          {loanOptions.map((option, index) => (
            <option key={index} value={option}>
              {option}
            </option>
          ))}
        </select>

        <label>Purpose:</label>
        <input
          type="text"
          name="purpose"
          value={loanData.purpose}
          onChange={handleInputChange}
          placeholder='reason for applying loan...'
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
    </div>
  );
};

export default LoanApplication;
