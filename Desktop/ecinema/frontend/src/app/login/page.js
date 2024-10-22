"use client"; 

import React, { useState } from 'react';
import './login.css'; // import the css
import { useRouter } from 'next/navigation'; // use router for redirection

export default function Login() {
  const [email, setEmail] = useState(''); 
  const [password, setPassword] = useState('');
  const [errorMessage, setErrorMessage] = useState(''); // To handle errors
  const router = useRouter(); // Initialize router

  const handleLogin = async (e) => {
    e.preventDefault();

    try {
      const response = await fetch('http://localhost:8080/api/users');
      const users = await response.json();

      // Check if the email exists in the users list
      const user = users.find((user) => user.email === email);

      if (user) {
        // For now, we assume the password matches since there's no hashed password handling here.
        // You should match passwords if hashed in real scenarios.
        console.log('Login successful:', user);

        // Redirect to a desired page on success, e.g., home page or dashboard
        alert("Login successful!");
        router.push('/home-loggedin'); // Redirect to dashboard or any page
      } else {
        setErrorMessage('Invalid email or password');
      }
    } catch (error) {
      console.error('Error fetching users:', error);
      setErrorMessage('An error occurred while trying to log in.');
    }
  };

  return (
    <div className="login-container">
      <h1>Login</h1>
      <form onSubmit={handleLogin}>
        <div>
          <input
            type="email" 
            placeholder="Email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />
        </div>
        <div>
          <input
            type="password"
            placeholder="Password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
        </div>
        <button type="submit">Login</button>
      </form>
      {errorMessage && <p style={{ color: 'red' }}>{errorMessage}</p>}
      <p>Don't have an account? <a href="/register">Register here</a></p>
      <a href="/">Back to Home</a> {/* Link back to home */}
    </div>
  );
}
