import { useState } from "react"

function RequesteeControl(prop){

    const [show, setShow] = useState(true)

    return(
        <div>
            { show ? 
            <div>
                <p>{prop.firstName}&nbsp;{prop.lastName} Wants to be your friend</p>
                <button onClick={()=> {setShow(false)}}>Accept</button>
                <button onClick={()=> {setShow(false)}}>Reject</button>  
            </div> :<></>
            }
        </div>
    )

}
export default RequesteeControl
