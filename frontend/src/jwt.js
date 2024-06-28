import { jwtVerify } from 'jose';

const secret = new TextEncoder().encode('SecretKeyToGenJWTs');

// Function to decode and verify a JWT token
export async function verifyJwt(token) {
    try {
        const { payload } = await jwtVerify(token, secret);
        return payload;
    } catch (error) {
        console.error('Invalid token', error);
        return null;
    }
}

// Function to get the JWT token from localStorage
export function getToken() {
    try {
        const token = localStorage.getItem('token');
        if (!token) {
            console.error('Token not found in localStorage');
        }
        return token;
    } catch (error) {
        console.error('Error retrieving token from localStorage', error);
        return null;
    }
}

// Function to save the JWT token to localStorage
export function setToken(token) {
    try {
        localStorage.setItem('token', token);
    } catch (error) {
        console.error('Error saving token to localStorage', error);
    }
}

// Function to remove the JWT token from localStorage
export function removeToken() {
    try {
        localStorage.removeItem('token');
    } catch (error) {
        console.error('Error removing token from localStorage', error);
    }
}

// Function to transform the payload into the expected JSON structure
function transformPayload(payload) {
    return {
        id: payload.sub,
        username: payload.username || '',
        email: payload.email || '',
        password: payload.password || '',
        created_at: payload.created_at || '',
        updated_at: payload.updated_at || '',
        security_question: payload.security_question || '',
        security_answer: payload.security_answer || '',
        enabled: payload.enabled || false,
        role: payload.roles ? payload.roles[0] : '',
        status: payload.status || '',
        degree: payload.degree || '',
        favFood: payload.favFood || '',
        birthday: payload.birthday || '',
        biography: payload.biography || ''
    };
}

// Function to get user profile from the token
export async function getUserProfile() {
    try {
        const token = getToken();
        if (token) {
            const payload = await verifyJwt(token);

            if (payload) return transformPayload(payload);
        }
        console.error('No valid token found or payload is null');
        return null;
    } catch (error) {
        console.error('Error getting user profile', error);
        return null;
    }
}
