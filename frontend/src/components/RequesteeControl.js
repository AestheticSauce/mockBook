import axios from "axios";
import { useState } from "react";
import { getToken } from "../jwt";

function RequesteeControl({ id, userName, onClick }) {
    console.log(userName)
    console.log(id)
    function accept(){
        const token = getToken().trim()
        axios({
             method: 'PUT',
             url: `http://localhost:8085/v1/api/friendships/${id}/accept`,
             headers: {
                 "Authorization": `Bearer ${token}`,
                 "Content-Type": "application/json",
             }
         });
    }

    function Reject(){
        const token = getToken().trim()
        axios({
            method: 'PUT',
            url: `http://localhost:8085/v1/api/friendships/${id}/reject`,
            headers: {
                "Authorization": `Bearer ${token}`,
                "Content-Type": "application/json",
            }
        });
    }
    return (
        <>
            <link rel="stylesheet" href="https://matcha.mizu.sh/matcha.css"></link>
            <div>
                    <div>
                        <p>{userName} wants to be your friend</p>
                        <button onClick={() => {
                            onClick(); accept();
                        }}>Accept</button>
                        <button onClick={() => {
                            onClick(); Reject();
                        }}>Reject</button>  
                    </div>
            </div>
        </>
    );
}

export default RequesteeControl;
