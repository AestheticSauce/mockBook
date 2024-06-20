import { useState } from "react";

function RequestorControl(prop){
    const [show,updateShow] = useState(true);
    const [pending,updatePending] = useState(false);

    return(
        <>
        <link rel="stylesheet" href="https://matcha.mizu.sh/matcha.css"></link>
        <div>
            <p>{prop.firstName}&nbsp;{prop.lastName}</p>
            {show ? <div>
                    <button onClick={()=>{
                    updateShow(false)
                    updatePending(true)}}>Add Friend</button>
                    </div> :<></>}
                    
            { pending ? <div> 
                <button>Pending</button>
                <button onClick={()=>{
                updatePending(false)
                updateShow(true)
                 }}>Cancel</button>
            </div>:<></>}
        </div>
        </>
       
    )
}
export default RequestorControl