import React, { useState, useEffect } from 'react';
import {useLocation, useNavigate} from 'react-router-dom';
import { getToken, getUserProfile } from "../jwt";
import Logout from "../components/Logout";
function EditUserProfile() {
    const navigate = useNavigate();
    const [user, setUser] = useState(null); // handling who the user is
    const [loading, setLoading] = useState(true); // To handle loading state
    const [error, setError] = useState(null); // To handle errors
    const [redirect, setRedirect] = useState(true);
    const [formData, setFormData] = useState({
        username: '',
        biography: '',
        birthday: '',
        degree: '',
        favFood: ''
    });


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

    useEffect(() => {
        const fetchUser = async () => {
            try {
                const userProfile = await getUserProfile();
                if(parseInt(userProfile.id) !== parseInt(id)){
                    setTimeout(() => {
                        navigate(`/userprofile/${userProfile.id}`);
                    }, 3000);
                    return;
                }
                setRedirect(false);

                const token = getToken().trim();
                const myHeaders = {
                    "Authorization": `Bearer ${token}`,
                    "Content-Type": "application/json",
                    "Accept": "application/json"
                };

                const requestOptions = {
                    method: "GET",
                    headers: myHeaders,
                    redirect: "follow"
                };

                const response = await fetch(`http://localhost:8085/v1/api/users/${userProfile.id}`, requestOptions);

                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }

                const result = await response.json();

                formData.username = result.username;
                formData.biography = result.biography;
                formData.birthday = result.birthday;
                formData.degree = result.degree;
                formData.favFood = result.favFood;

                setUser(result);
                setLoading(false);
            } catch (error) {
                setError(error.message);
                setLoading(false);
            }
        };

        fetchUser();
    }, [navigate]);

    useEffect(() => {
        console.log('User data updated:', user);
    }, [user]);

    const handleChange = (event) => {
        const { name, value } = event.target;
        setFormData({
            ...formData,
            [name]: value
        });
    };

    const handleSave = async (event) => {
        event.preventDefault();
        try {
            const userProfile = await getUserProfile();
            const token = getToken().trim();
            const myHeaders = {
                "Authorization": `Bearer ${token}`,
                "Content-Type": "application/json",
                "Accept": "application/json"
            };

            const requestOptions = {
                method: "PUT",
                headers: myHeaders,
                body: JSON.stringify(formData),
                redirect: "follow"
            };

            const response = await fetch(`http://localhost:8085/v1/api/users/${userProfile.id}`, requestOptions);

            if (!response.ok) {
                throw new Error('Network response was not ok');
            }

            const result = await response.json();
            navigate(`/userprofile/${userProfile.id}`); // Navigate to the updated user profile
        } catch (error) {
            setError(error.message);
        }
    };
    if(redirect) return <h1>Users can only edit their own profiles. Redirecting. </h1>
    if (loading) return <div>Loading...</div>;
    if (error) return <div>Error: {error}</div>;
    if (!user) return <div>No user data available</div>;

    return (
        <form onSubmit={handleSave}>
            <div>
                <Logout/>
                <label>Username:</label>
                <input type="text" name="username" value={formData.username} onChange={handleChange} />
            </div>
            <div>
                <label>Biography:</label>
                <input type="text" name="biography" value={formData.biography} onChange={handleChange} />
            </div>
            <div>
                <label>Birthday:</label>
                <input type="text" name="birthday" value={formData.birthday} onChange={handleChange} />
            </div>
            <div>
                <label>Degree:</label>
                <input type="text" name="degree" value={formData.degree} onChange={handleChange} />
            </div>
            <div>
                <label>Favorite Food:</label>
                <input type="text" name="favFood" value={formData.favFood} onChange={handleChange} />
            </div>
            <button type="submit">Update</button>

        </form>
    );
}

export default EditUserProfile;
