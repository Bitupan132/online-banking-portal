import React, { useEffect, useState } from 'react';
import '../css/ViewTransaction.css';
import { useLocation, useNavigate } from 'react-router-dom';
import axios from 'axios';
import { useToken } from './TokenProvider';

const ViewTransaction = () => {
    const transactionServiceUrl = "http://localhost:8081/transaction";
    const location = useLocation();
    const navigate = useNavigate();
    const { currentAccountNo, username } = location.state || {};
    const { token, updateToken } = useToken();
    const [transactionHistory, setTransactionHistory] = useState([]);

    const returnHome = () => {
        navigate('/account', {
            state: {
                username: username,
            },
        });
    }

    useEffect(() => {
        const fetchTransactions = async () => {
            try {
                const response = await axios.get(`${transactionServiceUrl}/getAll/${currentAccountNo}`, {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                });
                if (response.data === "INVALID_TOKEN") {
                    alert("Session Expired. Please Login again.");
                    navigate('/');
                }
                else { setTransactionHistory(response.data); }

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
        fetchTransactions();
    }, [currentAccountNo, token]);


    return (

        <div className="transaction-history">
            <h2>Transaction History</h2>
            <span>My Account No: {currentAccountNo}</span>
            <table>
                <thead>
                    <tr>
                        <th>Transaction Type</th>
                        <th>Paid to/Received from</th>
                        <th>Amount</th>
                        <th>Summary</th>
                        <th>Transaction Time</th>
                    </tr>
                </thead>
                <tbody>
                    {transactionHistory.map((transaction) => (
                        <tr key={transaction.transactionId}>
                            <td className={currentAccountNo === transaction.senderAccountNo ? "debit" : "credit"}>{currentAccountNo === transaction.senderAccountNo ? "DEBIT" : "CREDIT"}</td>
                            <td>{currentAccountNo !== transaction.senderAccountNo ? transaction.senderAccountNo : transaction.receiverAccountNo}</td>
                            <td>${transaction.amount}</td>
                            <td>{transaction.summary}</td>
                            <td>{transaction.transactionDateTime}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
            <div>
                <button onClick={returnHome} className="return-button">Main Menu</button>
            </div>
        </div>

    );
};

export default ViewTransaction;
