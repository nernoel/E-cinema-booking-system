// authUtils.js
export const getUserIDFromToken = () => {
    try {
        const token = localStorage.getItem('token');
        if (!token) {
            throw new Error('Token not found in local storage');
        }

        const parts = token.split('.');
        if (parts.length !== 3) {
            throw new Error('Invalid JWT token format');
        }

        const payload = JSON.parse(atob(parts[1])); // Decoding the payload
        console.log('Decoded Payload:', payload);

        if (!payload.userId) {
            throw new Error('User ID not found in token payload');
        }

        return payload.userId;
    } catch (error) {
        console.error('Error extracting user ID:', error.message);
        return null; // Handle this as needed
    }
};
