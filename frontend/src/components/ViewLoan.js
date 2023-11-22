import React, { useEffect, useState } from 'react';
import '../css/viewLoan.css';
import { useLocation, useNavigate } from 'react-router-dom';
import axios from 'axios';

const ViewLoan = () => {
    const loanServiceUrl = "http://localhost:8012/loan";
    const location = useLocation();
    const navigate = useNavigate();
    const { bankAccountNo, token, username } = location.state || {};
    const [loanHistory, setLoanHistory] = useState([]);

    const deleteLoan = async(loanId) => {
        try {
            const response = await axios.delete(`${loanServiceUrl}/deleteLoan/${loanId}`, {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });
            if (response.data === "INVALID_TOKEN") {
                alert("Session Expired. Please Login again.");
                navigate('/');
            }else{
                fetchLoan();
            }
        } catch (error) {
            console.error(error.message);
        }
    };

    const returnHome =()=>{
        navigate('/account', {
            state: {
              token: token,
              username: username,
            },
          });
    }
    const fetchLoan = async () => {
        try {
            const response = await axios.get(`${loanServiceUrl}/getAllByAccountNo/${bankAccountNo}`, {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });
            if (response.data === "INVALID_TOKEN") {
                alert("Session Expired. Please Login again.");
                navigate('/');
            }else{
            setLoanHistory(response.data);
            }
        } catch (error) {
            console.error(error.message);
        }

    };
    const getStatusColor = (loanStatus) => {
        switch (loanStatus) {
            case 'PENDING':
                return 'orange';
            case 'REJECTED':
                return 'red';
            case 'APPROVED':
                return 'green';
            default:
                return 'black'; 
        }
    }
    useEffect(() => {
        fetchLoan();
    }, [bankAccountNo, token]);
    return (

        <div className="loan-history">
            <h2>Loan History</h2>
            <span>My Account No: {bankAccountNo}</span>
            <div>
                <ul >
                    {loanHistory.map((loan) => (
                        <li key={loan.loanId}>
                            <span>Loan Account No.: {loan.loanId}</span>
                            <span>Loan Amount: ${loan.loanAmount}</span>
                            <span>Interest Rate: {loan.interestRate}</span>
                            <span style={{ display: 'flex', alignItems: 'center' }}>
                                <span style={{ color: 'black' }}>Loan Status: </span>
                                <span style={{ color: getStatusColor(loan.loanStatus) }}>{loan.loanStatus}</span>
                            </span>
                            <span>Loan Application Date: {loan.loanApplicationDateTime}</span>
                            <button
                                className="delete-loan-button"
                                onClick={()=>{deleteLoan(loan.loanId)}}
                            >
                                Delete Loan
                            </button>
                        </li>
                    ))}
                </ul>
            </div>
            <div>
                <button onClick={returnHome} className="return-button">Main Menu</button>
            </div>
        </div>
    );
};

export default ViewLoan;