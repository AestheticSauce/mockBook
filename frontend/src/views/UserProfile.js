import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

function UserProfile() {
    const navigate = useNavigate();
    const [user, setUser] = useState(null); //handling who the user is
    const [loading, setLoading] = useState(true); // To handle loading state
    const [error, setError] = useState(null); // To handle errors
    const handleEditClick = () => {
        navigate(`/userprofile/5/edit`);
    };

    // use effect that fetches correct user data, and handles errors (network/user not existing)
    useEffect(() => {
        fetch("http://localhost:8085/v1/api/users/5")
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network failure');
                }
                return response.json();
            })
            .then(data => {
                setUser(data);
                setLoading(false);
            })
            .catch(error => {
                console.error("User may not exist!", error);
                setError(error.message);
                setLoading(false);
            });
    }, []);

    //handling errors/loading of the page
    if (loading) return <div>Loading...</div>;
    if (error) return <div>Error: {error}</div>;
    if (!user) return <div>No user data available</div>;

    return (
        <div>
            <h1>{user.name}</h1>
            <p>Email: {user.email}</p>
            <p>Bio: </p>
            <p>Favourite Color: </p>
            <p>Birthday: </p>
            <button onClick={handleEditClick}>Edit Profile</button>
        </div>
    );
}

export default UserProfile;
