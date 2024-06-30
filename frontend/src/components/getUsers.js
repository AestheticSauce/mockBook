import { useEffect, useState } from "react";
import axios from 'axios';
import { getToken,getUserProfile } from "../jwt";
import RequestorControl from "./RequestorControl";

function GetUsers() {
    const [show, setShow] = useState(false);
    const [users, updateUsers] = useState([]);
    const [processedRequests, setProcessedRequests] = useState(new Set());
    const [currUsers, updateCurrUsers] = useState([]);

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
                        url: "http://localhost:8085/v1/api/users",
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
        const getUserProfiles = async () => {
            if (users.length > 0) {
                const currentUser = await getUserProfile()
                const friendRequests = users
                    .filter(user => user && !processedRequests.has(user.id) && currentUser.id !== user.id)
                    .map(user => {
                        if (user.id != currentUser.id) {
                            console.log("User data:", user.sourceUser); // Debug log
                            return (
                                <RequestorControl
                                    key={user.id}
                                    id={user.id}
                                    userName={user.email}
                                    onClick={() => removeRequest(user.sourceUser.id)}
                                />
                            );
                        }
                    });
                    updateCurrUsers(friendRequests);
            }
        }
        getUserProfiles();

    }, [users, processedRequests]);

    return (
        <>
            <link rel="stylesheet" href="https://matcha.mizu.sh/matcha.css"></link>
            <div>
                <button onClick={() => setShow(!show)}>Get Users</button>
                {show ? currUsers : null}
            </div>
        </>
    );
}

export default GetUsers;
