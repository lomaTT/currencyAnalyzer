import React, {useEffect, useState} from 'react';
import {useAuth} from "../../providers/auth-provider";
import RedirectProvider from "../../providers/redirect-provider";
import axios from "axios";
import { Pie } from 'react-chartjs-2';
import {ArcElement, Chart, Legend, Tooltip} from "chart.js";
import './Dashboard.css'

const Dashboard = () => {
    const [responseJson, setResponseJson] = useState([]);

    // const [data, setData] = useState([]);
    useEffect(() => {
        const response = axios.get('http://localhost:8080/api/currency/get-users-currencies', {
            withCredentials: true,
        })
            .then((response) => {
                setResponseJson(response.data);
            })
            .catch((error) => error.error);
        }, []);
    const Auth = useAuth()

    console.log(responseJson);
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

    return (
        <div>
            <RedirectProvider />

            <div className="mainLayout">
                <h2>Your finances</h2>
                <div className="circleDiagram">
                    <Pie data={data} />
                </div>
            </div>

        </div>
    );
};

export default Dashboard;