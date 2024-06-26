import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
function EditUserProfile() {
    const navigate = useNavigate();
    const [user, setUser] = useState(null); // handling who the user is
    const [loading, setLoading] = useState(true); // To handle loading state
    const [error, setError] = useState(null); // To handle errors

    const [formData, setFormData] = useState({
        username: '',
        biography: '',
        birthday: '',
        degree: '',
        favFood: ''
    });

    //effect to pull in user data.. will probably implement smarter solution later
    useEffect(() => {
        fetch("http://localhost:8085/v1/api/24")
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

    // Handle input changes
    const handleChange = (event) => {
        const { name, value } = event.target;
        setFormData({
            ...formData,
            [name]: value
        });
    };

    //Handling clicking the save button, put request and then response from console
    const handleSave = (event) => {
        event.preventDefault();
        fetch("http://localhost:8085/users/24", {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(formData)
        })
            .then(response => response.json())
            .then(()=> {
                console.log('User updated:');
                navigate('/userprofile/5'); //sends to user profile so they can see change right away
            })
            .catch(error => console.error('Error updating user profile', error));
    };
    //handling errors/loading of the page
    if (loading) return <div>Loading...</div>;
    if (error) return <div>Error: {error}</div>;
    if (!user) return <div>No user data available</div>;

    return (
        <form onSubmit={handleSave}>
            <div>
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
export default EditUserProfile