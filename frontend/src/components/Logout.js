import React from "react";
import { useNavigate } from 'react-router-dom';
import {getToken, getUserProfile} from "../jwt";

function Logout() {
    const navigate = useNavigate();

    const handleLogout = async () => {
        try {
            const token =getToken().trim();
            console.log(`Token ${token}`);
            const myHeaders = {
                "Authorization": `Bearer ${token}`,
                "Content-Type": "application/json",
                "Accept": "application/json"
            };
            console.log('Headers:', myHeaders);
            const requestOptions = {
                method: "DELETE",
                headers: myHeaders,
                redirect: "follow"
            };

            localStorage.removeItem('token');
            navigate('/login');
        } catch (error) {
            console.error("Error:", error);
        }
    }
    return (
        <div>
            <button onClick={handleLogout}>Logout</button>
        </div>
    );
}

export default Logout;
