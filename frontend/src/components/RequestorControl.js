import { useState } from "react";
import axios from 'axios';
import { getToken } from "../jwt";

function RequestorControl({userName,id,onClick}){
    const [show,updateShow] = useState(true);
    const [pending,updatePending] = useState(false);
    function add(){
        const token = getToken().trim()
        axios({
             method: 'POST',
             url: `http://localhost:8085/v1/api/friendships`,
             data:{
               "targetId": id
             },
             headers: {
                 "Authorization": `Bearer ${token}`,
                 "Content-Type": "application/json",
             }
         }).then(response=>{console.log(response)})
    }
    return(
        <>
        <link rel="stylesheet" href="https://matcha.mizu.sh/matcha.css"></link>
        <div>
            <p>{userName}</p>
            {show ? <div>
                    <button onClick={()=>{
                    updateShow(false);
                    updatePending(true);
                    add();}}>Add Friend</button>
                    </div> :<></>}
                    
            { pending ? <div> 
                <button>Pending</button>
            </div>:<></>}
        </div>
        </>
       
    )
}
export default RequestorControl