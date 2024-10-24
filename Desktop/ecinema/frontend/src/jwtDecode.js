import jwt_decode from "jwt-decode";

export function getEmailFromToken(token) {
  if (!token) return null;

  try {
    const decoded = jwt_decode(token);
    return decoded.email; // Adjust based on your JWT structure
  } catch (error) {
    console.error('Error decoding token:', error);
    return null;
  }
}

// Function to decode the JWT and extract the email or user ID
export function getUserIdFromToken(token) {
  if (!token) return null;

  try {
    const decoded = jwt_decode(token);
    return decoded.id; // Adjust based on your JWT structure
  } catch (error) {
    console.error('Error decoding token:', error);
    return null;
  }
}

