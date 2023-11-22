import React, { useEffect, useState } from 'react';
import './index.css';
import { useLocation } from 'react-router-dom';
import axios from 'axios';

const ViewLoan = () => {
    const loanServiceUrl = "http://localhost:8012/loan";
    const location = useLocation();
    const { bankAccountNo, token } = location.state || {};
    const [loanHistory, setLoanHistory] = useState([]);

    useEffect(() => {
        const fetchLoan = async () => {
            try {
                const response = await axios.get(`${loanServiceUrl}/getAllByAccountNo/${bankAccountNo}`, {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                });
                console.log(response.data);
                setLoanHistory(response.data);
            } catch (error) {
                console.error(error.message);
                //handle error
            }

        };
        fetchLoan();
    }, [bankAccountNo, token]);
    return (

        <div className="loan-history">
            <h2>Loan History</h2>
            <span>My Account No: {bankAccountNo}</span>
            <ul>
                {loanHistory.map((loan) => (
                    <li key={loan.loanId}>
                        <span>Loan Account No.: {loan.loanId}</span>
                        <span>Loan Amount: ${loan.loanAmount}</span>
                        <span>Interest Rate: {loan.interestRate}</span>
                        <span>Loan Status: {loan.loanStatus}</span>
                        <span>Loan Application Date: {loan.loanApplicationDateTime}</span>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default ViewLoan;