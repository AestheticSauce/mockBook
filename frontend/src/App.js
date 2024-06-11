import {
    createBrowserRouter,
    RouterProvider,
} from "react-router-dom";
import Register from "./views/Register";
import { useState } from "react";
import Login from "./views/Login";

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
