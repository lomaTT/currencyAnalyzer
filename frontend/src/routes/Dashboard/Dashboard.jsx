import React, {useEffect, useState} from 'react';
import {useAuth} from "../../providers/auth-provider";
import RedirectProvider from "../../providers/redirect-provider";
import axios from "axios";
import { Pie } from 'react-chartjs-2';
import {ArcElement, Chart, Legend, Tooltip} from "chart.js";
import './Dashboard.css'
import {
    Paper,
    styled,
    Table,
    TableBody,
    TableCell,
    tableCellClasses,
    TableContainer,
    TableHead,
    TableRow,
    Button
} from "@mui/material";
import DeleteIcon from '@mui/icons-material/Delete';

const StyledTableCell = styled(TableCell)(({ theme }) => ({
    [`&.${tableCellClasses.head}`]: {
        backgroundColor: theme.palette.common.black,
        color: theme.palette.common.white,
    },
    [`&.${tableCellClasses.body}`]: {
        fontSize: 14,
    },
}));

const StyledTableRow = styled(TableRow)(({ theme }) => ({
    '&:nth-of-type(odd)': {
        backgroundColor: theme.palette.action.hover,
    },
    // hide last border
    '&:last-child td, &:last-child th': {
        border: 0,
    },
}));

const Dashboard = () => {
    const [responseJson, setResponseJson] = useState([]);
    const [rerender, setRerender] = useState(false);

    // const [data, setData] = useState([]);
    useEffect(() => {
        const response = axios.get('http://localhost:8080/api/currency/get-users-currencies', {
            withCredentials: true,
        })
            .then((response) => {
                setResponseJson(response.data);
                setRerender(false);
            })
            .catch((error) => error.error);
        }, [rerender]);
    const Auth = useAuth()

    function createData(currency_name, value) {
        return { currency_name, value };
    }

    const rows = [];

    Object.entries(responseJson).map((value) => {
        rows.push(createData(value[0], value[1]));
    });

    Chart.register(ArcElement, Tooltip, Legend);

    const data = {
        labels: Object.entries(responseJson).map(item => item[0]),
        // datasets is an array of objects where each object represents a set of data to display corresponding to the labels above. for brevity, we'll keep it at one object
        datasets: [
            {
                label: 'Money',
                data: Object.entries(responseJson).map(item => item[1]),
                backgroundColor: [
                    'rgba(255, 99, 132, 0.2)',
                    'rgba(54, 162, 235, 0.2)',
                    'rgba(255, 206, 86, 0.2)',
                    'rgba(75, 192, 192, 0.2)',
                    'rgba(153, 102, 255, 0.2)',
                    'rgba(255, 159, 64, 0.2)',
                ],
                borderWidth: 1,
            }
        ]
    }

    const handleDeleteCurrency = (currency_name) => {
        axios.delete('http://localhost:8080/api/currency/delete-user-currency', {
            withCredentials: true,
            data: {
                "currency_id": currency_name,
            }
        })
            .then((response) => {
                console.log(response.data);
                setRerender(true);
            })
            .catch((error) => console.log(error));
    };

    return (
        <div>
            <RedirectProvider />

            <div className="mainLayout">
                <h2>Your finances</h2>
                <div className="circleDiagram">
                    {Object.keys(responseJson).length !== 0 ? <Pie data={data} /> : <div className="diagram-info">No data presented</div>}
                </div>
                <h2>Manage your currencies</h2>
                <div className="rowData">
                    <TableContainer component={Paper}>
                        <Table sx={{ minWidth: 700 }} aria-label="customized table">
                            <TableHead>
                                <TableRow>
                                    <StyledTableCell>Currency</StyledTableCell>
                                    <StyledTableCell align="right">Value</StyledTableCell>
                                    <StyledTableCell align="right"></StyledTableCell>
                                </TableRow>
                            </TableHead>
                            <TableBody>
                                {rows.map((row) => (
                                    <StyledTableRow key={row.currency_name}>
                                        <StyledTableCell component="th" scope="row">
                                            {row.currency_name}
                                        </StyledTableCell>
                                        <StyledTableCell align="right">{row.value}</StyledTableCell>
                                        <StyledTableCell align="right"><Button color="info" startIcon={<DeleteIcon />} onClick={() => handleDeleteCurrency(row.currency_name)}>Delete</Button></StyledTableCell>
                                    </StyledTableRow>
                                ))}
                            </TableBody>
                        </Table>
                    </TableContainer>
                </div>
            </div>

        </div>
    );
};

export default Dashboard;