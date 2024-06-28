import axios from "axios";
import { useState } from "react";

function RequesteeControl({ userName, onClick }) {
    function accept(){
        axios({
            method:'PUT',
            url:'',
            data:{
                //data to be put here
            }
        })
    }

    function Reject(){
        axios({
            method:'PUT',
            url:'',
            data:{
                //data to be put here
            }
        })
    }
    return (
        <>
            <link rel="stylesheet" href="https://matcha.mizu.sh/matcha.css"></link>
            <div>
                    <div>
                        <p>{userName} wants to be your friend</p>
                        <button onClick={() => {
                            onClick();
                        }}>Accept</button>
                        <button onClick={() => {
                            onClick();
                        }}>Reject</button>  
                    </div>
            </div>
        </>
    );
}

export default RequesteeControl;
