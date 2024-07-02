import React, { useEffect, useState } from "react";
import { getToken} from "../jwt";
import Friend from "./friend";  // Corrected the import statement
import '../styles/dropdown.css'
import '../styles/Main.css'
import axios from "axios";
import * as jwtUtils from "../jwt.js";

function FriendList() {
    const [show, setShow] = useState(false);
    const [deletedFriends, setDeletedFriends] = useState(new Set());
    const [currFriends, updateCurrFriends] = useState([]);
    const [users, updateUsers] = useState([]);
    const [user, setUser] = useState();
    function removeFriend(id) {
        setDeletedFriends(prevDeletedFriends => new Set([...prevDeletedFriends, id]));
    }
    useEffect(()=>{
        const fetchUser = async () => {
            try {
              const userProfile = await jwtUtils.getUserProfile();
              console.log(userProfile);
              setUser(userProfile);
            } catch (error) {
              console.error("Unauthorized access", error);
            }
          };
          fetchUser();
    },[])
    
    useEffect(() => {
        if (show) {
            const fetchReq = async () => {
                    const token = getToken().trim()
                    console.log(token)
                    const response = await axios({
                        method: 'GET',
                        url: "http://localhost:8085/v1/api/friendships",
                        headers: {
                            "Authorization": `Bearer ${token}`,
                            "Content-Type": "application/json",
                        }
                    }).catch(error=>{
                        console.log(error);
                    });
                    updateUsers(response.data); 
            };
           fetchReq();
        }
    }, [show]);
    
    useEffect(() => {
        if (users.length > 0) {
            const friends = users.filter(person => person.sourceUser && !deletedFriends.has(person.sourceUser.id))
                                 .map(person=> {
                                    let friend = person.sourceUser;
                                    if(friend.id === Number(user.id)){
                                        friend = person.targetUser;
                                    }
                                    return(
                                        <Friend key={person.id}
                                             id={person.id}
                                             friendId={friend.id}
                                             username={friend.email} 
                                             onClick={() => removeFriend(friend.id)} />
                                    )
                                 });
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
