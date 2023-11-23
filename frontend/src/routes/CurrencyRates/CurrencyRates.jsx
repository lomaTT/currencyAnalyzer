import React, {useEffect, useState} from 'react';
import axios from "axios";
import MenuItem from "@mui/material/MenuItem";
import {
    FormControl,
    InputLabel,
    OutlinedInput,
    Paper,
    Select, Table,
    TableBody,
    TableCell, TableContainer,
    TableHead,
    TableRow
} from "@mui/material";
import "./CurrencyRates.css";

function createData(name, value) {
    return { name, value };
}

const CurrencyRates = () => {

    const [currencies, setCurrencies] = useState({});
    const [currenciesNames, setCurrenciesNames] = useState([]);
    const [selectedCurrency, setSelectedCurrency] = React.useState();
    const [rows, setRows] = useState([]);

    const handleChange = (event) => {
        const {
            target: { value },
        } = event;
        setSelectedCurrency(typeof value === 'string' ? value.split(',') : value);
        // console.log(value);
        axios.get(`http://localhost:8080/api/currency/get/${currencies[value]}`)
            .then((response) => {
                let rowsForTable = [];
                for (const [key, value] of Object.entries(response.data)) {
                    let keys = Object.keys(currencies).filter(k=> currencies[k] === key);
                    if (key !== "STATUS") {
                        rowsForTable.push(createData(keys[0], value));
                    }
                }
                // console.log(rowsForTable);
                setRows(rowsForTable);
            })
            .catch((e) => {
                console.log("Error: " + e)
            });
    };

    useEffect(() => {
        axios.get("http://localhost:8080/api/currency/get")
            .then((response) => {
                setCurrencies(response.data);
                const currenciesResponse = Object.keys(response.data).filter((item) => item !== "STATUS");
                setCurrenciesNames(currenciesResponse);
            })
            .catch((e) => {
                console.log(e)
            });
    }, []);

    return (
        <div>
            <div className="form-control">
                <FormControl sx={{ m: 1, width: 300 }}>
                    <InputLabel id="demo-multiple-name-label">Name</InputLabel>
                    <Select
                        labelId="demo-multiple-name-label"
                        id="demo-multiple-name"
                        // multiple
                        value={selectedCurrency}
                        onChange={handleChange}
                        input={<OutlinedInput label="Name" />}
                    >
                        {currenciesNames.map((name) => (
                            <MenuItem
                                key={name}
                                value={name}
                            >
                                {name}
                            </MenuItem>
                        ))}
                    </Select>
                </FormControl>
            </div>


            <TableContainer component={Paper}>
                <Table sx={{ minWidth: 650, maxWidth: 900, margin: "0 auto" }} aria-label="simple table">
                    <TableHead>
                        <TableRow>
                            <TableCell>Name of the currency</TableCell>
                            <TableCell align="right">Value</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {rows.map((row) => (
                            <TableRow
                                key={row.name}
                                sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                            >
                                <TableCell component="th" scope="row">
                                    {row.name}
                                </TableCell>
                                <TableCell align="right">{row.value}</TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>
        </div>
    );
};

export default CurrencyRates;