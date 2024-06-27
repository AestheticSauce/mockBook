import { Link } from "react-router-dom";
import React, {useState} from "react";
import {useNavigate} from 'react-router-dom';
import * as jwtUtil from "../jwt.js";

function Login(){
    // State to manage password masking
    const [showPassword, setShowPassword] = useState(false);
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');

    const navigate = useNavigate();

    const handleLogin = async (event) => {
        event.preventDefault();

        try {
            const response = await fetch("http://localhost:8085/v1/api/auth/login", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({email, password})
            });

            if (!response.ok) {
                setError("Invalid credentials. Please try again.");
                return;
            }

            const data = await response.json();
            jwtUtil.setToken(data.token);
            const user = await jwtUtil.getUserProfile();
            console.log('user:', user);
            if (user) {
                navigate(`/userprofile/${user.sub}`); // Redirect to user profile page
            } else {
                setError("Failed to decode token. Please try again.");
            }
        } catch (error) {
            console.error("Error:", error);
            setError("An error occurred. Please try again later.");
        }
    };

    return (
        <div>
            <h2>Login</h2>
            {/* Add your login form here */}
            <Link to="/password-reset">Forgot Password?</Link>
            <h1>Login</h1>
            <form onSubmit={handleLogin}>
                <label htmlFor="email">Email</label>
                <input
                    id="email"
                    type="text"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                />

                <label htmlFor="password">Password</label>
                <input
                    id="password"
                    type={showPassword ? "text" : "password"}
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                />

                <button type="button" onClick={() => setShowPassword(!showPassword)}>
                    {showPassword ? "Hide" : "Show"} Password
                </button>

                {error && <p style={{color: 'red'}}>{error}</p>}

                <p></p>
                <button type="submit">Submit</button>

                <p></p>
                <button type="button" onClick={() => window.location.href = "/Register"}>
                    Register
                </button>
            </form>
        </div>
    );
}

export default Login;