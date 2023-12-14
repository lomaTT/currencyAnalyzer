import logo from "../../public/logo.png";
import './HomePage.css';

const HomePage = () => {
    return (
        <div className="test">
            <div className="home-page">
                <img src={logo} alt="photo-logo"/>
                <div className="text-div">
                    Currency Analyzer - open-source application that can store all your financial transactions,
                    conversations and etc. Work with pleasure!
                </div>
            </div>
        </div>
    );
}

export default HomePage;
