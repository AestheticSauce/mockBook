import React, { useState, useEffect } from 'react';
import {resolvePath, useNavigate, useLocation} from 'react-router-dom';
import {getToken, getUserProfile} from "../jwt"; // Assuming you use react-router-dom
import FriendList from '../components/friendList';
import GetFriendRequest from '../components/getfriendRequest';
import GetUsers from '../components/getUsers';
import Logout from "../components/Logout";
function UserProfileComponent() {
        const [user, setUser] = useState({});
        const [loading, setLoading] = useState(true);
        const [error, setError] = useState(null);
        const navigate = useNavigate();
        const [tokenId, setTokenId] = useState(``);

        const handleEditClick = () => {
            const handleEditClick = async () => {
                const userProfile = await getUserProfile();
                navigate(`/userprofile/${userProfile.id}/edit`);
            };
            handleEditClick();
        };

        // get the path variable from the URL
        console.log('Path:', useLocation().pathname.split('/'));
        var id = null;
        if (useLocation().pathname.split('/').length < 3) {
            console.error('No user ID found in path');
            throw new Error('No user ID found in path');
        } else {
            console.log('User ID:', useLocation().pathname.split('/').at(2));
            id = useLocation().pathname.split('/').at(2);
        }

        // Effect to pull in user data
        useEffect(() => {
            const fetchUser = async () => {
                try {
                    const userProfile = await getUserProfile(); //used for validation of button display
                    setTokenId(userProfile.id);
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



                    const response = await fetch(`http://localhost:8085/v1/api/users/${id}`, requestOptions)
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
        console.log('User data updated:', user.id);
        console.log(`ID`, id);
        console.log(`token id`,tokenId);
        console.log( `=`,parseInt(id) ===  parseInt(tokenId) )
    }, [user]);

            const setStatus = async (selectedStatus) => {
                try {
                    const userProfile = await getUserProfile();
                    const token = getToken().trim();
                    console.log(`Token ${token}`);
                    const myHeaders = {
                        "Authorization": `Bearer ${token}`,
                        "Content-Type": "application/json",
                        "Accept": "application/json"
                    };
                    console.log('Headers:', myHeaders);
                    const requestOptions = {
                        method: "PUT",
                        headers: myHeaders,
                        body: JSON.stringify({ status: selectedStatus }),
                        redirect: "follow"
                    };

                    const response = await fetch(`http://localhost:8085/v1/api/users/${userProfile.id}/status`, requestOptions);
                    console.log('Response Headers:', response.headers);
                    console.log('Response Status:', response.status);

                    if (!response.ok) {
                        throw new Error('Network response was not ok');
                    }
                    const text = await response.text(); // Get the raw response text
                    const result = text ? JSON.parse(text) : {}; // Parse if not empty
                    console.log('Result:', result);
                } catch (error) {
                    console.error('Error changing status', error);
                }
            };

    const statusChange = async (event) => {
        const status = event.target.value;
        if (status !== user.status) {
            await setStatus(status); // Wait for the status update to complete
            setUser((prevUser) => ({
                ...prevUser,
                status: status // Update the user status in the local state
            }));
        }
    };


        return (
            <>
                <div>
                    <h1>{user.username || "Please set your username"}</h1>
                    <Logout/>
                    <p>Email: {user.email || "Not updated by user"}</p>
                    <p>Bio: {user.biography || "Not updated by user"}</p>
                    <p>Favourite food: {user.favFood || "Not updated by user"}</p>marin
                    <p>Birthday: {user.birthday || "Not updated by user"}</p>
                    <p>Degree: {user.degree || "Not updated by user"}</p>

                </div>
                {parseInt(id) ===  parseInt(tokenId) &&
                <div>
                    <button onClick={handleEditClick}>Edit Profile</button>
                    <select
                        name="status"
                        onChange={statusChange}
                        value={user.status || ""}
                        style={{ padding: '10px', borderRadius: '5px', border: '1px solid #ccc' }}
                    >
                        <option value="Online">Online</option>
                        <option value="Do not disturb">Do not disturb</option>
                        <option value="Offline">Offline</option>
                    </select>
                </div>
                }
                <FriendList></FriendList>
                <GetFriendRequest></GetFriendRequest>
                <GetUsers></GetUsers>
                <p></p>
                <button type="button" onClick={() => window.location.href = "/homepage"}>
                    Home Page
                </button>
            </>
        );
}

export default UserProfileComponent;
