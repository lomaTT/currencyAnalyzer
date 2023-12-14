import React, {useEffect, useState} from 'react';
import axios from "axios";
import './Trade.css';
import {Alert, FormControl, InputLabel, Select, TextField} from "@mui/material";
import MenuItem from "@mui/material/MenuItem";
import {t} from "i18next";
import Button from "@mui/material/Button";
import {useNavigate} from "react-router-dom";
import {useTimeout} from "@mui/x-data-grid/internals";

const Trade = () => {

    const [responseJson, setResponseJson] = useState([]);
    const [rerender, setRerender] = useState(false);

    const [existingCurrency, setExistingCurrency] = useState('');
    const [wantedCurrency, setWantedCurrency] = useState('');
    const [listOfCurrencies, setListOfCurrencies] = useState([]);
    const [currencyValue, setCurrencyValue] = useState(0);

    const [error, setError] = useState(false);
    const [success, setSuccess] = useState(false);

    const navigate = useNavigate();

    const [selectedCurrencyRateResponse, setSelectedCurrencyRateResponse] = useState(0);

    const calculateRate = async () => {
        const currencyCodeExistingCurrency = existingCurrency.split('_')[1];
        const currencyCodeWantedCurrency = wantedCurrency.split('_')[1];
        if (existingCurrency === '' || wantedCurrency === '') {
            return '';
        }

        const response = await (await axios.get(`http://localhost:8080/api/currency/get/${currencyCodeExistingCurrency}`, {
            withCredentials: true,
        })).data;

        setSelectedCurrencyRateResponse(response[currencyCodeWantedCurrency]);
    }

    const rateValue = async () => {
        if (existingCurrency === '' || wantedCurrency === '') return;
        await calculateRate();
    }

    const handleChangeExistingCurrency = (event) => {
        setExistingCurrency(event.target.value);
    };

    const handleChangeWantedCurrency = (event) => {
        setWantedCurrency(event.target.value);
        setRerender(true);
    }

    const handleCurrencyValueChange = (event) => {
        if (event.target.value > responseJson[existingCurrency]) {
            setCurrencyValue(responseJson[existingCurrency]);
        } else {
            setCurrencyValue(event.target.value);
        }
    }

    useEffect(() => {
        axios.get('http://localhost:8080/api/currency/get-users-currencies', {
            withCredentials: true,
        })
            .then((response) => {
                setResponseJson(response.data);
                setRerender(false);
                console.log(response.data);
            })
            .catch((error) => error.error);

        axios.get('http://localhost:8080/api/currency/get-list-of-currencies', {
            withCredentials: true,
        })
            .then((response) => {
                setListOfCurrencies(response.data);
            })

        rateValue();

    }, [rerender]);

    const handleTrade = () => {
        axios.post('http://localhost:8080/api/currency/trade-currency', {
            wantedCurrency: wantedCurrency,
            existingCurrency: existingCurrency,
            value: currencyValue,
            rate: selectedCurrencyRateResponse
        }, {
            withCredentials: true,
        })
            .then(response => {
                setSuccess(true);
                setTimeout(() => {
                    setSuccess(false);
                    navigate('/dashboard');
                }, 5000);
            })
            .catch(error => {
                setError(true);
                setTimeout(() => {
                    setError(false);
                    navigate('/dashboard');
                }, 5000);
            });
    }

    return (
        <div className="trade-content">
            <h2>Trade your currencies</h2>

            <div className="sell-content-box">
                <FormControl fullWidth>
                    <InputLabel id="demo-simple-select-label">What currency you want to sell?</InputLabel>
                    <Select
                        labelId="currency-select-label"
                        id="currency-select"
                        value={existingCurrency}
                        label="Currency"
                        onChange={handleChangeExistingCurrency}
                    >
                        {Object.keys(responseJson).map((currency, key) => {
                            if (currency !== wantedCurrency) {
                                return (<MenuItem key={key} value={currency}>{t(currency)}</MenuItem>);
                            }
                        })}
                    </Select>
                </FormControl>
                {(existingCurrency !== '') ?
                    (<div>Currently
                        have: {responseJson[existingCurrency]} {(t(existingCurrency)).toLowerCase()}</div>) : (
                        <div></div>)}
            </div>

            <div className="value-content-box">
                <TextField
                    id="outlined-basic"
                    label="Value"
                    variant="outlined"
                    fullWidth
                    type={"number"}
                    onChange={handleCurrencyValueChange}
                    value={currencyValue}
                    disabled={existingCurrency === ''}
                />
            </div>


            <div className="buy-content-box">
                <FormControl fullWidth>
                    <InputLabel id="demo-simple-select-label">What currency you want to buy?</InputLabel>
                    <Select
                        labelId="currency-select-label"
                        id="currency-select"
                        value={wantedCurrency}
                        label="Currency"
                        disabled={existingCurrency === ''}
                        onChange={handleChangeWantedCurrency}
                    >
                        {listOfCurrencies.map((currency, key) => {
                            if (currency !== existingCurrency) {
                                return <MenuItem key={key} value={currency}>{t(currency)}</MenuItem>;
                            }
                        })}
                    </Select>
                </FormControl>
                {(existingCurrency === '' || wantedCurrency === '') ?
                    (<div></div>) :
                    (<div className="bottom-container">
                        Current rate: 1 {t(existingCurrency)} = {selectedCurrencyRateResponse.toFixed(2)} {t(wantedCurrency)}
                        <Button disabled={error === true || success === true} onClick={handleTrade}>Trade?</Button>
                    </div>)}
                {error === true ? (<Alert severity="error">Something went wrong. Redirecting....</Alert>) : (<div></div>)}
                {success === true ? (<Alert severity="success">You successfully traded currency. Redirecting....</Alert>) : (<div></div>)}
            </div>


        </div>
    );
};

export default Trade;