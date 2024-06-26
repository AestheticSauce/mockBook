import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from "axios";
import { useParams } from 'react-router-dom';
import {getUserId, getUserProfile, verifyJwt} from "../jwt.js";
function UserProfile() {
    const navigate = useNavigate();
    const [user, setUser] = useState(null); //handling who the user is
    const [loading, setLoading] = useState(true); // To handle loading state
    const [error, setError] = useState(null); // To handle errors
    const [ id,setId] = useState(null);

    const handleEditClick = () => {
        navigate(`/userprofile/25/edit`);
    };



    //effect to pull in user data.. will probably implement smarter solution later
    useEffect(() => {
        const fetchUser = async () => {
            try {
                var usrProfile = await getUserProfile();
                // get user info
                // var newUser = fetch("user/{usrprofile.id}")
                setUser(newUser);
                navigate(`/userprofile/${newUser.id}/`);
                setId(newUser.id);
                setLoading(false);
            } catch (error) {
                setError(error);
                setLoading(false);
            }
        };
        fetchUser();
    }, []);

    //handling errors/loading of the page
    if (loading) return <div>Loading...</div>;
    if (error) return <div>Error: {error}</div>;
    if (!user) return <div>No user data available</div>;

    const setStatus= async (selectedRole) => {
        try{
            const response = await axios.put(`http://localhost:8085/v1/api/users/${id}/status`);
            console.log(response.data);
            await setStatus(response.data);
        } catch (error) {
            console.error(`Error changing status`, error);
        }
    };

    const statusChange = (event) => {
        const status = event.target.value;
        setStatus(id,status)
    };

    return (
        <>
        <div>
            <h1>{user.username}</h1>
            <p>Email: {user.email}</p>
            <p>Bio: {user.biography}</p>
            <p>Favourite food: {user.favFood}</p>
            <p>Birthday: {user.degree}</p>
            <button onClick={handleEditClick}>Edit Profile</button>
        </div>
        <div>
            <select name="status" onChange={statusChange} style={{ padding: '10px', borderRadius: '5px',
                border: '1px solid #ccc' }}>
                <option value="">user.status</option>
                <option value="Online">Online</option>
                <option value="Do not disturb">Do not disturb</option>
                <option value="Offline">Offline</option>
            </select>
        </div>
        </>
    );
}

export default UserProfile;
