import {jwtVerify} from 'jose';


const secret = new TextEncoder().encode('SecretKeyToGenJWTs');

// Function to decode and verify a JWT token
export async function verifyJwt(token) {
    try {
        const {payload} = await jwtVerify(token, secret);
        return payload;
    } catch (error) {
        console.error('Invalid token', error);
        return null;
    }
}

// Function to get the JWT token from localStorage
export function getToken() {
    return localStorage.getItem('token');
}

// Function to save the JWT token to localStorage
export function setToken(token) {
    localStorage.setItem('token', token);
}

// Function to remove the JWT token from localStorage
export function removeToken() {
    localStorage.removeItem('token');
}

export async function getUserProfile() {
    const token = getToken();
    if (token) {
        return await verifyJwt(token);
    }
    return null;
}