import React, { useEffect, useState } from "react";
import { getToken } from "../jwt";
import Friend from "./friend";  // Corrected the import statement
import '../styles/dropdown.css'
import '../styles/Main.css'
import axios from "axios";
function FriendList() {
    const [show, setShow] = useState(false);
    const [deletedFriends, setDeletedFriends] = useState(new Set());
    const [currFriends, updateCurrFriends] = useState([]);
    const [users, updateUsers] = useState([]);

    function removeFriend(id) {
        setDeletedFriends(prevDeletedFriends => new Set([...prevDeletedFriends, id]));
    }
    
    useEffect(() => {
        if (show) {
            const fetchReq = async () => {
                try {
                    const token = getToken().trim()
                    console.log(token)
                    const response = await axios({
                        method: 'GET',
                        url: "http://localhost:8085/v1/api/friendships",
                        headers: {
                            "Authorization": `Bearer ${token}`,
                            "Content-Type": "application/json",
                        }
                    });
                    updateUsers(response.data); 
                } catch (err) {
                    console.log(err);
                }
            };
           fetchReq();
        }
    }, [show]);
    
    useEffect(() => {
        if (users.length > 0) {
            const friends = users.filter(user => user.sourceUser && !deletedFriends.has(user.sourceUser.id))
                                 .map(user => (
                                     <Friend key={user.sourceUser.id}
                                             id={user.id}
                                             friendId={user.sourceUser.id}
                                             username={user.sourceUser.email} 
                                             onClick={() => removeFriend(user.sourceUser.id)} />
                                 ));
            updateCurrFriends(friends);
        }
    }, [users, deletedFriends]);

    return (
        <>
            <link rel="stylesheet" href="https://matcha.mizu.sh/matcha.css"></link>
            <button className='friendList' onClick={() => { setShow(!show) }}>Friends List</button>
            {show && (
                <div className="dropdown">
                    <div className="dropdown-content">
                        {currFriends}
                    </div>
                </div>
            )}
        </>
    );
}

export default FriendList;
