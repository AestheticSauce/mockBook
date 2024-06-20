import { useState } from "react"

function RequesteeControl(prop){

    const [show, setShow] = useState(true)

    return(
        <>
        <link rel="stylesheet" href="https://matcha.mizu.sh/matcha.css"></link>
        <div>
            { show ? 
            <div>
                <p>{prop.firstName}&nbsp;{prop.lastName} Wants to be your friend</p>
                <button onClick={()=> {setShow(false)}}>Accept</button>
                <button onClick={()=> {setShow(false)}}>Reject</button>  
            </div> :<></>
            }
        </div>
        </>
        
    )

}
export default RequesteeControl
