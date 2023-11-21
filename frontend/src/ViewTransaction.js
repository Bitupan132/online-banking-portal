// TransactionForm.js
import React, { useEffect, useState } from 'react';
import './index.css';
import { useLocation } from 'react-router-dom';
import axios from 'axios';

const ViewTransaction = () => {
    const transactionServiceUrl = "http://localhost:8081/transaction";
    const location = useLocation();
    const { currentAccountNo, token } = location.state || {};
    const [transactionHistory, setTransactionHistory] = useState([]);

    useEffect(() => {
        const fetchTransactions = async () => {
            let response = [];
            try {
                const response = await axios.get(`${transactionServiceUrl}/getAll/${currentAccountNo}`, {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                });
                console.log(response.data);
                setTransactionHistory(response.data);
            } catch (error) {
                console.error(error.message);
                //handle error
            }

        };
        fetchTransactions();
    }, [currentAccountNo, token]);


    return (

        <div className="transaction-history">
            <h2>Transaction History</h2>
            <span>My Account No: {currentAccountNo}</span>
            <ul>
                {transactionHistory.map((transaction) => (
                    <li key={transaction.transactionId}>
                        {/* <span>Transaction ID: {transaction.transactionId}</span> */}
                        <span>Transaction Type: {currentAccountNo===transaction.senderAccountNo?"DEBIT":"CREDIT"}</span>
                        <span>Account No: {currentAccountNo!==transaction.senderAccountNo?transaction.senderAccountNo:transaction.receiverAccountNo}</span>
                        <span>Amount: ${transaction.amount}</span>
                        <span>Summary: {transaction.summary}</span>
                        <span>Transaction Time: {transaction.transactionDateTime}</span>

                    </li>
                ))}
            </ul>
        </div>
    );
};

export default ViewTransaction;
