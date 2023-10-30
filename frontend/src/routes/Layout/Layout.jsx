import React from 'react';
import HeaderComponent from "../../components/HeaderComponent/HeaderComponent";
import {Outlet} from "react-router-dom";

const Layout = () => {
    return (
        <div>
            <div className="main-content">
                <HeaderComponent/>

                <Outlet/>
            </div>
        </div>
    );
};

export default Layout;