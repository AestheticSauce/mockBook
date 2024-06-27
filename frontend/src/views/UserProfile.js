import React, { useState, useEffect } from 'react';
import {resolvePath, useNavigate} from 'react-router-dom';
import {getToken, getUserProfile} from "../jwt"; // Assuming you use react-router-dom

function UserProfileComponent() {
        const [user, setUser] = useState({});
        const [loading, setLoading] = useState(true);
        const [error, setError] = useState(null);
        const navigate = useNavigate();

        // const handleEditClick = () => {
        //     navigate(`/userprofile/${id}/edit`);
        // };

        // Effect to pull in user data
        useEffect(() => {
            const fetchUser = async () => {
                try {
                    const userProfile = await getUserProfile();
                    const token =getToken().trim();
                    console.log(`Token ${token}`);
                    const myHeaders = {
                        "Authorization": `Bearer ${token}`,
                        "Content-Type": "application/json",
                        "Accept": "application/json"
                    };
                    console.log('Headers:', myHeaders);
                    const requestOptions = {
                        method: "GET",
                        headers: myHeaders,
                        redirect: "follow"
                    };

                    const response = await fetch(`http://localhost:8085/v1/api/users/${userProfile.id}`, requestOptions)
                    console.log('Response Headers:', response.headers);
                    console.log('Response Status:', response.status);

                    if (!response.ok) {
                        throw new Error('Network response was not ok');
                    }
                    const result = await response.json();
                    console.log('Result:', result);
                    setUser(result);
                    console.log(`user data:`,user.data)
                    navigate(`/userprofile/${result.id}/`);
                    setLoading(false);
                } catch (error) {
                    setError(error);
                    setLoading(false);
                }
            };
            fetchUser();

        }, [navigate]);

    useEffect(() => {
        console.log('User data updated:', user);
    }, [user]);

        // const setStatus = async (selectedStatus) => {
        //     try {
        //         const response = await axios.put(`http://localhost:8085/v1/api/users/${id}/status`, {
        //             status: selectedStatus
        //         }, {
        //             headers: {
        //                 'Authorization': `Bearer ${getToken()}`,
        //                 'Content-Type': 'application/json',
        //             }
        //         });
        //         console.log(response.data);
        //     } catch (error) {
        //         console.error(`Error changing status`, error);
        //     }
        // };
        //
        // const statusChange = (event) => {
        //     const status = event.target.value;
        //     setStatus(status);
        // };

        return (
            <>
                <div>
                    <h1>doggy</h1>
                    <p>Email: {user.email}</p>
                    <p>Bio: {user.biography}</p>
                    <p>Favourite food: {user.favFood}</p>
                    <p>Birthday: {user.degree}</p>
            {/*        <button onClick={handleEditClick}>Edit Profile</button>*/}
            {/*    </div>*/}
            {/*    <div>*/}
            {/*        <select name="status" onChange={statusChange} style={{ padding: '10px', borderRadius: '5px',*/}
            {/*            border: '1px solid #ccc' }}>*/}
            {/*            <option value="">user.status</option>*/}
            {/*            <option value="Online">Online</option>*/}
            {/*            <option value="Do not disturb">Do not disturb</option>*/}
            {/*            <option value="Offline">Offline</option>*/}
            {/*        </select>*/}
                </div>
            </>
        );
}

export default UserProfileComponent;
