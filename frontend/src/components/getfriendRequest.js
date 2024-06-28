import { useEffect, useState } from "react";
import RequesteeControl from "./RequesteeControl";
import axios from 'axios';
import { getToken } from "../jwt";

function GetFriendRequest() {
    const [show, setShow] = useState(false);
    const [users, updateUsers] = useState([]);
    const [processedRequests, setProcessedRequests] = useState(new Set());
    const [currRequests, updateCurrRequests] = useState([]);

    const removeRequest = (id) => {
        setProcessedRequests(prevProcessedRequests => new Set([...prevProcessedRequests, id]));
    };

    useEffect(() => {
        if (show) {
            const fetchReq = async () => {
                try {
                    const token = getToken().trim();
                    const response = await axios({
                        method: 'GET',
                        url: "http://localhost:8085/v1/api/friendships/requests",
                        headers: {
                            "Authorization": `Bearer ${token}`,
                            "Content-Type": "application/json",
                        }
                    });
                    console.log("Response data:", response.data); // Debug log
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
            const friendRequests = users
                .filter(user => user.sourceUser && !processedRequests.has(user.sourceUser.id))
                .map(user => {
                    console.log("User data:", user.sourceUser); // Debug log
                    return (
                        <RequesteeControl
                            key={user.id}
                            id={user.sourceUser.email}
                            userName={user.sourceUser.email}
                            onClick={() => removeRequest(user.sourceUser.id)}
                        />
                    );
                });
            updateCurrRequests(friendRequests);
        }

    }, [users, processedRequests]);

    return (
        <>
            <link rel="stylesheet" href="https://matcha.mizu.sh/matcha.css"></link>
            <div>
                <button onClick={() => setShow(!show)}>Friend Requests</button>
                {show ? currRequests : null}
            </div>
        </>
    );
}

export default GetFriendRequest;
