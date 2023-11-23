import React, { useEffect, useState } from 'react';
import { Link, useLocation, useNavigate } from "react-router-dom";
import '../css/AccountInfo.css';
import axios from 'axios';
import { useToken } from './TokenProvider';

const AccountInfo = () => {
  const getUserDetailsUrl = "http://localhost:8686/account/getAccountForUsername/";
  const location = useLocation();
  const navigate = useNavigate();
  const { username } = location.state || {};
  const { token, updateToken } = useToken();
  const [accountData, setAccountData] = useState({
    name: '',
    accountNo: '',
    mobile: '',
    balance: '',
  });

  useEffect(() => {
    const fetchUserDetails = async () => {
      try {
        const response = await axios.get(getUserDetailsUrl + username, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        if (response.data === "INVALID_TOKEN") {
          if (token === "null") {
            navigate('/');
          } else {
            alert("Session Expired. Please Login again.");
            navigate('/');
          }
        }
        else if (response.data === "ACCOUNT_DOES_NOT_EXIST") {
          alert("Account Does not Exist. Please Login again.");
          navigate('/');
        }
        else {
          setAccountData(prevState => ({
            ...prevState,
            name: response.data.fullName,
            accountNo: response.data.accountNo,
            mobile: response.data.phoneNo,
            balance: response.data.balance
          }));
        }

      } catch (error) {
        if (error.response.data.status === 500) {
          alert("Error Occured. Please Login again.");
          navigate('/');

        }
        else {
          alert(error.message)
        }
      }

    };
    fetchUserDetails();
  }, [username, token]);

  const goToMakeTranasaction = () => {
    navigate('/make_transaction', {
      state: {
        senderAccountNo: accountData.accountNo,
        username: username,
      },
    });
  }

  const applyLoan = () => {
    navigate('/apply_loan', {
      state: {
        bankAccountNo: accountData.accountNo,
        username: username,
      },
    });
  }

  const viewTransaction = () => {
    navigate('/view_transaction', {
      state: {
        currentAccountNo: accountData.accountNo,
        username: username,
      },
    });
  }

  const viewLoan = () => {
    navigate('/view_loan', {
      state: {
        bankAccountNo: accountData.accountNo,
        username: username,
      },
    });
  }

  const handleLogout = async () => {
    await updateToken('null');
    navigate('/');

  }

  return (
    <div>
      <div className="button-container-log">
        <button className="logout-button" type="button" onClick={() => { handleLogout() }}>Logout</button>
      </div>
      <div className="account-info-container">
        <h2>Account Information</h2>

        <div className="column-container">
          <div>
            <strong><label>Name: </label></strong>
            <span>{accountData.name}</span>
          </div>

          <div>
            <strong><label>Mobile: </label></strong>
            <span>{accountData.mobile}</span>
          </div>
        </div>

        <div className="column-container">
          <div>
            <strong><label>Account Number: </label></strong>
            <span>{accountData.accountNo}</span>
          </div>

          <div>
            <strong><label>Balance: </label></strong>
            <span>{accountData.balance}</span>
          </div>
        </div>

        <div className="button-container">
          <button className="make-transaction-button" type="button" onClick={goToMakeTranasaction}>Make Transaction</button>
          <button className="view-transaction-button" type="button" onClick={viewTransaction}>View Transaction</button>
          <button className="apply-loan-button" type="button" onClick={applyLoan}>Apply Loan</button>
          <button className="view-loan-button" type="button" onClick={viewLoan}>View Loan</button>
        </div>
      </div>
    </div>
  );
};

export default AccountInfo;
