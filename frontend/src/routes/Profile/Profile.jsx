import React, {useEffect, useState} from 'react';
import {useAuth} from "../../providers/auth-provider";
import {Link} from "react-router-dom";
import Button from "@mui/material/Button";
import RedirectProvider from "../../providers/redirect-provider";
import axios from "axios";
import {useTranslation} from "react-i18next";
import Box from "@mui/material/Box";
import { DataGrid } from '@mui/x-data-grid';
import {t} from "i18next";
import "./Profile.css";

const convertISOTimeToNormal = (isoDate) => {
    const date = new Date(new Date(isoDate).getTime()).toLocaleDateString("pl-PL");
    const time = new Date(new Date(isoDate).getTime()).toLocaleTimeString("pl-PL");
    return date + " " + time;

}

const columnVisibilityModel = {
    id: false,
};

const columns = [
    { field: 'id', headerName: 'ID', width: 90 },
    {
        field: 'transaction_time',
        headerName: 'Time and date',
        width: 250,
        editable: false,
        valueGetter: (params) => `${convertISOTimeToNormal(params.row.transaction_time) || ''}`,
    },
    {
        field: 'value',
        headerName: 'Value',
        width: 100,
        editable: false,
        valueGetter: (params) => `${params.row.value || ''}`,
    },
    {
        field: 'operation',
        headerName: 'Operation',
        // type: 'number',
        width: 150,
        editable: false,
        valueGetter: (params) => `${t(params.row.operation) || ''}`,
    },
    {
        field: 'currency',
        headerName: 'Currency',
        // description: 'This column has a value getter and is not sortable.',
        sortable: false,
        width: 200,
        valueGetter: (params) => `${t(params.row.currency.currency) || ''}`,
        // valueGetter: (params) =>
        //     `${params.row.firstName || ''} ${params.row.lastName || ''}`,
    },
];

const Profile = () => {
    const Auth = useAuth();

    const [transactions, setTransactions] = useState([]);
    const [rows, setRows] = useState([]);
    
    useEffect(() => {
        axios.get('http://localhost:8080/api/currency/get-last-user-transactions', {
            withCredentials: true,
        })
            .then(response =>  {
                setTransactions(response.data);
                // console.log(response.data);
            })
            .catch(error => console.log(error));
    }, []);

    // console.log(rows);

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
                    <Box sx={{ height: 400, width: '80%', margin: '0 auto' }}>
                        <DataGrid
                            rows={transactions}
                            columns={columns}
                            columnVisibilityModel={columnVisibilityModel}
                            initialState={{
                                pagination: {
                                    paginationModel: {
                                        pageSize: 5,
                                    },
                                },
                            }}
                            pageSizeOptions={[5]}
                            checkboxSelection
                            disableRowSelectionOnClick
                        />
                    </Box>
                </div>

            </div>
        </div>
    );
};

export default Profile;