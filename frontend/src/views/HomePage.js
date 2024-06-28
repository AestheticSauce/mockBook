import React, {useEffect, useState} from "react";
import axios from "axios";
import * as jwtUtils from "../jwt.js";

const HomePage = () => {
    const [friendsPosts, setFriendsPosts] = useState([]);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchFriendsPosts = async () => {
            try {
                const response =
                    await axios.get("http://localhost:8085/v1/api/posts", {
                    headers: {
                        "Authorization": `Bearer ${jwtUtils.getToken()}`
                    }
                });
                setFriendsPosts(response.data);
                console.log(response.data); // Log the actual response data
            } catch (error) {
                console.error("Error fetching friendships:", error);
                setError("Error fetching data");
            }
        };

        fetchFriendsPosts();
    }, []);

    if (error) {
        return <div>{error}</div>;
    }

    return (
        <div>
            <h1>Home Page</h1>
            <button type="button" onClick={() => window.location.href = "/UserProfile"}>
                My Profile
            </button>
            <h2>My Friends' Posts</h2>
            <ul>
                {friendsPosts.map((friendsPost, index) => (
                    <li key={index}>
                        <h3>{friendsPost.title}</h3>
                        <p>{friendsPost.content}</p>
                        <small>Author: {friendsPost.author.name}</small>
                        <small>Created at: {new Date(friendsPost.created_at).toLocaleString()}</small>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default HomePage;