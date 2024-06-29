import React from 'react';
import axios from 'axios';
import { getToken } from "../jwt";

function Friend({ id, username, friendId, onClick }) {
    console.log(id)
    async function deleteFriend(){
       const token = getToken().trim()
       axios({
            method: 'DELETE',
            url: `http://localhost:8085/v1/api/friendships/${id}`,
            headers: {
                "Authorization": `Bearer ${token}`,
                "Content-Type": "application/json",
            }
        });
    }
    return (
        <div className="friend">
            <div className="friend-info">
                <div><a href={`/userprofile/${friendId}`}>{username}</a></div>
                <button onClick={() => { onClick(); deleteFriend(); }}>Delete</button>
            </div>
        </div>
    );
}

export default Friend;
