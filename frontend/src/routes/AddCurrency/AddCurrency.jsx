import React, {useEffect, useState} from 'react';
import RedirectProvider from "../../providers/redirect-provider";
import axios from "axios";
import Box from "@mui/material/Box";
import {Alert, Button, FilledInput, FormControl, InputAdornment, InputLabel, Select} from "@mui/material";
import MenuItem from "@mui/material/MenuItem";
import './AddCurrency.css'
import DeleteIcon from "@mui/icons-material/Delete";
import {useTranslation} from "react-i18next";
import {useAuth} from "../../providers/auth-provider";
import {useNavigate} from "react-router-dom";


const currenciesMap = {
    'CURRENCY_USD': '$',
    'CURRENCY_EUR': '€',
    'CURRENCY_RUB': '₽',
    'CURRENCY_PLN': 'PLN',
    'CURRENCY_GBP': '£',
    'CURRENCY_CNY': '¥ (CNY)',
    'CURRENCY_JPY': '¥ (JPY)',
}

const AddCurrency = () => {
    const {t, i18n} = useTranslation();

    const Auth = useAuth();
    const navigate = useNavigate();

    const [currenciesList, setCurrenciesList] = useState([])
    const [currency, setCurrency] = useState('');
    const [value, setValue] = useState(0);
    const [error, setError] = useState(false);
    const [success, setSuccess] = useState(false);

    useEffect(() => {
        axios.get('http://localhost:8080/api/currency/get-list-of-currencies', {
            withCredentials: true
        })
            .then((response) => {
                setCurrenciesList(response.data);
            })
            .catch((error) => console.log(error));
    }, []);

    const handleChange = (event) => {
        setCurrency(event.target.value);
    };

    const handleValueChange = (e) => {
        setValue(e.target.value);
    }

    const handleAddCurrency = () => {
        if (currency === '' || value.toString() === '') {
            return;
        }

        axios.post('http://localhost:8080/api/currency/add-currency-to-currencies-list', {
                "currency_id": currency,
                "value": value,
            }, {withCredentials: true,}
        )
            .then(r => {
                setSuccess(true);
                setTimeout(() => {
                    setSuccess(false);
                    navigate('/dashboard');
                }, 5000);
            })
            .catch(e => {
                setError(true);
                setTimeout(() => {
                    Auth.userLogout();
                    setError(false);
                    navigate('/');
                }, 5000);
            });
    }

    return (
        <div>
            <RedirectProvider/>
            <div className="select-container">
                <Box sx={{minWidth: 120}}>
                    <FormControl fullWidth>
                        <InputLabel id="demo-simple-select-label">Currency</InputLabel>
                        <Select
                            labelId="currency-select-label"
                            id="currency-select"
                            value={currency}
                            label="Currency"
                            onChange={handleChange}
                        >
                            {currenciesList.map((currency, key) => (
                                <MenuItem key={key} value={currency}>{t(currency)}</MenuItem>
                            ))}
                        </Select>

                    </FormControl>
                    <div className="value-input">
                        <FilledInput
                            className="currency-value"
                            id="currency-amount"
                            onChange={(e) => handleValueChange(e)}
                            value={value}
                            startAdornment={<InputAdornment
                                position="start">{currenciesMap[currency]}</InputAdornment>}
                        />
                    </div>

                    <div className="add-button">
                        <Button color="info" disabled={error === true || success === true}
                                onClick={() => handleAddCurrency()}
                        >
                            Add
                        </Button>

                        {(error === true) ? (<Alert severity="error">Something went wrong. Redirecting....</Alert>) : (<></>)}
                        {(success === true) ? (<Alert severity="success">You successfully added currency. Redirecting....</Alert>) : (<></>)}
                    </div>

                </Box>
            </div>
        </div>
    );
};

export default AddCurrency;