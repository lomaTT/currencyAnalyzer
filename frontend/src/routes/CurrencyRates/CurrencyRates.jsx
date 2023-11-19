import React, {useEffect, useState} from 'react';
import axios from "axios";

const CurrencyRates = () => {

    const [currencies, setCurrencies] = useState({});

    useEffect(() => {
        axios.get("http://localhost:8080/api/currency/get")
            .then((response) => {
                setCurrencies(response.data);
            })
            .catch((e) => {
                console.log(e)
            });
    }, []);

    return (
        <div>
            {Object.keys(currencies).map((value) => (<>{value}  {currencies[value]} <br /> </>))}
        </div>
    );
};

export default CurrencyRates;