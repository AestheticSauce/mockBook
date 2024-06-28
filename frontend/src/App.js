import {
    createBrowserRouter, Navigate,
    RouterProvider,
} from "react-router-dom";
import Register from "./views/Register";
import { useState } from "react";
import Login from "./views/Login";
import PasswordReset from "./views/PasswordReset";
import UserProfile from "./views/UserProfile";
import EditUserProfile from './views/EditUserProfile';
import HomePage from "./views/HomePage";
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
            path: '/password-reset',
            element: <PasswordReset/>
        },
        {
            path: '/userprofile/:id',
            element: <UserProfile />
        },
        {
            path: '/userprofile/:id/edit', //needs to check login status in the future, so only user can edit user
            element: <EditUserProfile />
        },
        {
            path: '/homepage',
            element: <HomePage />
        }
    ])

    return (
        <><link rel="stylesheet" href="https://matcha.mizu.sh/matcha.css"></link>
        <RouterProvider router={router}>
            {router}
        </RouterProvider></>
    );
}

export default App;
