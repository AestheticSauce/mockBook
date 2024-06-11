import { useState } from "react"
import AcceptButton from "./AcceptButton"
import RejectButton from './RejectButton'
function FriendButtonContainer(){

    const [show,setShowToFalse] = useState(true)

    function updateShow(){
        setShowToFalse(false)
    }

    return(
        <div>
            {show && <AcceptButton onClick={updateShow}/> }
            {show && <RejectButton onClick={updateShow}/> }
        </div>
    )
}
export default FriendButtonContainer
