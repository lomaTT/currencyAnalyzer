import React, {useEffect, useState} from 'react';
import {useAuth} from "../../providers/auth-provider";
import {Link} from "react-router-dom";
import Button from "@mui/material/Button";
import RedirectProvider from "../../providers/redirect-provider";
import axios from "axios";
import {useTranslation} from "react-i18next";

const convertISOTimeToNormal = (isoDate) => {
    const date = new Date(new Date(isoDate).getTime()).toLocaleDateString("pl-PL");
    const time = new Date(new Date(isoDate).getTime()).toLocaleTimeString("pl-PL");
    return date + " " + time;

}

const Profile = () => {
    const Auth = useAuth();
    const { t, i18n } = useTranslation();

    const [transactions, setTransactions] = useState([]);
    
    useEffect(() => {
        axios.get('http://localhost:8080/api/currency/get-last-user-transactions', {
            withCredentials: true,
        })
            .then(response => setTransactions(response.data))
            .catch(error => console.log(error));
    }, []);

    return (
        <div>
            <RedirectProvider />
            <div className="main-description">
                Your profile
                <div>
                    <Button variant="contained" component={Link} to={"/profile/add-currency"}>Add currency</Button>
                </div>

                <div className="last-transactions-table">
                    <h2>Your last transactions</h2>
                    {transactions.map((transaction, key) => (
                        <div key={key}>{convertISOTimeToNormal(transaction.transaction_time)}, {transaction.value}, {t(transaction.operation)}, {t(transaction.currency.currency)}</div>
                    ))}
                </div>

            </div>
        </div>
    );
};

export default Profile;