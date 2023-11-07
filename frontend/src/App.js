import './App.css';
import {Route, Routes} from "react-router-dom";
import HomePage from "./routes/HomePage/HomePage";
import Login from "./routes/Login/Login";
import Layout from "./routes/Layout/Layout";
import Register from "./routes/Register/Register";


function App() {
  return (
    <>
    <Routes>
        <Route path="/" element={<Layout />}>
            <Route path="/" element={<HomePage />} />
            <Route path="/login" element={<Login />} />
            <Route path="/register" element={<Register />} />

        </Route>
    </Routes>

    </>
  );
}

export default App;
