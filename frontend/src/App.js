import './App.css';
import {BrowserRouter, Route, Routes} from "react-router-dom";
import HomePage from "./routes/HomePage/HomePage";
import Login from "./routes/Login/Login";
import Layout from "./routes/Layout/Layout";
import Register from "./routes/Register/Register";
import {useState} from "react";
import Dashboard from "./routes/Dashboard/Dashboard";
import {AuthProvider} from "./providers/auth-provider";
import Logout from "./routes/Logout/Logout";
import Profile from "./routes/Profile/Profile";
import CurrencyRates from "./routes/CurrencyRates/CurrencyRates";


function App() {
    return (
        <>
            <AuthProvider>
                <BrowserRouter>
                    <Routes>
                        <Route path="/" element={<Layout/>}>
                            <Route path="/" element={<HomePage/>}/>
                            <Route path="/login" element={<Login/>}/>
                            <Route path="/register" element={<Register/>}/>
                            <Route path="/dashboard" element={<Dashboard/>}/>
                            <Route path="/profile" element={<Profile />} />
                            <Route path="/logout" element={<Logout/>}/>
                            <Route path="/currency-rates" element={<CurrencyRates />} />
                        </Route>
                    </Routes>
                </BrowserRouter>
            </AuthProvider>
        </>
    );
}

export default App;
