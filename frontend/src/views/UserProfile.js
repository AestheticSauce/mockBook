import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

function UserProfile() {
    const navigate = useNavigate();
    const [user, setUser] = useState(null);

    useEffect(() => {
        // For now fetching my id HARDCODED, since login is not finished.
        fetch("http://localhost:8085/v1/api/users/5")
            .then(response => response.json())
            .then(data => setUser(data))
            .catch(error => console.error("User data may not exist", error));
    }, []);

    const handleEditClick = () => {
        navigate(`/userprofile/5/edit`);
    };

    if (!user) return <div>Loading...</div>;

    return (

        <div>
            <h1>{user.name}</h1>
            <p>Email: {user.email}</p>
            <p>Bio: To be included</p>
            <button onClick={handleEditClick}>Edit Profile</button>
        </div>
    );
}

export default UserProfile;
