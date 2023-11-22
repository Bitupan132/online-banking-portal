import React, { useState } from 'react';
import '../css/index.css';
import { useLocation, useNavigate } from 'react-router-dom';
import axios from 'axios';


const LoanApplication = () => {
  const loanServiceUrl = "http://localhost:8012/loan";
  const location = useLocation();
  const navigate = useNavigate();
  const { bankAccountNo, token, username } = location.state || {};
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

  const returnHome =()=>{
    navigate('/account', {
        state: {
          token: token,
          username: username,
        },
      });
}

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
        .then(res => {
          if (res.data === "INVALID_TOKEN") {
            alert("Session Expired. Please Login again.");
            navigate('/');
          }
          else {
            alert("Loan Applied Successfuilly. Loan Application id:"+ res.data.loanId)
            navigate('/account', {
              state: { username, token },
            });
          }
        })
        .catch((e) => alert(e.message));
      handleReset();
    } else {
      alert('Please enter all required information');
    }
  };

  return (
    <div className="card">
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
      <div>
          <button onClick={returnHome} style={{ backgroundColor: 'red',margin:'10px' }}>Cancel</button>
      </div>
    </div>
  );
};

export default LoanApplication;
