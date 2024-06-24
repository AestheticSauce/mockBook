import {
    createBrowserRouter, Navigate,
    RouterProvider,
} from "react-router-dom";
import Register from "./views/Register";
import { useState } from "react";
import Login from "./views/Login";
<<<<<<< HEAD
import PasswordReset from "./views/PasswordReset";

=======
import UserProfile from "./views/UserProfile";
import EditUserProfile from './views/EditUserProfile';
>>>>>>> d9684cd (basic functionality added, in process of hardcoding for examples sake)
function App() {
    
    const [user, setUser] = useState(null);

    const router = createBrowserRouter([
        {
            path: '/', // root page
            element: <Register/>
        },
        {
            path: '/Register',
            element: <Register/>
        },
        {
            path: '/Login',
            element: <Login setUser={setUser}/>
        },
        {
<<<<<<< HEAD
            path: '/password-reset',
            element: <PasswordReset/>
=======
            path: '/UserProfile',
            element: <Navigate to="/userprofile/19" /> //using navigate for hardcoded example, until login is finished
        },
        {
            path: '/userprofile/:id',
            element: <UserProfile />
        },
        {
            path: '/userprofile/:id/edit', //needs to check login status in the future, so only user can edit user
            element: <EditUserProfile />
>>>>>>> d9684cd (basic functionality added, in process of hardcoding for examples sake)
        }
    ]);

    return (
        <><link rel="stylesheet" href="https://matcha.mizu.sh/matcha.css"></link>
        <RouterProvider router={router}>
            {router}
        </RouterProvider></>
    );
}

export default App;
