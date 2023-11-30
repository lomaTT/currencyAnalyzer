import React, {useEffect} from 'react';
import RedirectProvider from "../../providers/redirect-provider";
import axios from "axios";

const AddCurrency = () => {
    useEffect(() => {
        axios.get('http://localhost:8080/api/currency/get-list-of-currencies')
            .then((response) => console.log(response.data))
            .catch((error) => console.log(error));
    }, []);

    return (
        <div>
            <RedirectProvider />
            add currency component
        </div>
    );
};

export default AddCurrency;