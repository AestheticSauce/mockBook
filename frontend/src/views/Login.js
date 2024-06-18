import { Link } from "react-router-dom";

function Login(){
    return (
        <div>
            <h2>Login</h2>
            {/* Add your login form here */}
            <Link to="/password-reset">Forgot Password?</Link>
        </div>
    );
}

export default Login;